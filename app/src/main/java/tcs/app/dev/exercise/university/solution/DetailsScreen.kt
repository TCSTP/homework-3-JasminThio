package tcs.app.dev.exercise.university.solution

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.exercise.university.data.LMU
import tcs.app.dev.exercise.university.data.Option
import tcs.app.dev.exercise.university.data.TUM
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun DetailsScreen(option: Option, modifier: Modifier = Modifier) {
    DetailsScreen(
        image = option.image,
        title = option.title,
        description = option.description,
        modifier = modifier
    )
}

@Composable
fun DetailsScreen(
    image: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    DetailsScreen(
        image = { modifier -> Image(image, contentDescription = null, modifier = modifier) },
        title = title,
        description = description,
        modifier = modifier
    )
}

/**
 * We apply what we learned, when designing the `RadioRow` and `SelectionScreen`.
 */
@Composable
fun DetailsScreen(
    image: @Composable (Modifier) -> Unit,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.secondary).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        image(
            Modifier
                .size(256.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Text(
            title,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Text(
            description,
            modifier = Modifier.padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(showBackground = true, name = "TUM")
@Composable
fun DetailsScreenPreviewTUM() {
    AppTheme {
        DetailsScreen(option = TUM)
    }
}

@Preview(showBackground = true, name = "LMU")
@Composable
fun DetailsScreenPreviewLMU() {
    AppTheme {
        DetailsScreen(option = LMU)
    }
}
