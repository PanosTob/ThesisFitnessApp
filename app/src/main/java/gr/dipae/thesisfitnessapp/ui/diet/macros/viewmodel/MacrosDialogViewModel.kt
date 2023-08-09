package gr.dipae.thesisfitnessapp.ui.diet.macros.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
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
                setMacrosDailyUseCase(
                    calories.value,
                    protein.value,
                    carbs.value,
                    fats.value,
                    water.value
                )
            }
        }
    }
}