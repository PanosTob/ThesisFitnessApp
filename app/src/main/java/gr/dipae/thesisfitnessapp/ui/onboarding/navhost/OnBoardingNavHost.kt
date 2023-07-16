package gr.dipae.thesisfitnessapp.ui.onboarding.navhost

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.navigateToLobby
import gr.dipae.thesisfitnessapp.ui.splash.navigation.SplashRoute
import gr.dipae.thesisfitnessapp.ui.splash.navigation.splashScreen
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.loginScreen
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.wizardScreen
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun OnBoardingNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        splashScreen {
            navController.singleNavigateWithPopInclusive(it, SplashRoute)
        }
        loginScreen(
            onGoogleSignInClicked = { /*viewModel.onGoogleSignInClicked(it)*/ },
            onLogoutPressed = {}
        )
        wizardScreen {
            navController.navigateToLobby()
        }
    }
}