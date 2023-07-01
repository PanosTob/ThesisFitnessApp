package gr.dipae.thesisfitnessapp.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gr.dipae.thesisfitnessapp.ui.activity.navigation.activityScreen
import gr.dipae.thesisfitnessapp.ui.diet.navigation.dietScreen
import gr.dipae.thesisfitnessapp.ui.history.navigation.historyScreen
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.lobbyScreen
import gr.dipae.thesisfitnessapp.ui.splash.navigation.SplashRoute
import gr.dipae.thesisfitnessapp.ui.splash.navigation.splashScreen
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.loginScreen
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.navigateToLogin
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.navigateToWizard
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.wizardScreen
import gr.dipae.thesisfitnessapp.ui.workout.navigation.workoutScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    viewModel: AppViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.initApp()
    }

    val navController = rememberNavController()

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { viewModel.navigateToWizard.value }.filterNotNull().collectLatest {
            navController.navigateToWizard()
        }
    }
    NavHost(navController = navController, startDestination = SplashRoute) {
        splashScreen {
            navController.navigateToLogin()
        }
        wizardScreen()

        lobbyScreen()
        workoutScreen()
        activityScreen()
        dietScreen()

        historyScreen()
        loginScreen(
            onGoogleSignInClicked = { viewModel.onGoogleSignInClicked(it) }
        )
    }
}