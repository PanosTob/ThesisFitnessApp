package gr.dipae.thesisfitnessapp.ui.app

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.lobby.home.navigation.navigateToHome
import gr.dipae.thesisfitnessapp.ui.lobby.navhost.lobbyNavHost
import gr.dipae.thesisfitnessapp.ui.lobby.navhost.navigateToLobbyNavHost
import gr.dipae.thesisfitnessapp.ui.onboarding.navigation.OnBoardingNavHostRoute
import gr.dipae.thesisfitnessapp.ui.onboarding.navigation.onBoardingNavHost
import gr.dipae.thesisfitnessapp.ui.profile.navigation.ProfileRoute
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.LoginRoute
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.navigateToWizard
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
                navController.navigateToHome()
            }
        }
        LaunchedEffect(viewModel, lifecycle) {
            snapshotFlow { navigateBackToLogin.value }.filterNotNull().filter { it }.flowWithLifecycle(lifecycle).collectLatest {
                navController.navigateBackToLogin()
            }
        }

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            navController = navController,
            startDestination = OnBoardingNavHostRoute
        ) {
            onBoardingNavHost(
                onGoogleSignInClicked = { viewModel.onGoogleSignInClicked(it) },
                onUserAlreadySignIn = { navController.navigateToLobbyNavHost() }
            )
            lobbyNavHost(
                startStopWatch = { viewModel.startStopWatch() },
                pauseStopWatch = { viewModel.pauseStopWatch() },
                stopStopWatch = { viewModel.stopStopWatch() },
                logoutUser = { viewModel.logoutUser() }
            )
        }
    }
}

fun NavController.navigateBackToLogin() {
    if (currentDestination?.route != LoginRoute) {
        val navOptions = NavOptions.Builder().setPopUpTo(ProfileRoute, true).setEnterAnim(R.anim.slide_in_left).build()
        navigate(LoginRoute, navOptions)
    }
}