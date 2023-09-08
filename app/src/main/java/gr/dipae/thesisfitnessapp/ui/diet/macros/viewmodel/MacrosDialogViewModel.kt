package gr.dipae.thesisfitnessapp.ui.diet.macros.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.diet.entity.SetDailyDietResult
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.macros.model.MacrosDialogUiState
import gr.dipae.thesisfitnessapp.usecase.diet.SetMacrosDailyUseCase
import javax.inject.Inject

@HiltViewModel
class MacrosDialogViewModel @Inject constructor(
    private val setMacrosDailyUseCase: SetMacrosDailyUseCase
) : BaseViewModel() {

    private val _uiState: MutableState<MacrosDialogUiState> = mutableStateOf(MacrosDialogUiState())
    val uiState: State<MacrosDialogUiState> = _uiState

    fun init() {
        _uiState.value.updateSaveButtonEnabledState()
    }

    fun onSaveClicked() {
        launchWithProgress {
            _uiState.value.apply {
                val result = setMacrosDailyUseCase(
                    calories = (calories.value.toLongOrNull() ?: 0),
                    protein = (protein.value.toDoubleOrNull() ?: 0.0),
                    carbs = (carbs.value.toDoubleOrNull() ?: 0.0),
                    fats = (fats.value.toDoubleOrNull() ?: 0.0),
                    water = (water.value.toDoubleOrNull() ?: 0.0)
                )

                if (result is SetDailyDietResult.Success) {
                    saveCompleted.value = true
                }
            }
        }
    }
}