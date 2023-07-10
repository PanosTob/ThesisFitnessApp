package gr.dipae.thesisfitnessapp.ui.lobby.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.app.model.BottomAppBarUiItem
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.navigation.DietRoute
import gr.dipae.thesisfitnessapp.ui.lobby.mapper.LobbyUiMapper
import gr.dipae.thesisfitnessapp.ui.lobby.model.LobbyUiState
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.LobbyRoute
import gr.dipae.thesisfitnessapp.ui.profile.navigation.ProfileRoute
import gr.dipae.thesisfitnessapp.ui.sport.navigation.SportsRoute
import gr.dipae.thesisfitnessapp.ui.workout.navigation.WorkoutRoute
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val lobbyUiMapper: LobbyUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<LobbyUiState?> = mutableStateOf(null)
    val uiState: State<LobbyUiState?> = _uiState

    fun init() {
        _uiState.value = lobbyUiMapper()
    }

    companion object {
        val bottomAppBarUiItems = listOf(
            BottomAppBarUiItem(
                iconRes = R.drawable.ic_home,
                labelStringRes = R.string.home_title,
                route = LobbyRoute,
                selected = mutableStateOf(true)
            ),
            BottomAppBarUiItem(
                iconRes = R.drawable.ic_sport,
                labelStringRes = R.string.sport_title,
                route = SportsRoute,
                selected = mutableStateOf(false)
            ),
            BottomAppBarUiItem(
                iconRes = R.drawable.ic_workout,
                labelStringRes = R.string.workout_title,
                route = WorkoutRoute,
                selected = mutableStateOf(false)
            ),
            BottomAppBarUiItem(
                iconRes = R.drawable.ic_diet,
                labelStringRes = R.string.diet_title,
                route = DietRoute,
                selected = mutableStateOf(false)
            ),
            BottomAppBarUiItem(
                iconRes = R.drawable.ic_profile,
                labelStringRes = R.string.profile_title,
                route = ProfileRoute,
                selected = mutableStateOf(false)
            )
        )
    }
}