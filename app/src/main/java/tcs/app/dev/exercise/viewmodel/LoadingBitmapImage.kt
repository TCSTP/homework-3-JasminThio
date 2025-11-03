package tcs.app.dev.exercise.viewmodel

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun LoadingBitmapImage(
    bitmap: Bitmap?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    AnimatedContent(bitmap) { bitmap ->
        if (bitmap == null) {
            CircularProgressIndicator(
                modifier = modifier,
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            Image(
                bitmap.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
    }
}
