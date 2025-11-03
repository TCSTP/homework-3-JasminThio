package tcs.app.dev.exercise.viewmodel

import androidx.lifecycle.ViewModel
import tcs.app.dev.exercise.viewmodel.data.getImage
import tcs.app.dev.exercise.viewmodel.data.getOptions
import tcs.app.dev.exercise.viewmodel.data.getPreviewImage

/**
 * # Implement a ViewModel for University Selection
 *
 * In this exercise, you will implement a `SelectionViewModel` that manages UI state
 * for selecting a university and displaying its details. The ViewModel should expose
 * observable state using Kotlin Flows and handle asynchronous image loading.
 *
 * ## Tasks
 *
 * 1. **Define the UI state model**
 *    - Create an immutable `data class State` inside `SelectionViewModel` containing:
 *      - `options: List<Option>` – the list of available options (default: empty).
 *      - `selection: Option?` – the currently selected option (default: `null`).
 *      - `selectionImage: Bitmap?` – the full image for the selected option (default: `null`).
 *
 * 2. **Make the state observable**
 *    - Create a private property of type `MutableStateFlow<State>`.
 *    - Expose a public `StateFlow<State>` using `asStateFlow()` so the UI can collect it.
 *
 *  3. **Provide image-loading helpers**
 *    - Implement helper functions `loadPreview(id: String)` and `loadImage(id: String)`
 *  that load preview and full images from the data layer.
 *  Use [getImage] and [getPreviewImage].
 *    - Execute them on a background dispatcher (`Dispatchers.IO`).
 *    - You may retry until an image is available, or use a capped retry mechanism.
 *
 * 4. **Load and publish the options list**
 *    - Implement a function `loadOptions()` to retrieve a list of `Option` objects
 *   using [getOptions].
 *    - Update the ViewModel state with the fetched list.
 *    - For each option, asynchronously load a *preview image* (on a background dispatcher)
 *      and update the corresponding `Option` in the list once loaded.
 *
 * 5. **Handle selection and full image loading**
 *    - Implement a function `select(selection: Option?)`.
 *    - If `selection` is `null`, clear both `selection` and `selectionImage`.
 *    - Otherwise, update the state’s `selection` immediately.
 *    - Load the *full image* for the selected option in the background.
 *    - Only update `selectionImage` if the same option is still selected when loading completes.
 *
 *
 * ## Resources
 *
 * - [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel)
 * - [Coroutine dispatchers](https://kotlinlang.org/docs/coroutines-basics.html#coroutine-dispatchers)
 * - [Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)
 * - [ViewModel scope](https://developer.android.com/topic/libraries/architecture/coroutines#viewmodelscope)
 * - [StateFlow in ViewModels](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow)
 * - [StateFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
 * - [Flow](https://kotlinlang.org/docs/flow.html)
 *
 */
class SelectionViewModel : ViewModel() {

}
