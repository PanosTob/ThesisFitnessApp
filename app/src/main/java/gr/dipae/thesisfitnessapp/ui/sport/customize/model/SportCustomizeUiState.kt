package gr.dipae.thesisfitnessapp.ui.sport.customize.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.ui.sport.model.SportParameterUiItem

data class SportCustomizeUiState(
    val sportId: String,
    val name: String,
    val parameters: List<SportParameterUiItem>,
    val selectedParameter: MutableState<SportParameterUiItem?> = mutableStateOf(null)
) {
    fun onParameterSelection(parameterUiItem: SportParameterUiItem) {
        parameters.onEach { it.selected.value = false }
        parameters.find { parameterUiItem.name == it.name }?.selected?.value = true
        selectedParameter.value = parameterUiItem
    }
}