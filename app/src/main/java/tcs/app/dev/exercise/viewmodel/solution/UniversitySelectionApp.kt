package tcs.app.dev.exercise.viewmodel.solution

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tcs.app.dev.R
import tcs.app.dev.exercise.viewmodel.DetailsScreen
import tcs.app.dev.exercise.viewmodel.LoadingBitmapImage
import tcs.app.dev.exercise.viewmodel.SelectionScreen


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
    modifier: Modifier = Modifier,
    viewModel: SelectionViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadOptions()
    }

    when (val selection = state.selection) {
        null -> {
            SelectionScreen(
                title = stringResource(R.string.university_selection),
                options = state.options,
                modifier = modifier
            ) { selected -> viewModel.select(selected) }
        }

        else -> {
            BackHandler {
                viewModel.select(null)
            }
            DetailsScreen(
                title = selection.title,
                description = selection.description,
                image = { modifier ->
                    LoadingBitmapImage(
                        bitmap = state.selectionImage,
                        contentDescription = null,
                        modifier = modifier
                    )
                },
                modifier = modifier
            )
        }
    }
}
