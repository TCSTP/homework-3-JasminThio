package tcs.app.dev.homework1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class Item(val id: String) : Parcelable
