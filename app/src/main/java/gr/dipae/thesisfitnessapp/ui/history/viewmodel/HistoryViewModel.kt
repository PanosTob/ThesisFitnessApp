package gr.dipae.thesisfitnessapp.ui.history.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.history.mapper.HistoryUiMapper
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryUiState
import gr.dipae.thesisfitnessapp.ui.history.navigation.HistoryRouteArgs
import gr.dipae.thesisfitnessapp.usecase.user.history.CalculateDaysBetweenTwoDatesUseCase
import gr.dipae.thesisfitnessapp.usecase.user.history.GetDaySummariesByRangeUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDaySummariesByRangeUseCase: GetDaySummariesByRangeUseCase,
    private val historyUiMapper: HistoryUiMapper,
    private val calculateDaysBetweenTwoDatesUseCase: CalculateDaysBetweenTwoDatesUseCase
) : BaseViewModel() {

    private val _uiState: MutableState<HistoryUiState?> = mutableStateOf(null)
    val uiState: State<HistoryUiState?> = _uiState

    private val startDate = savedStateHandle.get<Long>(HistoryRouteArgs[0])
    private val endDate = savedStateHandle.get<Long>(HistoryRouteArgs[1])

    fun init(fromSports: Boolean) {
        launchWithProgress {
            if (startDate != null && endDate != null) {
                val daySummaries = getDaySummariesByRangeUseCase(startDate, endDate)
                if (daySummaries.isNotEmpty()) {
                    val totalDaysList = calculateDaysBetweenTwoDatesUseCase(startDate, endDate)

                    _uiState.value = historyUiMapper(fromSports, getDaySummariesByRangeUseCase(startDate, endDate), totalDaysList)
                }
            }
        }
    }

    fun filterSportsDone() {

    }
}