package gr.dipae.thesisfitnessapp.ui.sport.customize.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SportCustomizeUiState(
    val sportId: String,
    val name: String,
    val parameters: List<SportParameterUiItem>,
    val selectedParameter: MutableState<SportParameterUiItem?> = mutableStateOf(null),
    val navigateToSportSession: MutableState<Pair<String, String>?> = mutableStateOf(null)
) {
    fun onParameterSelection(parameterUiItem: SportParameterUiItem) {
        parameters.onEach { it.selected.value = false }
        parameters.find { parameterUiItem.name == it.name }?.selected?.value = true
        selectedParameter.value = parameterUiItem
    }
}

data class SportParameterUiItem(
    val name: String,
    val parameterValue: MutableState<String> = mutableStateOf(""),
    @DrawableRes val iconRes: Int,
    val selected: MutableState<Boolean> = mutableStateOf(false)
)