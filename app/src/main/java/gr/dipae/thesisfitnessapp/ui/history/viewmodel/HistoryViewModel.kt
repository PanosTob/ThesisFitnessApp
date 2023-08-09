package gr.dipae.thesisfitnessapp.ui.history.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.history.mapper.HistoryUiMapper
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryUiState
import gr.dipae.thesisfitnessapp.usecase.user.history.GetTodaySummaryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTodaySummaryUseCase: GetTodaySummaryUseCase,
    private val historyUiMapper: HistoryUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<HistoryUiState?> = mutableStateOf(null)
    val uiState: State<HistoryUiState?> = _uiState

    fun init() {
        launchWithProgress {
            _uiState.value = historyUiMapper(getTodaySummaryUseCase())
        }
    }
}