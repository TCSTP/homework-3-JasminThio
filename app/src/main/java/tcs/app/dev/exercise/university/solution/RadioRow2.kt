package tcs.app.dev.exercise.university.solution

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.exercise.university.data.LMU
import tcs.app.dev.ui.theme.AppTheme

/**
 * Putting the elements in a [Row], puts them horizontally next to each other.
 * It still looks ugly, because the styling is missing.
 **/
@Composable
fun RadioRow2(
    image: Painter,
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row {
        Image(image, contentDescription = null)
        Text(text = title)
        RadioButton(selected = selected, onClick = null)
    }
}

@Preview(showBackground = true)
@Composable
fun RadioRow2SelectedPreview() {
    AppTheme {
        RadioRow2(
            image = LMU.image,
            title = LMU.title,
            selected = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioRow2NotSelectedPreview() {
    AppTheme {
        RadioRow2(
            image = LMU.image,
            title = LMU.title,
            selected = false
        )
    }
}
