package tcs.app.dev.exercise.viewmodel

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun RadioRow(
    image: @Composable (Modifier) -> Unit,
    title: String,
    description: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onSelected: () -> Unit = {}
) {
    RadioRow(
        image = image,
        title = { modifier -> Text(title, modifier = modifier) },
        description = { modifier -> Text(description, modifier = modifier) },
        selected = selected,
        onSelected = onSelected,
        modifier = modifier
    )
}

@Composable
fun RadioRow(
    image: @Composable (Modifier) -> Unit,
    title: @Composable (Modifier) -> Unit,
    description: @Composable (Modifier) -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onSelected: () -> Unit = {}
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = onSelected,
                onLongClick = { expanded = !expanded }
            ),
        shape = MaterialTheme.shapes.medium,
        border = border,
        color = color
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                image(
                    Modifier
                        .size(64.dp)
                        .clip(MaterialTheme.shapes.medium)
                )

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

            if (expanded) {
                description(Modifier.padding(all = 8.dp))
            }
        }
    }
}
