package gr.dipae.thesisfitnessapp.ui.app

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.diet.navigation.dietScreen
import gr.dipae.thesisfitnessapp.ui.history.navigation.historyScreen
import gr.dipae.thesisfitnessapp.ui.lobby.composable.LobbyBottomNabItem
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.LobbyRoute
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.lobbyScreen
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.navigateToLobby
import gr.dipae.thesisfitnessapp.ui.profile.navigation.ProfileRoute
import gr.dipae.thesisfitnessapp.ui.profile.navigation.profileScreen
import gr.dipae.thesisfitnessapp.ui.splash.navigation.SplashRoute
import gr.dipae.thesisfitnessapp.ui.splash.navigation.splashScreen
import gr.dipae.thesisfitnessapp.ui.sport.navigation.sportsScreen
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.LoginRoute
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.loginScreen
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.navigateToWizard
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.wizardScreen
import gr.dipae.thesisfitnessapp.ui.workout.navigation.workoutScreen
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

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
            snapshotFlow { navigateBackToLogin.value }.filterNotNull().filter { it }.flowWithLifecycle(lifecycle).collectLatest {
                navController.navigateBackToLogin()
            }
        }

        Scaffold(
            topBar = {
                if (viewModel.appUiState.value.topBarState.isVisible.value) {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = ColorPrimary,
                            navigationIconContentColor = ColorSecondary
                        ),
                        title = {
                            ThesisFitnessBMText(text = stringResource(id = topBarState.titleRes.value), color = Color.White, fontSize = 22.sp)
                        },
                        actions = {
                            Icon(modifier = Modifier.padding(end = SpacingDefault_16dp), painter = painterResource(id = topBarState.actionIcon.value), contentDescription = "", tint = ColorSecondary)
                        }
                    )
                }
            },
            content = { paddingValues ->
                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScreen.value)
                        .padding(top = paddingValues.calculateTopPadding() + SpacingDefault_16dp),
                    navController = navController,
                    startDestination = SplashRoute
                ) {
                    splashScreen {
                        navController.singleNavigateWithPopInclusive(it, SplashRoute)
                    }
                    loginScreen(
                        onGoogleSignInClicked = { viewModel.onGoogleSignInClicked(it) }
                    )
                    wizardScreen {
                        navController.navigateToLobby()
                    }

                    lobbyScreen(
                        onLobbyShown = {
                            viewModel.showLoggedInUi()
                            viewModel.updateTopBarToBurgerIcon()
                        }
                    )
                    workoutScreen()
                    sportsScreen()
                    dietScreen()
                    profileScreen(
                        onProfileShown = { viewModel.updateToCalenderIcon() },
                        onLogout = { viewModel.logoutUser() }
                    )

                    historyScreen()
                }
            },
            bottomBar = {
                val bottomNavItems by remember(viewModel.appUiState.value.bottomAppBarItems.value) { mutableStateOf(viewModel.appUiState.value.bottomAppBarItems.value) }
                if (bottomNavItems.isNotEmpty()) {
                    BottomAppBar(
                        containerColor = Color.Black
                    ) {
                        bottomNavItems.forEach {
                            LobbyBottomNabItem(item = it) {
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