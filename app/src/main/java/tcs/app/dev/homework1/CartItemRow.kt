package tcs.app.dev.homework1

import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.cents

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Euro
import tcs.app.dev.homework1.data.times

@Composable
fun CartItemRow(
    @DrawableRes imageRes: Int,
    @StringRes nameRes: Int,
    amount: Int,
    price: Euro,
    onInc: () -> Unit,
    onDec: () -> Unit,
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
            Image(painter = painterResource(imageRes), contentDescription = null, modifier = Modifier.size(48.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(text = stringResource(nameRes), style = MaterialTheme.typography.titleMedium)
                Text(text = price.toString(), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(onClick = onDec) { Text("−") }
            Text(" $amount ", modifier = Modifier.padding(horizontal = 8.dp))
            OutlinedButton(onClick = onInc) { Text("+") }
        }
    }
}

@Preview(showBackground = true, name = "CartItemRow – 3x Apple")
@Composable
fun CartItemRowPreview() {
    val item = MockData.Apple
    val unitPrice = 60u.cents
    val amount = 3
    val total = unitPrice * amount.toUInt()

    AppTheme {
        CartItemRow(
            imageRes = MockData.getImage(item),
            nameRes = MockData.getName(item),
            amount = amount,
            price = total,
            onInc = {},
            onDec = {}
        )
    }
}