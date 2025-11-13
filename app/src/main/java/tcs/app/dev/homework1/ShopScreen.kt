package tcs.app.dev.homework1

import tcs.app.dev.homework1.data.*
import tcs.app.dev.homework1.data.MockData.getName
import tcs.app.dev.homework1.data.MockData.getImage

import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.ui.theme.AppTheme

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import androidx.compose.runtime.saveable.rememberSaveable
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.Shop

/**
 * # Homework 3 — Shop App
 *
 * Build a small shopping UI with ComposeUI using the **example data** from the
 * `tcs.app.dev.homework.data` package (items, prices, discounts, and ui resources).
 * The goal is to implement three tabs: **Shop**, **Discounts**, and **Cart**.
 *
 * ## Entry point
 *
 * The composable function [ShopScreen] is your entry point that holds the UI state
 * (selected tab and the current `Cart`).
 *
 * ## Data
 *
 * - Use the provided **example data** and data types from the `data` package:
 *   - `Shop`, `Item`, `Discount`, `Cart`, and `Euro`.
 *   - There are useful resources in `res/drawable` and `res/values/strings.xml`.
 *     You can add additional ones.
 *     Do **not** hard-code strings in the UI!
 *
 * ## Requirements
 *
 * 1) **Shop item tab**
 *    - Show all items offered by the shop, each row displaying:
 *      - item image + name,
 *      - item price,
 *      - an *Add to cart* button.
 *    - Tapping *Add to cart* increases the count of that item in the cart by 1.
 *
 * 2) **Discount tab**
 *    - Show all available discounts with:
 *      - an icon + text describing the discount,
 *      - an *Add to cart* button.
 *    - **Constraint:** each discount can be added **at most once**.
 *      Disable the button (or ignore clicks) for discounts already in the cart.
 *
 * 3) **Cart tab**
 *    - Only show the **Cart** tab contents if the cart is **not empty**. Within the cart:
 *      - List each cart item with:
 *        - image + name,
 *        - per-row total (`price * amount`),
 *        - an amount selector to **increase/decrease** the quantity (min 0, sensible max like 99).
 *      - Show all selected discounts with a way to **remove** them from the cart.
 *      - At the bottom, show:
 *        - the **total price** of the cart (items minus discounts),
 *        - a **Pay** button that is enabled only when there is at least one item in the cart.
 *      - When **Pay** is pressed, **simulate payment** by clearing the cart and returning to the
 *        **Shop** tab.
 *
 * ## Navigation
 * - **Top bar**:
 *      - Title shows either the shop name or "Cart".
 *      - When not in Cart, show a cart icon.
 *        If you feel fancy you can add a badge to the icon showing the total count (capped e.g. at "99+").
 *      - The cart button is enabled only if the cart contains items. In the Cart screen, show a back
 *        button to return to the shop.
 *
 * - **Bottom bar**:
*       - In Shop/Discounts, show a 2-tab bottom bar to switch between **Shop** and **Discounts**.
*       - In Cart, hide the tab bar and instead show the cart bottom bar with the total and **Pay**
*         action as described above.
 *
 * ## Hints
 * - Keep your cart as a single source of truth and derive counts/price from it.
 *   Rendering each list can be done with a `LazyColumn` and stable keys (`item.id`, discount identity).
 * - Provide small reusable row components for items, cart rows, and discount rows.
 *   This keeps the screen implementation compact.
 *
 * ## Bonus (optional)
 * Make the app feel polished with simple animations, such as:
 * - `AnimatedVisibility` for showing/hiding the cart,
 * - `animateContentSize()` on rows when amounts change,
 * - transitions when switching tabs or updating the cart badge.
 *
 * These can help if want you make the app feel polished:
 * - [NavigationBar](https://developer.android.com/develop/ui/compose/components/navigation-bar)
 * - [Card](https://developer.android.com/develop/ui/compose/components/card)
 * - [Swipe to dismiss](https://developer.android.com/develop/ui/compose/touch-input/user-interactions/swipe-to-dismiss)
 * - [App bars](https://developer.android.com/develop/ui/compose/components/app-bars#top-bar)
 * - [Pager](https://developer.android.com/develop/ui/compose/layouts/pager)
 *
 */
enum class ShopTab { SHOP, DISCOUNTS, CART }

