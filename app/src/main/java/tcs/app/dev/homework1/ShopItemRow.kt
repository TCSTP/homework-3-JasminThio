package tcs.app.dev.homework1

import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.cents

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Euro
import tcs.app.dev.R

@Composable
fun ShopItemRow(
    @DrawableRes imageRes: Int,
    @StringRes nameRes: Int,
    price: Euro,
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
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    text = stringResource(nameRes),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = price.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Button(onClick = onAdd) {
            Text(stringResource(R.string.description_add_to_cart))
        }
    }
}

@Preview(showBackground = true, name = "ShopItemRow – Apple")
@Composable
fun ShopItemRowPreview() {
    val item = MockData.Apple
    val price = 60u.cents

    AppTheme {
        ShopItemRow(
            imageRes = MockData.getImage(item),
            nameRes = MockData.getName(item),
            price = price,
            onAdd = {}
        )
    }
}