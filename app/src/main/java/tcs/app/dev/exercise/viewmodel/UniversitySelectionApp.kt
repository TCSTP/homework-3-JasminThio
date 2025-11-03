package tcs.app.dev.exercise.viewmodel

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R
import tcs.app.dev.exercise.viewmodel.data.Option

/**
 * # University selection app with ViewModel
 *
 * We will integrate our [SelectionViewModel] into [UniversitySelectionApp].
 * Do the following tasks after finishing the ones in [SelectionViewModel].
 *
 * ## Tasks
 *
 * 1. **Accept and construct the ViewModel**
 *    - Modify `UniversitySelectionApp` to accept a `SelectionViewModel` parameter.
 *    - Provide a default value for the parameter using `viewModel()`.
 *
 * 2. **Bind UI to ViewModel state**
 *    - Collect the ViewModel’s `StateFlow` using `collectAsStateWithLifecycle()`.
 *    - Remove the placeholders `selection` and `options`.
 *    - Use the collected state to populate the UI:
 *      - `state.options` for the `SelectionScreen`.
 *      - `state.selection` for the currently selected item.
 *      - `state.selectionImage` for the selected image.
 *    - Use `viewModel.select` to react to UI events.
 *    - Use `LaunchedEffect(Unit)` to call `viewModel.loadOptions` once when
 *      the Composable first appears.
 *
 *  ## Resources
 *
 *  - See [SelectionViewModel] for ViewModel and Flow related resources.
 *  - [Access ViewModel](https://developer.android.com/develop/ui/compose/libraries#viewmodel)
 *  - [Access ViewModel detailed](https://developer.android.com/reference/kotlin/androidx/lifecycle/viewmodel/compose/package-summary#viewmodel)
 *  - [collectAsStateWithLifecycle](https://developer.android.com/develop/ui/compose/state#use-other-types-of-state-in-jetpack-compose)
 *  - [LaunchedEffect](https://developer.android.com/develop/ui/compose/side-effects#launchedeffect)
 *  - [Side-effects](https://developer.android.com/develop/ui/compose/side-effects)
 */
@Composable
fun UniversitySelectionApp(
    modifier: Modifier = Modifier
) {
    // TODO: Use ViewModel State
    val selection: Option? = null
    val options: List<Option> = emptyList()

    when (val selection = selection) {
        null -> {
            SelectionScreen(
                title = stringResource(R.string.university_selection),
                options = options,
                modifier = modifier
            ) { selected ->
                // TODO: Register selection
            }
        }

        else -> {
            BackHandler {
                // TODO: Remove selection
            }
            DetailsScreen(
                title = selection.title,
                description = selection.description,
                image = { modifier ->
                    // TODO: Show image of selection
                },
                modifier = modifier
            )
        }
    }
}
