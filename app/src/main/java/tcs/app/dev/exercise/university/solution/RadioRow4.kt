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
import tcs.app.dev.ui.theme.AppTheme

/**
 * - We wrap the [Row] in a [Surface].
 * - We create a [BorderStroke] for the [Surface] with 1 [dp] width
 *   and a color depending on [selected].
 * - We define colors for the [Surface] depending on [selected].
 *   We use colors from the theme, so that they are consistent and adapt to dark/bright mode.
 * - We make the surface as wide as possible using [fillMaxWidth]
 *   and give it a reasonable [padding].
 *
 * ## Resources:
 * - [Surface](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#Surface%28androidx.compose.ui.Modifier,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.ui.unit.Dp,androidx.compose.foundation.BorderStroke,kotlin.Function0)
 * - [Material3 in compose](https://developer.android.com/develop/ui/compose/designsystems/material3)
 * - [Theming colors](https://developer.android.com/codelabs/jetpack-compose-theming#3)
 * - [Padding and size modifiers](https://developer.android.com/develop/ui/compose/modifiers#padding-and-size)
 * - [Dynamic content](https://developer.android.com/develop/ui/compose/mental-model#dynamic)
 */
@Composable
fun RadioRow4(
    image: Painter,
    title: String,
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
            Image(
                image,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                text = title
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
fun RadioRow4SelectedPreview() {
    AppTheme {
        RadioRow4(
            image = LMU.image,
            title = LMU.title,
            selected = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioRow4NotSelectedPreview() {
    AppTheme {
        RadioRow4(
            image = LMU.image,
            title = LMU.title,
            selected = false
        )
    }
}
