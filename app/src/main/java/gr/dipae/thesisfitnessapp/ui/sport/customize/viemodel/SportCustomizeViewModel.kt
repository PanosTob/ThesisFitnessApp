package gr.dipae.thesisfitnessapp.ui.sport.customize.viemodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportSessionSaveResult
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.sport.customize.mapper.SportCustomizeUiMapper
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.sport.customize.navigation.SportCustomizeArgumentKeys
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportByIdUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.InitializeSportSessionUseCase
import javax.inject.Inject

@HiltViewModel
class SportCustomizeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSportByIdUseCase: GetSportByIdUseCase,
    private val initializeSportSessionUseCase: InitializeSportSessionUseCase,
    private val sportCustomizeUiMapper: SportCustomizeUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<SportCustomizeUiState?> = mutableStateOf(null)
    val uiState: State<SportCustomizeUiState?> = _uiState

    private val sportId = savedStateHandle.get<String>(SportCustomizeArgumentKeys.first())!!
    fun init() {
        launchWithProgress {
            _uiState.value = sportCustomizeUiMapper(getSportByIdUseCase(sportId))
        }
    }

    fun onStartClicked() {
        _uiState.value?.apply {
            launchWithProgress {
                val result = initializeSportSessionUseCase(sportId, sportCustomizeUiMapper.mapSportParameter(selectedParameter.value))
                if (result is SportSessionSaveResult.Success) {
                    navigateToSportSession.value = sportId
                } else {
                    //TODO something went wrong
                }
            }
        }
    }
}