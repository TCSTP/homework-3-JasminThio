package tcs.app.dev.exercise.university.solution

import androidx.compose.foundation.Image
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.exercise.university.data.LMU
import tcs.app.dev.ui.theme.AppTheme

/**
 * - We display [image] using [Image].
 *   The `contentDescription` parameter is for accessibility.
 *   We set it to `null`, because [title] already describes the image.
 * - We display [title] using [Text].
 * - We display [selected] with a [RadioButton].
 *   We do not listen to clicks on the button and set `onClick` to `null`.
 *
 * All the UI elements are just put above each other and are not styled.
 *
 * ## Resources:
 * - [Image](https://developer.android.com/develop/ui/compose/graphics/images/)
 * - [Painter](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/painter/Painter)
 * - [Accessibility](https://developer.android.com/develop/ui/compose/accessibility/api-defaults#graphic-elements)
 * - [Text](https://developer.android.com/develop/ui/compose/text/)
 * - [RadioButton](https://developer.android.com/develop/ui/compose/components/radio-button)
 **/
@Composable
fun RadioRow1(
    image: Painter,
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Image(image, contentDescription = null)
    Text(text = title)
    RadioButton(selected = selected, onClick = null)
}

@Preview(showBackground = true)
@Composable
fun RadioRow1SelectedPreview() {
    AppTheme {
        RadioRow1(
            image = LMU.image,
            title = LMU.title,
            selected = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioRow1NotSelectedPreview() {
    AppTheme {
        RadioRow1(
            image = LMU.image,
            title = LMU.title,
            selected = false
        )
    }
}
