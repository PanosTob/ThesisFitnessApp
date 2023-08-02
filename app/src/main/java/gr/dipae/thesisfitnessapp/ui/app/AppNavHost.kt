package gr.dipae.thesisfitnessapp.ui.app

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.diet.navigation.dietScreen
import gr.dipae.thesisfitnessapp.ui.history.navigation.historyScreen
import gr.dipae.thesisfitnessapp.ui.lobby.composable.LobbyBottomNavItem
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.LobbyRoute
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.lobbyScreen
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.navigateToLobby
import gr.dipae.thesisfitnessapp.ui.onboarding.navigation.OnBoardingNavHostRoute
import gr.dipae.thesisfitnessapp.ui.onboarding.navigation.onBoardingNavHost
import gr.dipae.thesisfitnessapp.ui.profile.navigation.ProfileRoute
import gr.dipae.thesisfitnessapp.ui.profile.navigation.profileScreen
import gr.dipae.thesisfitnessapp.ui.sport.customize.navigation.navigateToSportCustomize
import gr.dipae.thesisfitnessapp.ui.sport.customize.navigation.sportCustomizeScreen
import gr.dipae.thesisfitnessapp.ui.sport.navigation.SportsRoute
import gr.dipae.thesisfitnessapp.ui.sport.navigation.sportsScreen
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.navigateToSportSession
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.sportSessionScreen
import gr.dipae.thesisfitnessapp.ui.theme.ColorBottomNavBar
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.LoginRoute
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.navigateToWizard
import gr.dipae.thesisfitnessapp.ui.workout.navigation.workoutScreen
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    viewModel: AppViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.initApp()
    }

    val navController = rememberNavController()

    with(viewModel.appUiState.value) {
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        LaunchedEffect(viewModel, lifecycle) {
            snapshotFlow { navigateToWizard.value }.filterNotNull().filter { it }.flowWithLifecycle(lifecycle).collectLatest {
                navController.navigateToWizard()
            }
        }
        LaunchedEffect(viewModel, lifecycle) {
            snapshotFlow { navigateToLobby.value }.filterNotNull().filter { it }.flowWithLifecycle(lifecycle).collectLatest {
                navController.navigateToLobby()
            }
        }
        LaunchedEffect(viewModel, lifecycle) {
            snapshotFlow { navigateBackToLogin.value }.filterNotNull().filter { it }.flowWithLifecycle(lifecycle).collectLatest {
                navController.navigateBackToLogin()
            }
        }

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
                        .padding(top = paddingValues.calculateTopPadding() + SpacingDefault_16dp),
                    navController = navController,
                    startDestination = OnBoardingNavHostRoute
                ) {
                    onBoardingNavHost(
                        onGoogleSignInClicked = { viewModel.onGoogleSignInClicked(it) },
                        onUserAlreadySignIn = { navController.navigateToLobby() }
                    )

                    lobbyScreen(
                        onLobbyShown = {
                            viewModel.showLoggedInUi()
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
                            viewModel.startStopWatch()
                        },
                        popBackToSports = {
                            navController.popBackStack(route = SportsRoute, inclusive = false)
                        },
                        onSportSessionTimerResume = { viewModel.startStopWatch() },
                        onSportSessionTimerPause = { viewModel.pauseStopWatch() },
                        onSportSessionTimerStop = { viewModel.stopStopWatch() }
                    )
                    dietScreen(
                        onDietShown = { viewModel.showDietTopBar() }
                    )
                    profileScreen(
                        onProfileShown = { viewModel.showProfileTopBar() },
                        onLogout = { viewModel.logoutUser() }
                    )

                    historyScreen()
                }
            },
            bottomBar = {
                val bottomNavItems by remember(viewModel.appUiState.value.bottomAppBarItems.value) { mutableStateOf(viewModel.appUiState.value.bottomAppBarItems.value) }
                if (bottomNavItems.isNotEmpty()) {
                    BottomAppBar(
                        containerColor = ColorBottomNavBar
                    ) {
                        bottomNavItems.forEach {
                            LobbyBottomNavItem(item = it) {
                                navController.singleNavigateWithPopInclusive(it.route, LobbyRoute)
                                viewModel.appUiState.value.onBottomAppBarItemSelection(it)
                                viewModel.updateTopBarTitle(it.route)
                            }
                        }
                    }
                }
            }
        )
    }
}

fun NavController.navigateBackToLogin() {
    if (currentDestination?.route != LoginRoute) {
        val navOptions = NavOptions.Builder().setPopUpTo(ProfileRoute, true).setEnterAnim(R.anim.slide_in_left).build()
        navigate(LoginRoute, navOptions)
    }
}