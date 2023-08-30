package gr.dipae.thesisfitnessapp.ui.lobby.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.navigation.DietRoute
import gr.dipae.thesisfitnessapp.ui.lobby.home.navigation.HomeRoute
import gr.dipae.thesisfitnessapp.ui.lobby.model.BottomAppBarUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.model.LobbyUiState
import gr.dipae.thesisfitnessapp.ui.lobby.model.NavigationIconUiItem
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

    fun handleBarsForHome() {
        _uiState.value.topBarState.actionIcons.value = emptyList()
        _uiState.value.topBarState.isVisible.value = false
        showBottomNavigation()
    }

    fun handleBarsForSports(onEditClicked: () -> Unit, onEditDoneClicked: () -> Unit, onCalendarClicked: () -> Unit, statusBarState: Boolean) {
        if (statusBarState) {
            _uiState.value.topBarState.actionIcons.value = listOf(
                TopBarActionUiItem(R.drawable.ic_add, onEditDoneClicked),
                TopBarActionUiItem(R.drawable.ic_calendar, onCalendarClicked)
            )
        } else {
            _uiState.value.topBarState.actionIcons.value = listOf(
                TopBarActionUiItem(R.drawable.ic_edit, onEditClicked),
                TopBarActionUiItem(R.drawable.ic_calendar, onCalendarClicked)
            )
        }
        updateTopBarNavigationIcon(null)
        updateTopBarTitle(SportsRoute)
        _uiState.value.topBarState.isVisible.value = true
        showBottomNavigation()
    }

    fun handleBarsForDiet(onCalendarClicked: () -> Unit) {
        updateTopBarNavigationIcon(null)
        _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_calendar, onCalendarClicked))
        _uiState.value.topBarState.isVisible.value = true
        showBottomNavigation()
    }

    fun showSportSessionTopBar(onSportSessionDiscard: () -> Unit, onCheckButtonClicked: () -> Unit, showCheckBtn: Boolean) {
        if (showCheckBtn) {
            _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_check, onCheckButtonClicked, tint = { Color.Green }))
        }
        updateTopBarNavigationIcon(Icons.Filled.Close, onSportSessionDiscard)
    }

    fun showWorkoutTopBar() {
        _uiState.value.topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_calendar))
        _uiState.value.topBarState.isVisible.value = true
    }

    fun showProfileTopBar() {
        _uiState.value.topBarState.actionIcons.value = listOf()
        _uiState.value.topBarState.isVisible.value = false
    }

    fun handleBarsForInnerDestination(onBackButtonPressed: () -> Unit) {
        showInnerLoginTopBar(onBackButtonPressed)
        hideBottomNavigation()
    }

    fun handleBarsForHistory(fromSports: Boolean, onFilterClicked: () -> Unit, onBackButtonPressed: () -> Unit) {
        _uiState.value.apply {
            topBarState.titleRes.value = R.string.empty
            if (fromSports) {
                topBarState.actionIcons.value = listOf(TopBarActionUiItem(R.drawable.ic_filter, onFilterClicked))
            } else {
                topBarState.actionIcons.value = emptyList()
            }
            updateTopBarNavigationIcon(Icons.Filled.ArrowBack, onBackButtonPressed) { Color.White }
            topBarState.isVisible.value = true
        }
        hideBottomNavigation()
    }

    fun showInnerLoginTopBar(onBackButtonPressed: () -> Unit) {
        _uiState.value.apply {
            updateTopBarNavigationIcon(Icons.Filled.ArrowBack, onBackButtonPressed)
            topBarState.isVisible.value = true
            topBarState.titleRes.value = R.string.empty
            topBarState.actionIcons.value = emptyList()
        }
    }

    fun showBottomNavigation() {
        _uiState.value.bottomAppBarItems.value = bottomAppBarUiItems
    }

    fun hideBottomNavigation() {
        _uiState.value.bottomAppBarItems.value = emptyList()
    }

    private fun updateTopBarNavigationIcon(icon: ImageVector?, action: () -> Unit = {}, tint: @Composable () -> Color = @Composable { MaterialTheme.colorScheme.primary }) {
        if (icon == null) {
            _uiState.value.topBarState.navigationItem.value = null
            return
        }
        _uiState.value.topBarState.navigationItem.value = NavigationIconUiItem(icon, action, tint)
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