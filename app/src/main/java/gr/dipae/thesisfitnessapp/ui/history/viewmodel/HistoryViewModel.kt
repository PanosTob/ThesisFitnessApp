package gr.dipae.thesisfitnessapp.ui.history.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
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

    private var daySummaries: MutableList<DaySummary> = mutableListOf()
    private var totalDays: MutableList<Long> = mutableListOf()

    fun init(fromSports: Boolean) {
        launchWithProgress {
            if (startDate != null && endDate != null) {
                daySummaries.addAll(getDaySummariesByRangeUseCase(startDate, endDate))
                if (daySummaries.isNotEmpty()) {
                    totalDays.addAll(calculateDaysBetweenTwoDatesUseCase(startDate, endDate))

                    showHistoryContent(fromSports)
                }
            }
        }
    }

    fun showHistoryContent(fromSports: Boolean) {
        _uiState.value = historyUiMapper(fromSports, daySummaries, totalDays)
    }

    fun showHistoryDialog() {
        _uiState.value?.sportsUiState?.apply {
            if (daySummaries.isNotEmpty()) {
                sportsToFilter.value = daySummaries
                    .mapNotNull { historyUiMapper.mapDaySummaryUiItem(it) }
                    .flatMap { it.sportsDone }
                    .filter { it.sportParameters.any { parameter -> parameter.type == SportParameterType.Distance } }
                    .distinctBy { it.sportId }
                showFilterSportsDialog.value = true
            }
        }
    }

    fun filterHistoryBySport(sportId: String) {
        if (daySummaries.isNotEmpty()) {
            _uiState.value = historyUiMapper.mapBySportId(daySummaries, totalDays, sportId)
        }
    }
}