package tcs.app.dev.homework1


import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.ui.theme.AppTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource

@Composable
fun CartDiscountRow(
    discountText: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(discountText)
        TextButton(onClick = onRemove) { Text(stringResource(tcs.app.dev.R.string.description_remove_from_cart)) }
    }
}

@Preview(showBackground = true, name = "CartDiscountRow – 10% off")
@Composable
fun CartDiscountRowPreview() {
    AppTheme {
        CartDiscountRow(
            discountText = "10% off!",
            onRemove = {}
        )
    }
}
