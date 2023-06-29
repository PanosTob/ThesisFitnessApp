package gr.dipae.thesisfitnessapp.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gr.dipae.thesisfitnessapp.ui.splash.navigation.SplashRoute
import gr.dipae.thesisfitnessapp.ui.splash.navigation.splashScreen
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.navigateToWelcome
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.welcomeScreen

@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    viewModel: AppViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.initApp()
    }

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute) {
        splashScreen {
            navController.navigateToWelcome()
        }
        welcomeScreen()
    }
}