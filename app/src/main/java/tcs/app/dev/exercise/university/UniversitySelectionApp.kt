package tcs.app.dev.exercise.university

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.MainActivity
import tcs.app.dev.exercise.university.data.LMU
import tcs.app.dev.exercise.university.data.Option
import tcs.app.dev.exercise.university.data.TUM
import tcs.app.dev.exercise.university.data.Universities

/**
 * # University selection app
 * We want to write a simple app (with questionable usefulness) that has two screens:
 * 1. On the first screen you can select a university.
 * 2. After the selection we can go to the second screen,
 *   which displays the uni logo and further description.
 *
 * # UI
 * We will build the UI of the app component-wise in a bottom-up manner.
 * Monitor your UI using [Preview].
 * You can use [LMU], [TUM] and [Universities] as mock data.
 *
 * ## Tasks:
 * - First, we create a composable function `RadioRow`, which will be an selectable "element".
 *   We want to add an image on the left, a title in the middle and a `RadioButton` on the right.
 * - `RadioRow` should take four parameters:
 *   1. `image: Painter`: This image should be shown on the left.
 *   2. `title: String`: The title in the middle.
 *   3. `selected: Boolean`: This indicates if the `RadioButton` is selected or not.
 *   4. `modifier: Modifier = Modifier`: See best practices.
 * - Try displaying the three parameters using [Image], [Text] and [RadioButton].
 * - Use the [Row] layout to display the 3 elements next to each other.
 * - Style our UI Elements with the following parameters and [Modifier]s.
 *   Always monitor what is happening and read up in the documentation.
 *   [Row]: [fillMaxWidth], [padding], [Alignment], [Arrangement];
 *   [Image]: [size], [clip];
 *   [Text]: [RowScope.weight], [padding];
 *   [RadioButton]: [padding].
 *   Try around with different values until it looks "good".
 * - We also want to give the `RadioRow` a background and a border:
 *   Wrap everything in a [Surface], and give it a border and rounded corners.
 *   Set the color of the surface and the border depending on the parameter `selected`.
 * - Overload `RadioRow` twice:
 *   1. Use the slot API pattern for `image` and `title`.
 *   2. Take an [Option] as parameter.
 * - Create a composable function `SelectionScreen` with the parameters:
 *    - `title: String`
 *    - `options: List<Option>`
 * - Use the slot API of the [Scaffold] layout:
 *   1. The top bar should consist of a checkbox [Icon] and the title.
 *   You can use [Row] or if you feel fancy try [TopAppBar] or [CenterAlignedTopAppBar].
 *   2. In the body display the `options` in a row using a `for`-loop.
 *   3. The bottom bar should contain a [Button] that says "Next".
 * - For the styling of the `SelectionScreen` use what you learned until now.
 * - Create a composable function `DetailsScreen` that shows the contents of a [Option] on the
 *   whole screen. Use a [Column] layout. For the styling use what you learned until now.
 *
 * ## Resources:
 * - [Image](https://developer.android.com/develop/ui/compose/graphics/images/)
 * - [Painter](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/painter/Painter)
 * - [Text](https://developer.android.com/develop/ui/compose/text/)
 * - [RadioButton](https://developer.android.com/develop/ui/compose/components/radio-button)
 * - [Modifier parameter best practice](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-component-api-guidelines.md#parameter)
 * - [Row](https://developer.android.com/develop/ui/compose/layouts/basics#standard-layouts)
 * - [Padding and size modifiers](https://developer.android.com/develop/ui/compose/modifiers#padding-and-size)
 * - [Weight modifier](https://developer.android.com/develop/ui/compose/modifiers#weight-in-row-and-column)
 * - [Clip image](https://developer.android.com/develop/ui/compose/graphics/images/customize#clip-image-shape)
 * - [Surface](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#Surface%28androidx.compose.ui.Modifier,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.ui.unit.Dp,androidx.compose.foundation.BorderStroke,kotlin.Function0)
 * - [Slot API pattern](https://developer.android.com/develop/ui/compose/layouts/basics#slot-based-layouts)
 * - [Scaffold](https://developer.android.com/develop/ui/compose/components/scaffold)
 * - [Icon](https://developer.android.com/develop/ui/compose/graphics/images/material)
 * - [Available icons](https://fonts.google.com/icons?icon.set=Material+Icons)
 * - [App bars](https://developer.android.com/develop/ui/compose/components/app-bars)
 * - [Button](https://developer.android.com/develop/ui/compose/components/button)
 * - [Column](https://developer.android.com/develop/ui/compose/layouts/basics#standard-layouts)
 *
 * # State
 * We created our UI but cannot interact with it yet.
 * Now, we have to add logic and state.
 * From now on to try out your app using an emulator.
 * For that, call [UniversitySelectionApp] in [MainActivity]
 * and put what you want to try out in [UniversitySelectionApp].
 *
 * ## Tasks:
 * - We want to listen to clicks on the `RadioRow`s.
 *   For that, add a parameter `onSelected: () -> Unit = {}` to `RadioRow`.
 *   Listen to click events of the [Surface] using its `onClick` parameter
 *   and call `onSelected` when it's clicked.
 *   Like this, we give control to the caller of `RadioRow` over what is happening when it is clicked.
 *   This pattern is called state hoisting.
 * - Now, we actually want to display the selection of an option.
 *   We want that max 1 option is selected at once.
 *   For that, store the current selection in the `Selection` screen.
 *   We cannot do it with a simple variable, because composable functions have no state by default.
 *   We have to use [remember] with [mutableStateOf] inside it to store an [Option].
 *   Since we don't have a selection in the beginning store a `Option?` with `null` as initial value.
 *   We can use property delegation, such that we do not have to deal with [MutableState] directly.
 * - Connect the state to the `RadioRow` using it's `onSelected` parameter
 *   and also set its `selected` parameter correctly.
 * - If you try it out on the emulator and change the screen orientation or the dark/bright mode,
 *   you'll see that the selection is gone.
 *   This is because [remember] does not survive configuration changes.
 *   If you want to change this, you can use [rememberSaveable].
 *   This just works with state that is saveable in the `Bundle`.
 *   For this reason we made [Option] confirm [Parcelable].
 *   Another possibility would be to just store an id instead of the [Option] object as state.
 * - Next, we want to connect the state to the "Next" button on the `SelectionScreen`.
 *   Enable the [Button] if a selection is made.
 *   Hoist its `onClick` event as a parameter `onSelected: (Option) -> Unit`.
 * - Finally, we want to connect our two screens in [UniversitySelectionApp].
 *   Store selections made by `SelectionScreen`.
 *   Show `SelectionScreen` until a selection is made.
 *   If a selection is made show `DetailsScreen` with the selection.
 *
 * ## Resources:
 * - [Emulator](https://developer.android.com/studio/run/emulator)
 * - [State hoisting](https://developer.android.com/develop/ui/compose/state#state-hoisting)
 * - [State](https://developer.android.com/develop/ui/compose/state#state-in-composables)
 * - [Button](https://developer.android.com/develop/ui/compose/components/button)
 * - [Parcelize plugin](https://developer.android.com/kotlin/parcelize)
 * - [Restore UI State](https://developer.android.com/develop/ui/compose/state#restore-ui-state)
 *
 * # Bonus
 *
 * ## Tasks:
 * - Right now we cannot get back from the `DetailsScreen` to the `SelectionScreen`.
 *   If we press the back button of the phone, the app closes.
 *   To change this, we can use [BackHandler] in [UniversitySelectionApp]
 *   and set the selection to `null` if the back button is pressed.
 *   (In bigger apps we do not want to deal with this kind of navigation everywhere manually.
 *   You can use the compose navigation library for that.
 *   It also features animations on screen transitions.)
 * - An app feels more natural and polished with animations.
 *   We want to add a new feature with an animation to the `RadioRow`:
 *   1. Add a new parameter to `RadioRow` such that it also takes the description of an [Option].
 *   2. Create a state `expanded: Boolean` with default value `false`.
 *   3. Flip `expanded` on a long click on the [Surface] using [combinedClickable].
 *   4. If `expanded` is `true` show the `description` under the [Row] using a [Column].
 *   5. Animate the size of the [Column] using [animateContentSize].
 *      You can use for example `spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)`
 *   (Another way would be to use [animateDpAsState] instead of [animateContentSize].)
 * - If we expand the selection options, would have more options or are on smaller device,
 *   we are might not able to fit all the options on the screen.
 *   A good solution is to make the selections list scrollable.
 *   This is possible with either [Modifier.scrollable] or by replacing [Column] with a [LazyColumn].
 *   Try out [LazyColumn]!
 *
 * ## Resources:
 * - [BackHandler](https://developer.android.com/reference/kotlin/androidx/activity/compose/package-summary#BackHandler(kotlin.Boolean,kotlin.Function0))
 * - [Navigation](https://developer.android.com/develop/ui/compose/navigation)
 * - [Long press](https://developer.android.com/develop/ui/compose/touch-input/pointer-input/tap-and-press#long-press-show)
 * - [Combined clickable](https://developer.android.com/reference/kotlin/androidx/compose/foundation/package-summary#(androidx.compose.ui.Modifier).combinedClickable(kotlin.Boolean,kotlin.String,androidx.compose.ui.semantics.Role,kotlin.String,kotlin.Function0,kotlin.Function0,kotlin.Boolean,androidx.compose.foundation.interaction.MutableInteractionSource,kotlin.Function0))
 * - [Animations](https://developer.android.com/develop/ui/compose/animation/introduction)
 * - [Scrolling](https://developer.android.com/develop/ui/compose/touch-input/pointer-input/scroll)
 * - [Lazy lists](https://developer.android.com/develop/ui/compose/lists#lazy)
 *
 * # General Resources:
 * - [Compose components](https://developer.android.com/develop/ui/compose/components)
 * - [Best practices](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-component-api-guidelines.md)
 * - [Preview](https://developer.android.com/develop/ui/compose/tooling/previews)
 * - [Compose layouts](https://developer.android.com/develop/ui/compose/layouts)
 * - [Compose modifiers](https://developer.android.com/develop/ui/compose/modifiers)
 * - [Compose theming](https://developer.android.com/codelabs/jetpack-compose-theming#0)
 * - [Material3 compose API](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary)
 * - [Material3 in compose](https://developer.android.com/develop/ui/compose/designsystems/material3)
 * - [Touch input](https://developer.android.com/develop/ui/compose/touch-input)
 **/
@Composable
fun UniversitySelectionApp(modifier: Modifier = Modifier) {

}
