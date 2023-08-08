package gr.dipae.thesisfitnessapp.ui.diet.macros.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.ui.diet.macros.composables.OnSaveClicked
import gr.dipae.thesisfitnessapp.ui.theme.ColorDisabledButton

data class MacrosDialogUiState(
    val protein: MutableState<String> = mutableStateOf(""),
    val carbs: MutableState<String> = mutableStateOf(""),
    val fats: MutableState<String> = mutableStateOf(""),
    val water: MutableState<String> = mutableStateOf(""),
    val saveButton: MacrosDialogSaveButton = MacrosDialogSaveButton()
) {
    fun updateSaveButtonEnabledState() {
        saveButton.isEnabled.value = protein.value.isNotBlank() || carbs.value.isNotBlank() || fats.value.isNotBlank() || water.value.isNotBlank()
    }
}

data class MacrosDialogSaveButton(
    val isEnabled: MutableState<Boolean> = mutableStateOf(false)
) {
    val color = @Composable {
        if (isEnabled.value) MaterialTheme.colorScheme.primary else ColorDisabledButton
    }

    fun onClick(callBack: OnSaveClicked) {
        if (isEnabled.value) callBack()
    }
}