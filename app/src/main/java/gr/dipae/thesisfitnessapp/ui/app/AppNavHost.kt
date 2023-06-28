package gr.dipae.thesisfitnessapp.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gr.dipae.thesisfitnessapp.ui.splash.navigation.SplashSubscriptionRoute
import gr.dipae.thesisfitnessapp.ui.splash.navigation.splashScreen

@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    viewModel: AppViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.initApp()
    }

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashSubscriptionRoute) {
        splashScreen()
    }
}