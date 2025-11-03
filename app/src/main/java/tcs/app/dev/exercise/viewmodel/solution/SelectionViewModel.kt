package tcs.app.dev.exercise.viewmodel.solution

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tcs.app.dev.exercise.viewmodel.data.Option
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
    data class State(
        val options: List<Option> = emptyList(),
        val selection: Option? = null,
        val selectionImage: Bitmap? = null
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun loadOptions() {
        viewModelScope.launch {
            val options = getOptions().getOrNull()

            if (options != null) {
                mutableState.update { state -> state.copy(options = options) }

                /* This could be further improved by loading the images in parallel and/or
                    updating the state after each new image loaded */
                val withPreviews =
                    options.map { option -> option.copy(previewImage = loadPreview(option.id)) }


                mutableState.update { state ->
                    if (state.options.map { it.id } == options.map { it.id })
                        state.copy(options = withPreviews)
                    else state
                }
            }
        }
    }

    /**
     * It would be useful to cache the images, such that we do not need to load them multiple times.
     */
    fun select(selection: Option?) {
        mutableState.update { state ->
            if (selection == null) state.copy(selection = null, selectionImage = null)
            else state.copy(selection = selection)
        }

        if (selection == null) return

        viewModelScope.launch {
            val image = loadImage(selection.id)

            mutableState.update { state ->
                if (state.selection?.id == selection.id) state.copy(selectionImage = image)
                else state
            }
        }
    }

    /**
     * We keep trying until the images are loaded.
     * In a real app, we would set a maximum number of tries and
     * then notice the user to check the internet connection for example.
     */
    private suspend fun loadPreview(id: String): Bitmap = withContext(Dispatchers.IO) {
        var preview: Bitmap?
        do {
            preview = getPreviewImage(id).getOrNull()
        } while (preview == null)
        preview
    }

    private suspend fun loadImage(id: String): Bitmap = withContext(Dispatchers.IO) {
        var image: Bitmap?
        do {
            image = getImage(id).getOrNull()
        } while (image == null)
        image
    }
}
