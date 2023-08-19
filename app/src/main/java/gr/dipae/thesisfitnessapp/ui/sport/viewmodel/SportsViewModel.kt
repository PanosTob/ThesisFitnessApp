package gr.dipae.thesisfitnessapp.ui.sport.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.sport.mapper.SportsUiMapper
import gr.dipae.thesisfitnessapp.ui.sport.model.SportsUiState
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportsUseCase
import gr.dipae.thesisfitnessapp.usecase.user.sport.SetUserFavoriteSportsUseCase
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val setUserFavoriteSportsUseCase: SetUserFavoriteSportsUseCase,
    private val sportsUiMapper: SportsUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<SportsUiState?> = mutableStateOf(null)
    val uiState: State<SportsUiState?> = _uiState

    fun init() {
        launchWithProgress {
            _uiState.value = sportsUiMapper(getSportsUseCase())
        }
    }

    fun updateEditState(editState: Boolean) {
        _uiState.value?.sports?.forEach { it.editable.value = editState }
    }

    fun setFavoriteSports() {
        launchWithProgress {
            val favoriteSports = getUserNewSelectedFavoriteSports()
            setUserFavoriteSportsUseCase(favoriteSports)
        }
    }

    private fun getUserNewSelectedFavoriteSports(): List<String>? {
        return _uiState.value?.sports?.filter { it.favorite.value }?.map { it.id }
    }
}