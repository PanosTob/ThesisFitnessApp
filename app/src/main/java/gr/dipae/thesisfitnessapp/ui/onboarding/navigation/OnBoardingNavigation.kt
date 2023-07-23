package gr.dipae.thesisfitnessapp.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.onboarding.composables.OnBoardingScreen
import gr.dipae.thesisfitnessapp.ui.onboarding.navhost.OnGoogleSignInClicked
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val OnBoardingNavHostRoute = "on_boarding_nav_host"

fun NavGraphBuilder.onBoardingNavHost(
    onGoogleSignInClicked: OnGoogleSignInClicked,
    onUserAlreadySignIn: () -> Unit
) {
    composable(OnBoardingNavHostRoute) {
        OnBoardingScreen(
            onGoogleSignInClicked = { onGoogleSignInClicked(it) },
            onUserAlreadySignIn = { onUserAlreadySignIn() }
        )
    }
}

fun NavController.navigateToOnBoardingNavHost() {
    singleNavigate(OnBoardingNavHostRoute)
}