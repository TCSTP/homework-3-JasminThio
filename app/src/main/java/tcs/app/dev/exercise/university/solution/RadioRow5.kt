package tcs.app.dev.exercise.university.solution

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.exercise.university.data.LMU
import tcs.app.dev.exercise.university.data.Option
import tcs.app.dev.ui.theme.AppTheme

/**
 * Overload for [Option].
 */
@Composable
fun RadioRow5(
    option: Option,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    RadioRow5(
        image = option.image,
        title = option.title,
        selected = selected,
        modifier = modifier
    )
}


@Composable
fun RadioRow5(
    image: Painter,
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    RadioRow5(
        image = { modifier -> Image(image, contentDescription = null, modifier = modifier) },
        title = { modifier -> Text(title, modifier = modifier) },
        selected = selected,
        modifier = modifier
    )
}

/**
 * Slot API Overload.
 *
 * Nicer with [Modifier], but this would also work:
 *
 * ```
 * @Composable
 * fun RadioRow5(
 *     image: @Composable () -> Unit,
 *     title: @Composable () -> Unit,
 *     selected: Boolean,
 *     modifier: Modifier = Modifier
 * ) { ... }
 * ```
 *
 *
 * ## Resources:
 * - [Slot API pattern](https://developer.android.com/develop/ui/compose/layouts/basics#slot-based-layouts)
 */
@Composable
fun RadioRow5(
    image: @Composable (Modifier) -> Unit,
    title: @Composable (Modifier) -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val border = BorderStroke(
        width = 1.dp,
        color =
            if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline
    )

    val color =
        if (selected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        border = border,
        color = color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            image(Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.medium))

            title(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )

            RadioButton(
                modifier = Modifier.padding(end = 8.dp),
                selected = selected,
                onClick = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioRow5SelectedPreview() {
    AppTheme {
        RadioRow5(
            image = LMU.image,
            title = LMU.title,
            selected = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioRow5NotSelectedPreview() {
    AppTheme {
        RadioRow5(option = LMU, selected = false)
    }
}
