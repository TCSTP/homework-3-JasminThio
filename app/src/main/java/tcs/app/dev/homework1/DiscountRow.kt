package tcs.app.dev.homework1

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.ui.tooling.preview.Preview

import tcs.app.dev.ui.theme.AppTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import tcs.app.dev.R

@Composable
fun DiscountRow(
    icon: ImageVector,
    text: String,
    enabled: Boolean,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null)
            Spacer(Modifier.width(16.dp))
            Text(text)
        }
        Button(onClick = onAdd, enabled = enabled) {
            // Falls du lieber "Add" willst, erzeuge einen eigenen String-Res
            Text(stringResource(R.string.description_add_to_cart))
        }
    }
}



@Preview(showBackground = true, name = "DiscountRow – 3€ off")
@Composable
fun DiscountRowPreview() {
    AppTheme {
        DiscountRow(
            icon = Icons.Filled.LocalOffer,
            text = "3€ off!",
            enabled = true,
            onAdd = {}
        )
    }
}
