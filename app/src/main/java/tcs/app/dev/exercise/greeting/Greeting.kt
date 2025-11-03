package tcs.app.dev.exercise.greeting

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * # 1. Composable Functions
 *
 * Building an UI with ComposeUI consists of composing composable functions.
 *
 * What are composable functions?
 * - A composable function needs to have the @Composable annotation.
 * - A composable function converts data into UI:
 *      - The function takes in data.
 *        Composable functions can accept parameters, which allow the app logic to describe the UI.
 *        In this case it takes a String so it can greet the user.
 *      - The function displays UI.
 *        In this case it does so by calling the Text() composable function,
 *        which creates the text UI element.
 * - A composable function usually does not return anything, but "emits" UI.
 * - A composable function can be just called from another composable function.
 *      - This is similar to suspending functions.
 * - A composable function should be:
 *      1. Fast
 *      2. Idempotent:
 *      It should behave the same way when called multiple times with the same argument,
 *      and it does not use other values such as global variables.
 *      3. Side-effect free:
 *      Does not modify properties or global variables.
 *      We will learn later how to incorporate side-effects correctly.
 * - When introducing state we will see why these properties are important.
 *
 * ## Tasks:
 * - Create a composable function [Greeting] that has a parameter `name` of type [String].
 * - Greet the user with his name in [Greeting] using the composable function [Text].
 *
 * ## Resources:
 * - [General Documentation](https://developer.android.com/develop/ui/compose/documentation)
 * - [Composable Functions](https://developer.android.com/develop/ui/compose/mental-model)
 * - [Text](https://developer.android.com/develop/ui/compose/text/)
 */
@Composable
fun Greeting(modifier: Modifier = Modifier) {

}

/**
 * # Previews
 * The @Preview annotation tells Android Studio that this composable should be shown in the
 * design view of this file.
 * You can see live updates to your composable preview as you make your edits.
 * It is a quick way to design your UI.
 *
 * A preview function cannot take parameters.
 * But the @Preview annotation can be configured.
 * Some useful configurations:
 * - [Preview.showBackground]: Enable a default background.
 * - [Preview.heightDp] [Preview.widthDp]: Set max sizes for height and width.
 * - [Preview.fontScale]: Scale the font.
 *   Simulates what happens if the user sets a bigger font size on their phone.
 * - [Preview.uiMode]: Set a different UI mode, like dark mode ([Configuration.UI_MODE_NIGHT_YES]).
 *   Here you don't see a difference between dark or light mode.
 *   We'll have a look at that later.
 *
 * You can have multiple previews with different parameters for the same composable function.
 * To distinguish them you can use [Preview.name].
 *
 * ## Tasks
 * - Create a composable function `GreetingPreview`
 *   and display it as preview with 2 different settings.
 *
 * ## Resources:
 * - [Preview](https://developer.android.com/develop/ui/compose/tooling/previews)
 */
fun GreetingPreview() {

}