@Composable
fun ShopScreen(
    shop: Shop,
    availableDiscounts: List<Discount>,
    modifier: Modifier = Modifier
) {
    // save even if rotating phone
    var cart by rememberSaveable { mutableStateOf(Cart(shop = shop)) }
    var tab by rememberSaveable { mutableStateOf(ShopTab.SHOP) }

    val hasItems = cart.itemCount > 0u
    val showCartTab = hasItems // only show non empty cart

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {

            TopBar(
                inCart = tab == ShopTab.CART,
                title = if (tab == ShopTab.CART) stringResource(R.string.title_cart) else stringResource(R.string.name_shop),
                canOpenCart = hasItems,
                onBackFromCart = { tab = ShopTab.SHOP },
                onOpenCart = { tab = ShopTab.CART },
                cartBadge = cart.totalCount.toInt().coerceAtMost(99)
            )
        },
        bottomBar = {
            if (tab == ShopTab.CART) {
                CartBottomBar(
                    total = cart.price,
                    canPay = hasItems,
                    onPay = {
                        // Pay: empty and go back to shop
                        cart = Cart(shop = shop)
                        tab = ShopTab.SHOP
                    }
                )
            } else {
                ShopBottomBar(
                    selected = tab,
                    onSelect = { tab = it },
                    cartEnabled = hasItems
                )
            }
        }
    ) { inner ->
        when (tab) {
            ShopTab.SHOP -> ShopTabContent(
                shop = shop,
                modifier = Modifier.padding(inner),
                onAdd = { item -> cart += item }
            )
            ShopTab.DISCOUNTS -> DiscountsTabContent(
                discounts = availableDiscounts,
                cart = cart,
                modifier = Modifier.padding(inner),
                onAddOnce = { discount ->
                    // only adding once
                    if (!cart.discounts.contains(discount)) cart += discount
                }
            )
            ShopTab.CART -> CartTabContent(
                cart = cart,
                modifier = Modifier.padding(inner),
                onInc = { item -> cart += item },
                onDec = { item ->
                    val current = cart.items[item] ?: 0u
                    val next = if (current == 0u) 0u else current - 1u
                    cart = if (next == 0u) cart - item else cart.update(item to next)
                },
                onRemoveDiscount = { d -> cart -= d }
            )
        }
    }
}

@Composable
private fun TopBar(
    inCart: Boolean,
    title: String,
    canOpenCart: Boolean,
    onBackFromCart: () -> Unit,
    onOpenCart: () -> Unit,
    cartBadge: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // LEFT AREA: Back button or empty
        if (inCart) {
            IconButton(onClick = onBackFromCart) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.description_go_to_shop),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp)) // Platzhalter, damit Layout ausgerichtet bleibt
        }

        // MIDDLE area: Title
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        // RIGHT: Cart icon or empty
        if (!inCart) {
            IconButton(onClick = onOpenCart, enabled = canOpenCart) {
                if (canOpenCart) {
                    BadgedBox(
                        badge = {
                            Badge {
                                Text(
                                    if (cartBadge >= 99) stringResource(R.string.more_than_99)
                                    else cartBadge.toString()
                                )
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = stringResource(R.string.description_go_to_cart),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = stringResource(R.string.description_go_to_cart),
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp)) // Placeholder
        }
    }
}

@Composable
private fun ShopBottomBar(
    selected: ShopTab,
    onSelect: (ShopTab) -> Unit,
    cartEnabled: Boolean
) { NavigationBar {
    NavigationBarItem(
        selected = selected == ShopTab.SHOP,
        onClick = { onSelect(ShopTab.SHOP) },
        icon = { Icon(Icons.Default.List, contentDescription = null) },
        label = { Text(stringResource(R.string.label_shop)) }
    )
    NavigationBarItem(
        selected = selected == ShopTab.DISCOUNTS,
        onClick = { onSelect(ShopTab.DISCOUNTS) },
        icon = { Icon(Icons.Default.LocalOffer, contentDescription = null) },
        label = { Text(stringResource(R.string.label_discounts)) }
    )
} }

@Composable
private fun CartBottomBar(
    total: Euro,
    canPay: Boolean,
    onPay: () -> Unit
) { BottomAppBar {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.total_price, total.toString()))
        Button(onClick = onPay, enabled = canPay) {
            Text(stringResource(R.string.label_pay))
        }
    }
} }

@Composable
private fun ShopTabContent(
    shop: Shop,
    modifier: Modifier = Modifier,
    onAdd: (Item) -> Unit
) {
    //like in Bonus Exercise
    LazyColumn(modifier = modifier) {
        items(shop.items.toList(), key = { it.id }) { item ->
            val nameRes = MockData.getName(item)
            val imageRes = MockData.getImage(item)
            val price = shop.prices[item]!!
            ShopItemRow(
                imageRes = imageRes,
                nameRes = nameRes,
                price = price,
                onAdd = { onAdd(item) }
            )
        }
    }
}

