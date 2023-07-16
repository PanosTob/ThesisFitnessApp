package gr.dipae.thesisfitnessapp.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.onboarding.composables.OnBoardingScreen
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val OnBoardingNavHostRoute = "on_boarding_nav_host"

fun NavGraphBuilder.onBoardingNavHost() {
    composable(OnBoardingNavHostRoute) {
        OnBoardingScreen()
    }
}

fun NavController.navigateToOnBoardingNavHost() {
    singleNavigate(OnBoardingNavHostRoute)
}