package gr.dipae.thesisfitnessapp.ui.lobby.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.lobby.home.mapper.HomeUiMapper
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.usecase.user.GetUserDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val homeUiMapper: HomeUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<HomeUiState?> = mutableStateOf(null)
    val uiState: State<HomeUiState?> = _uiState

    fun init() {
        launchWithProgress {
            _uiState.value = homeUiMapper(getUserDetailsUseCase())
        }
    }
}