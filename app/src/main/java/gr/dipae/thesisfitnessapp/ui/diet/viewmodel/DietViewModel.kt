package gr.dipae.thesisfitnessapp.ui.diet.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.data.diet.broadcast.DailyDietBroadcast
import gr.dipae.thesisfitnessapp.domain.user.entity.DietGoal
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.mapper.DietLobbyUiMapper
import gr.dipae.thesisfitnessapp.ui.diet.model.DietLobbyUiState
import gr.dipae.thesisfitnessapp.usecase.user.diet.FetchDailyDietUseCase
import gr.dipae.thesisfitnessapp.usecase.user.diet.GetDietGoalUseCase
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val fetchDailyDietUseCase: FetchDailyDietUseCase,
    private val getDietGoalUseCase: GetDietGoalUseCase,
    private val dailyDietBroadcast: DailyDietBroadcast,
    private val dietLobbyUiMapper: DietLobbyUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<DietLobbyUiState?> = mutableStateOf(null)
    val uiState: State<DietLobbyUiState?> = _uiState

    private var dietGoal: DietGoal? = null

    fun init(date: Long? = null) {
        launchWithProgress {
            dietGoal = getDietGoalUseCase()
            fetchDailyDietUseCase(date)
        }
        launch {
            dailyDietBroadcast.dailyDietState.collectLatest {
                _uiState.value = dietLobbyUiMapper(it, dietGoal, date)
            }
        }
    }
}