package tcs.app.dev.homework1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class Shop(val prices: Map<Item, Euro>) : Parcelable {
    val items
        get() = prices.keys
}
