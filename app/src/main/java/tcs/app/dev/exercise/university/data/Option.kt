package tcs.app.dev.exercise.university.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize

/**
 * The main data type we will be working with in our views.
 *
 * # Parcelable
 * We made it parcelable such that we can store it in the bundle.
 * We will see later why that is useful.
 *
 * ## Resources:
 * - [Parcelize plugin](https://developer.android.com/kotlin/parcelize)
 * - [Restore UI State](https://developer.android.com/develop/ui/compose/state#restore-ui-state)
 *
 * # App resources
 * We do not store the data directly in this data type but resource ids,
 * with which we can reference strings and images from the resources.
 * The [StringRes] and [DrawableRes] annotations check that the [Int]s are valid resource ids.
 *
 * For convenience we create properties with composable getters to access
 * the actual values behind the resources ids.
 * We can do that because getters are just functions with no parameters.
 * This is one of the cases where composable functions can return a value.
 *
 * ## Resources:
 * - [App Resources](https://developer.android.com/guide/topics/resources/providing-resources)
 * - [Resource Annotations](https://developer.android.com/studio/write/annotations#res-annotations)
 * - [Resources in compose](https://developer.android.com/develop/ui/compose/resources)
 */
@Parcelize
data class Option(
    @param:StringRes val titleId: Int,
    @param:StringRes val descriptionId: Int,
    @param:DrawableRes val imageId: Int
) : Parcelable {

    val title: String
        @Composable
        get() = stringResource(titleId)

    val description: String
        @Composable
        get() = stringResource(descriptionId)

    val image
        @Composable
        get() = painterResource(imageId)
}
