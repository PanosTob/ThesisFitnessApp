package gr.dipae.thesisfitnessapp.ui.sport.customize.model

import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.ui.sport.customize.composable.OnStartClicked
import gr.dipae.thesisfitnessapp.ui.theme.ColorDisabledButton

data class SportCustomizeUiState(
    val sportId: String,
    val name: String,
    val hasMap: Boolean,
    val parameters: List<SportParameterUiItem>,
    val startButton: SportCustomizeStartButtonUiItem = SportCustomizeStartButtonUiItem(),
    val selectedParameter: MutableState<SportParameterUiItem?> = mutableStateOf(null),
    val navigateToSportSession: MutableState<Triple<String, Boolean, String?>?> = mutableStateOf(null)
) {
    fun onParameterSelection(parameterUiItem: SportParameterUiItem) {
        parameters.onEach { it.selected.value = false }
        parameters.find { parameterUiItem.name == it.name }?.selected?.value = true
        selectedParameter.value = parameterUiItem
        updateStartButtonEnabledState()
    }

    fun updateStartButtonEnabledState() {
        with(selectedParameter.value) {
            if (this == null) {
                startButton.enabledState.value = true
            } else {
                startButton.enabledState.value = parameterValue.value.isNotBlank()
            }
        }
    }
}

data class SportCustomizeStartButtonUiItem(
    val enabledState: MutableState<Boolean> = mutableStateOf(true),
) {
    val color = @Composable {
        if (enabledState.value) MaterialTheme.colorScheme.primary else ColorDisabledButton
    }

    fun onClick(callBack: OnStartClicked) {
        if (enabledState.value) callBack()
    }
}

data class SportParameterUiItem(
    val name: String,
    val parameterValue: MutableState<String> = mutableStateOf(""),
    @DrawableRes val iconRes: Int,
    val selected: MutableState<Boolean> = mutableStateOf(false)
)