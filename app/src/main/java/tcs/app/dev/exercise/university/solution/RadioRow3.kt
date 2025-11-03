package tcs.app.dev.exercise.university.solution

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tcs.app.dev.exercise.university.data.LMU
import tcs.app.dev.ui.theme.AppTheme

/**
 * ## [Row]:
 * - We align the elements in the vertically in the center using [Alignment.CenterVertically].
 * - We space the elements evenly using [Arrangement.SpaceBetween].
 * - We make the row as wide as possible using [fillMaxWidth].
 * - We add some space around all elements in the row using [padding].
 * - For sizes we almost always want to use [dp]. (For font sizes we want to use [sp]).
 *
 * ## [Image]:
 * - We make the image 64x64dp using [size].
 * - We clip the image to have rounded corners, using [clip] and `MaterialTheme.shapes.medium`.
 *   These shapes can be configured in [tcs.app.dev.ui.theme.AppTheme].
 *
 * ## [Text]:
 * - We give 16 [dp] of horizontal [padding].
 * - We give a [RowScope.weight] of 1, such that the text is broken if it gets too long.
 *
 * ## [RadioButton]:
 * - We give it an additional 8 [dp] of [padding] at the end of the row.
 *
 * ## Resources:
 * - [Row](https://developer.android.com/develop/ui/compose/layouts/basics#standard-layouts)
 * - [Padding and size modifiers](https://developer.android.com/develop/ui/compose/modifiers#padding-and-size)
 * - [Pixel density](https://m3.material.io/foundations/layout/understanding-layout/density)
 * - [Compose shapes](https://developer.android.com/develop/ui/compose/graphics/draw/shapes)
 * - [Theming shapes](https://developer.android.com/codelabs/jetpack-compose-theming#6)
 * - [Theming](https://developer.android.com/codelabs/jetpack-compose-theming#0)
 * - [Material3 in compose](https://developer.android.com/develop/ui/compose/designsystems/material3)
 * - [Weight modifier](https://developer.android.com/develop/ui/compose/modifiers#weight-in-row-and-column)
 **/
@Composable
fun RadioRow3(
    image: Painter,
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun RadioRow3SelectedPreview() {
    AppTheme {
        RadioRow3(
            image = LMU.image,
            title = LMU.title,
            selected = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioRow3NotSelectedPreview() {
    AppTheme {
        RadioRow3(
            image = LMU.image,
            title = LMU.title,
            selected = false
        )
    }
}
