package tcs.app.dev.homework1.data

import android.os.Parcelable
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlin.getValue

/**
 * I made the cart and every type inside [Parcelable],
 * such that it can be stored using [rememberSaveable].
 * In a real app it would be stored persistently.
 * We'll do that in the next homework.
 */
@Parcelize
data class Cart(
    val shop: Shop,
    val items: Map<Item, UInt> = emptyMap(),
    private val allDiscounts: List<Discount> = emptyList()
) : Parcelable {
    init {
        require(shop.prices.keys.containsAll(items.keys))
    }

    @IgnoredOnParcel
    val discounts by lazy {
        allDiscounts.clean()
    }

    @IgnoredOnParcel
    val price: Euro by lazy {
        val price = items
            .map { (item, amount) -> shop.prices[item]!! * amount }
            .fold(0u.euro, Euro::plus)

        discounts
            .fold(price) { total, price -> price.apply(this, total) }
    }

    @IgnoredOnParcel
    val itemCount by lazy {
        items.values.sum()
    }

    @IgnoredOnParcel
    val discountsCount by lazy {
        discounts.size.toUInt()
    }

    @IgnoredOnParcel
    val totalCount by lazy {
        discountsCount + itemCount
    }
}

fun Cart.update(other: Pair<Item, UInt>): Cart = copy(items = items + other)

operator fun Cart.plus(other: Pair<Item, UInt>): Cart {
    val (item, amount) = other

    val currentAmount = items[item] ?: 0u
    val pair = other.copy(second = amount + currentAmount)

    return copy(items = items + pair)
}

operator fun Cart.plus(other: Item): Cart = this + (other to 1u)

operator fun Cart.plus(other: Discount): Cart = copy(allDiscounts = this.discounts + other)

operator fun Cart.plus(other: List<Discount>): Cart = copy(allDiscounts = this.discounts + other)

operator fun Cart.minus(other: Item): Cart = copy(items = this.items - other)

operator fun Cart.minus(other: Discount): Cart = copy(allDiscounts = this.discounts - other)
