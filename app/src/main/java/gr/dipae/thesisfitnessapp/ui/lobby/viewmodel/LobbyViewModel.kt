package gr.dipae.thesisfitnessapp.ui.lobby.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.navigation.DietRoute
import gr.dipae.thesisfitnessapp.ui.lobby.home.navigation.HomeRoute
import gr.dipae.thesisfitnessapp.ui.lobby.model.BottomAppBarUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.model.LobbyUiState
import gr.dipae.thesisfitnessapp.ui.lobby.model.TopBarActionUiItem
import gr.dipae.thesisfitnessapp.ui.profile.navigation.ProfileRoute
import gr.dipae.thesisfitnessapp.ui.sport.navigation.SportsRoute
import gr.dipae.thesisfitnessapp.ui.workout.navigation.WorkoutRoute
import gr.dipae.thesisfitnessapp.usecase.app.StartAccelerometerUseCase
import gr.dipae.thesisfitnessapp.usecase.app.StartMonitoringStepsUseCase
import gr.dipae.thesisfitnessapp.usecase.app.StopAccelerometerUseCase
import gr.dipae.thesisfitnessapp.usecase.app.StopMonitoringStepsUseCase
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val startMonitoringStepsUseCase: StartMonitoringStepsUseCase,
    private val startAccelerometerUseCase: StartAccelerometerUseCase,
    private val stopMonitoringStepsUseCase: StopMonitoringStepsUseCase,
    private val stopAccelerometerUseCase: StopAccelerometerUseCase
) : BaseViewModel() {

    private val _uiState: MutableState<LobbyUiState> = mutableStateOf(LobbyUiState())
    val uiState: State<LobbyUiState> = _uiState

    fun showLobbyTopBar() {
        _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_top_burger_menu))
    }

    fun showSportsTopBar(onEditClicked: () -> Unit, onEditDoneClicked: () -> Unit, statusBarState: Boolean) {
        if (statusBarState) {
            _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_edit, onEditClicked), TopBarActionUiItem(R.drawable.ic_calendar))
        } else {
            _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_add, onEditDoneClicked), TopBarActionUiItem(R.drawable.ic_calendar))
        }
    }

    fun showSportSessionTopBar() {
        _uiState.value.topBarState.actionIcons.value = emptyList()
        _uiState.value.topBarState.navigationIcon.value = Icons.Filled.Close

    }

    fun showWorkoutTopBar() {
        _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_calendar))
    }

    fun showDietTopBar() {
        _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_calendar))
    }

    fun showProfileTopBar() {
        _uiState.value.topBarState.actionIcons.value = listOf()
    }

    fun showInnerLoginTopBar() {
        _uiState.value.apply {
            updateTopBarNavigationIcon(Icons.Filled.ArrowBack)
            topBarState.isVisible.value = true
            topBarState.titleRes.value = R.string.empty
            topBarState.actionIcons.value = emptyList()
            bottomAppBarItems.value = emptyList()
        }
    }

    private fun updateTopBarNavigationIcon(icon: ImageVector) {
        _uiState.value.topBarState.navigationIcon.value = icon
    }

    fun updateTopBarTitle(route: String) {
        _uiState.value.topBarState.titleRes.value = routeTitles[route] ?: R.string.empty
    }

    fun startMonitoringMovement() {
        startMonitoringStepsUseCase()
        startAccelerometerUseCase()
    }

    fun stopMonitoringMovement() {
        stopMonitoringStepsUseCase()
        stopAccelerometerUseCase()
    }

    companion object {
        val bottomAppBarUiItems = listOf(
            BottomAppBarUiItem(
                iconRes = R.drawable.ic_home,
                labelStringRes = R.string.home_title,
                route = HomeRoute,
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

        val routeTitles: LinkedHashMap<String, Int> = linkedMapOf(
            HomeRoute to R.string.home_title,
            WorkoutRoute to R.string.workout_title,
            SportsRoute to R.string.sport_title,
            DietRoute to R.string.diet_title,
            ProfileRoute to R.string.profile_title
        )
    }
}