@Composable
private fun DiscountsTabContent(
    discounts: List<Discount>,
    cart: Cart,
    modifier: Modifier = Modifier,
    onAddOnce: (Discount) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(discounts) { d ->
            val (icon, text) = when (d) {
                is Discount.Fixed -> Icons.Default.AttachMoney to stringResource(
                    R.string.amount_off,
                    d.amount.toString()
                )

                is Discount.Percentage -> Icons.Default.Percent to stringResource(
                    R.string.percentage_off,
                    d.value.toString()
                )

                is Discount.Bundle -> {
                    val name = stringResource(getName(d.item))
                    Icons.Default.ShoppingBag to
                            stringResource(
                                R.string.pay_n_items_and_get,
                                d.amountItemsPay.toString(),
                                name,
                                d.amountItemsGet.toString()
                            )
                }
            }
            DiscountRow(
                icon = icon,
                text = text,
                enabled = !cart.discounts.contains(d),
                onAdd = { onAddOnce(d) }
            )
            Divider(thickness = 0.5.dp)
        }
    }
}

@Composable
private fun CartTabContent(
    cart: Cart,
    modifier: Modifier = Modifier,
    onInc: (Item) -> Unit,
    onDec: (Item) -> Unit,
    onRemoveDiscount: (Discount) -> Unit
) {
    LazyColumn(modifier = modifier) {
        // Items
        items(cart.items.toList(), key = { it.first.id }) { (item, amount) ->
            val nameRes = getName(item)
            val imageRes = getImage(item)
            val unitPrice = cart.shop.prices[item]!!
            val rowTotal = unitPrice * amount
            CartItemRow(
                imageRes = imageRes,
                nameRes = nameRes,
                amount = amount.toInt(),
                price = rowTotal,
                onInc = { onInc(item) },
                onDec = { onDec(item) }
            )
            Divider(thickness = 0.5.dp)
        }
        // Discounts
        items(cart.discounts) { d ->
            val text = when (d) {
                is Discount.Fixed      -> stringResource(R.string.amount_off, d.amount.toString())
                is Discount.Percentage -> stringResource(R.string.percentage_off, d.value.toString())
                is Discount.Bundle     -> {
                    val name = stringResource(getName(d.item))
                    stringResource(R.string.pay_n_items_and_get, d.amountItemsPay.toString(), name, d.amountItemsGet.toString())
                }
            }
            CartDiscountRow(
                discountText = text,
                onRemove = { onRemoveDiscount(d) }
            )
            Divider(thickness = 0.5.dp)
        }
    }
}

@Preview(showBackground = true, name = "TopBar Shop")
@Composable
private fun TopBarShopPreview() {
    AppTheme {
        TopBar(
            inCart = false,
            title = stringResource(R.string.name_shop),
            canOpenCart = true,
            onBackFromCart = {},
            onOpenCart = {},
            cartBadge = 5
        )
    }
}

@Preview(showBackground = true, name = "ShopScreen Shop Tab")
@Composable
fun ShopScreenPreview() {
    AppTheme {
        ShopScreen(
            shop = MockData.ExampleShop,
            availableDiscounts = MockData.ExampleDiscounts
        )
    }
}



@Preview(showBackground = true, name = "TopBar Cart")
@Composable
private fun TopBarCartPreview() {
    AppTheme {
        TopBar(
            inCart = true,
            title = stringResource(R.string.title_cart),
            canOpenCart = false,
            onBackFromCart = {},
            onOpenCart = {},
            cartBadge = 0
        )
    }
}

@Preview(showBackground = true, name = "BottomBar  Shop Tab")
@Composable
private fun ShopBottomBarPreview() {
    AppTheme {
        ShopBottomBar(
            selected = ShopTab.SHOP,
            onSelect = {},
            cartEnabled = true
        )
    }
}

@Preview(showBackground = true, name = "BottomBar Cart")
@Composable
private fun CartBottomBarPreview() {
    AppTheme {
        CartBottomBar(
            total = 19u.euro,
            canPay = true,
            onPay = {}
        )
    }
}

@Preview(showBackground = true, name = "CartTab Cart filled")
@Composable
private fun CartTabContentPreview() {
    val shop = MockData.ExampleShop
    var cart = Cart(shop = shop)
    cart = cart + MockData.Apple
    cart = cart + MockData.Apple
    cart = cart + MockData.Banana
    cart = cart + Discount.Fixed(3u.euro)

    AppTheme {
        CartTabContent(
            cart = cart,
            onInc = {},
            onDec = {},
            onRemoveDiscount = {}
        )
    }
}

