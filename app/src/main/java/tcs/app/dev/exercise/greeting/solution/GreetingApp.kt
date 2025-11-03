package tcs.app.dev.exercise.greeting.solution

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 *
 *  It is best practice to have a modifier as parameter for every composable function.
 *  It is an easy way to make your composable function configurable.
 *
 * ## Tasks:
 * - Create a composable function `GreetingApp` and run it on an emulator.
 *
 * ## Resources:
 * - [Emulator](https://developer.android.com/studio/run/emulator)
 * - [Modifier parameter](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-component-api-guidelines.md#parameter)
 * - [Best practices](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-component-api-guidelines.md)
 */
@Composable
fun GreetingApp(modifier: Modifier = Modifier) {
    Greeting("World", modifier)
}
