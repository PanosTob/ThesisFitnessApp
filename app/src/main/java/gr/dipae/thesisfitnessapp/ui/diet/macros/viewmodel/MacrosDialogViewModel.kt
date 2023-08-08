package gr.dipae.thesisfitnessapp.ui.diet.macros.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.macros.model.MacrosDialogUiState
import javax.inject.Inject

@HiltViewModel
class MacrosDialogViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState: MutableState<MacrosDialogUiState> = mutableStateOf(MacrosDialogUiState())
    val uiState: State<MacrosDialogUiState> = _uiState

    fun init() {

    }

    fun onSaveClicked() {

    }
}