package gr.dipae.thesisfitnessapp.ui.lobby.navhost

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.diet.navigation.dietScreen
import gr.dipae.thesisfitnessapp.ui.history.navigation.historyScreen
import gr.dipae.thesisfitnessapp.ui.lobby.composable.LobbyBottomNavItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.navigation.HomeRoute
import gr.dipae.thesisfitnessapp.ui.lobby.home.navigation.homeScreen
import gr.dipae.thesisfitnessapp.ui.lobby.viewModel.LobbyViewModel
import gr.dipae.thesisfitnessapp.ui.onboarding.navigation.OnBoardingNavHostRoute
import gr.dipae.thesisfitnessapp.ui.profile.navigation.profileScreen
import gr.dipae.thesisfitnessapp.ui.sport.customize.navigation.navigateToSportCustomize
import gr.dipae.thesisfitnessapp.ui.sport.customize.navigation.sportCustomizeScreen
import gr.dipae.thesisfitnessapp.ui.sport.navigation.SportsRoute
import gr.dipae.thesisfitnessapp.ui.sport.navigation.sportsScreen
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.navigateToSportSession
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.sportSessionScreen
import gr.dipae.thesisfitnessapp.ui.theme.ColorBottomNavBar
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.workout.navigation.workoutScreen
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive

internal const val LobbyNavHostRoute = "lobby_nav_host"

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.lobbyNavHost(
    startStopWatch: () -> Unit,
    pauseStopWatch: () -> Unit,
    stopStopWatch: () -> Unit,
    logoutUser: () -> Unit
) {
    composable(LobbyNavHostRoute) {
        val viewModel: LobbyViewModel = hiltViewModel()
        val navController = rememberNavController()

        viewModel.uiState.value.apply {
            Scaffold(
                topBar = {
                    if (topBarState.isVisible.value) {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                navigationIconContentColor = MaterialTheme.colorScheme.primary
                            ),
                            navigationIcon = {
                                topBarState.navigationIcon.value?.let {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = it,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            },
                            title = {
                                ThesisFitnessBMText(text = stringResource(id = topBarState.titleRes.value), color = MaterialTheme.colorScheme.primary, fontSize = 22.sp)
                            },
                            actions = {
                                topBarState.actionIcons.value.forEach {
                                    Icon(
                                        modifier = Modifier
                                            .padding(end = SpacingDefault_16dp)
                                            .clickable { it.clickAction() },
                                        painter = painterResource(id = it.icon),
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        )
                    }
                },
                content = { paddingValues ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(top = paddingValues.calculateTopPadding() + SpacingDefault_16dp, bottom = paddingValues.calculateBottomPadding() + SpacingDefault_16dp),
                        navController = navController,
                        startDestination = HomeRoute
                    ) {
                        homeScreen(
                            onHomeShown = {
                                viewModel.showLobbyTopBar()
                            }
                        )
                        workoutScreen(
                            onWorkoutShown = { viewModel.showWorkoutTopBar() }
                        )
                        sportsScreen(
                            onSportsShown = { viewModel.showSportsTopBar() },
                            onSportSelected = { navController.navigateToSportCustomize(it) }
                        )
                        sportCustomizeScreen(
                            onSportCustomizeShown = { viewModel.showInnerLoginTopBar() },
                            onStartClicked = { navController.navigateToSportSession(it) }
                        )
                        sportSessionScreen(
                            onSportSessionShown = {
                                viewModel.showSportSessionTopBar()
                            },
                            popBackToSports = {
                                navController.popBackStack(route = SportsRoute, inclusive = false)
                            },
                            onSportSessionTimerResume = { startStopWatch() },
                            onSportSessionTimerPause = { pauseStopWatch() },
                            onSportSessionTimerStop = { stopStopWatch() }
                        )
                        dietScreen(
                            onDietShown = { viewModel.showDietTopBar() }
                        )
                        profileScreen(
                            onProfileShown = { viewModel.showProfileTopBar() },
                            onLogout = { logoutUser() }
                        )

                        historyScreen()
                    }
                },
                bottomBar = {
                    if (bottomAppBarItems.value.isNotEmpty()) {
                        BottomAppBar(
                            containerColor = ColorBottomNavBar
                        ) {
                            bottomAppBarItems.value.forEach {
                                LobbyBottomNavItem(item = it) {
                                    navController.singleNavigateWithPopInclusive(it.route, HomeRoute)
                                    onBottomAppBarItemSelection(it)
                                    viewModel.updateTopBarTitle(it.route)
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

fun NavController.navigateToLobbyNavHost() {
    singleNavigateWithPopInclusive(LobbyNavHostRoute, OnBoardingNavHostRoute)
}