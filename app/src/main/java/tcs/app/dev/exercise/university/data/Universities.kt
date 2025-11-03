package tcs.app.dev.exercise.university.data

import tcs.app.dev.R

/**
 * # Reference resources
 *
 * All resource IDs are defined in your project's R class, through which we can reference them.
 *
 * ## Resources:
 * - [Reference resources](https://developer.android.com/guide/topics/resources/providing-resources#Accessing)
 * - [App Resources](https://developer.android.com/guide/topics/resources/providing-resources)
 * - [Resources in compose](https://developer.android.com/develop/ui/compose/resources)
 */
val LMU = Option(
    titleId = R.string.lmu,
    descriptionId = R.string.description_lmu,
    imageId = R.drawable.lmu
)

val TUM = Option(
    titleId = R.string.tum,
    descriptionId = R.string.description_tum,
    imageId = R.drawable.tum
)

/**
 * We mock some data to display.
 * In a real application the data will come from the data layer through a view model.
 */
val Universities = listOf(LMU, TUM)
