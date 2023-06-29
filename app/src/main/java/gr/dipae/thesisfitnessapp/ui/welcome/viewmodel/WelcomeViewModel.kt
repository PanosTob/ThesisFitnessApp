package gr.dipae.thesisfitnessapp.ui.welcome.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.welcome.model.WelcomeUiState
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(

) : BaseViewModel() {

    private val _uiState = mutableStateOf(WelcomeUiState())
    val uiState: State<WelcomeUiState> = _uiState

    fun init() {

    }

}