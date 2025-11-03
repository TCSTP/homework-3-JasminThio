package tcs.app.dev.homework1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * # About value classes
 * A value class in Kotlin (`@JvmInline value class`) is a wrapper around a single property
 * that does **not** introduce a separate object at runtime (when optimizations are applied).
 * Instead, the underlying value (here: `UInt`) is used directly wherever possible.
 *
 * This gives you:
 * - **Type safety** – `Euro` can’t be mixed up with a plain `UInt` by accident.
 * - **No runtime overhead** – no extra allocation compared to using a bare `UInt`.
 * - **Cleaner domain modeling** – code reads in terms of `Euro`, `Item`, `Shop`, etc.
 *
 * Limitations:
 * - A value class can only wrap **one property**.
 * - They cannot have backing fields or inheritance hierarchies.
 * - Boxing may still occur in some cases (e.g. generics, reflection).
 *
 * # Why here?
 * Money amounts are stored in integer cents (UInt) to avoid floating-point rounding errors.
 * Wrapping them in a value class makes the type distinct and prevents accidental misuse.
 */
@Parcelize
@JvmInline
value class Euro(val cents: UInt) : Comparable<Euro>, Parcelable {

    // String representation for display only; keep all calculations in integers.
    override fun toString(): String = "€%.2f".format(cents.toDouble() / 100.0)

    override fun compareTo(other: Euro) = this.cents.compareTo(other.cents)
}

/**
 * NOTE on underflow/overflow:
 * - Using UInt means arithmetic wraps on underflow/overflow.
 *   In + and * this can overflow for large totals.
 * - For robust production code check for underflows/overflows!
 * - We are clamping at 0 and ignore overflows, which is fine for our use-case.
 *   In a real world use-case this can be problematic!
 */
operator fun Euro.plus(other: Euro) = Euro(cents + other.cents)

operator fun Euro.minus(other: Euro) =
    if (this <= other) Euro(0u) else Euro(cents - other.cents)

operator fun Euro.times(other: UInt) = Euro(cents * other)

/**
 * For convenience:
 */
val UInt.cents: Euro
    get() = Euro(this)

val UInt.euro: Euro
    get() = Euro(this * 100u)
