package tcs.app.dev.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

data class ImageDownloader(
    val connectionTimeout: Duration,
    val readTimeout: Duration,
    val maxImageBytes: Long,
    val baseUrl: String
) {
    data class NotFound(override val message: String) : Exception(message)

    suspend fun getImage(
        id: String,
        targetWidth: Int,
        targetHeight: Int
    ): Result<Bitmap> = withContext(Dispatchers.IO) {
        try {
            val urlString = "$baseUrl/${id.lowercase()}.png"
            val url = URL(urlString)

            if (!url.protocol.equals("https", ignoreCase = true)) {
                throw SecurityException("Only HTTPS is allowed: $urlString")
            }

            val connectionTimeoutMilliseconds = connectionTimeout.toInt(DurationUnit.MILLISECONDS)
            val readTimeoutMilliseconds = readTimeout.toInt(DurationUnit.MILLISECONDS)

            val connection = (url.openConnection() as HttpURLConnection).apply {
                instanceFollowRedirects = true
                requestMethod = "GET"
                connectTimeout = connectionTimeoutMilliseconds
                readTimeout = readTimeoutMilliseconds
                doInput = true
                useCaches = false
            }

            try {
                connection.connect()
                val code = connection.responseCode

                if (code == HttpURLConnection.HTTP_NOT_FOUND) {
                    connection.errorStream?.close()
                    throw NotFound("Image not found: $urlString")
                }

                if (code !in 200..299) {
                    throw IOException("HTTP $code ${connection.responseMessage} for $urlString")
                }

                val contentType = connection.contentType?.lowercase()
                if (contentType != null && !contentType.startsWith("image/")) {
                    connection.inputStream?.close()
                    throw IOException("Unexpected content type: $contentType")
                }

                val length = connection.contentLengthLong
                if (length > 0 && length > maxImageBytes) {
                    connection.inputStream?.close()
                    throw IOException("Image too large ($length bytes)")
                }

                val bytes = connection.inputStream.use { input ->
                    readAllBytesCapped(input, maxImageBytes)
                }

                val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
                if (bounds.outWidth <= 0 || bounds.outHeight <= 0) {
                    throw IOException("Failed to read image bounds")
                }

                val sample = calculateSampleSize(
                    sourceWidth = bounds.outWidth,
                    sourceHeight = bounds.outHeight,
                    targetWidth = targetWidth,
                    targetHeight = targetHeight
                )

                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = false
                    inSampleSize = sample
                    inPreferredConfig = Bitmap.Config.ARGB_8888
                }

                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
                    ?: throw IOException("Failed to decode bitmap")

                Result.success(bitmap)
            } finally {
                try { connection.disconnect() } catch (_: Throwable) {}
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    private fun readAllBytesCapped(input: InputStream, cap: Long): ByteArray {
        val buffer = ByteArray(8 * 1024)

        return ByteArrayOutputStream().use { out ->
            var total: Long = 0

            while (true) {
                val n = input.read(buffer)
                if (n == -1) break
                total += n
                if (total > cap) throw IOException("Image exceeds cap of $cap bytes")
                out.write(buffer, 0, n)
            }

            out.toByteArray()
        }
    }

    private fun calculateSampleSize(
        sourceWidth: Int,
        sourceHeight: Int,
        targetWidth: Int,
        targetHeight: Int
    ): Int {
        var sampleSize = 1
        if (targetWidth <= 0 || targetHeight <= 0) return sampleSize

        val halfHeight = sourceHeight / 2
        val halfWidth = sourceWidth / 2

        while (targetHeight <= halfHeight / sampleSize && targetWidth <= halfWidth / sampleSize) {
            sampleSize *= 2
        }

        return sampleSize.coerceAtLeast(1)
    }

    companion object {
        val TCSAppDevImages = ImageDownloader(
            connectionTimeout = 5.seconds,
            readTimeout = 10.seconds,
            maxImageBytes = 10 * 1024 * 1024,
            baseUrl = "https://www.tcs.ifi.lmu.de/user/pages/img/app-dev"
        )
    }
}
