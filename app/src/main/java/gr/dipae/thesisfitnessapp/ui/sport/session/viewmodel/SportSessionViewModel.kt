package gr.dipae.thesisfitnessapp.ui.sport.session.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.sport.customize.navigation.SportCustomizeArgumentKeys
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import javax.inject.Inject

@HiltViewModel
class SportSessionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState: MutableState<SportSessionUiState?> = mutableStateOf(null)
    val uiState: State<SportSessionUiState?> = _uiState

    fun init() {
        val sportId = savedStateHandle.get<String>(SportCustomizeArgumentKeys.first())!!
        _uiState.value = SportSessionUiState()
    }
}