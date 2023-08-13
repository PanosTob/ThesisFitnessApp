package gr.dipae.thesisfitnessapp.ui.diet.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.mapper.DietLobbyUiMapper
import gr.dipae.thesisfitnessapp.ui.diet.model.DietLobbyUiState
import gr.dipae.thesisfitnessapp.usecase.user.diet.GetDailyDietUseCase
import gr.dipae.thesisfitnessapp.usecase.user.diet.GetDietGoalUseCase
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val getDailyDietUseCase: GetDailyDietUseCase,
    private val getDietGoalUseCase: GetDietGoalUseCase,
    private val dietLobbyUiMapper: DietLobbyUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<DietLobbyUiState> = mutableStateOf(DietLobbyUiState())
    val uiState: State<DietLobbyUiState> = _uiState

    fun init() {
        launchWithProgress {
            _uiState.value = dietLobbyUiMapper(getDailyDietUseCase(), getDietGoalUseCase())
        }
    }
}