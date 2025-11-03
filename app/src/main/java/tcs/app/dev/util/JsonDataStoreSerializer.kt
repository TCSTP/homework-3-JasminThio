package tcs.app.dev.util

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class JsonDataStoreSerializer<T>(
    private val kSerializer: KSerializer<T>,
    override val defaultValue: T,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
    }
) : Serializer<T> {

    override suspend fun readFrom(input: InputStream): T = try {
        val text = input.readBytes().decodeToString()
        json.decodeFromString(kSerializer, text)
    } catch (e: SerializationException) {
        throw CorruptionException("Cannot read DataStore file.", e)
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        val bytes = json.encodeToString(kSerializer, t).encodeToByteArray()
        output.write(bytes)
    }
}
