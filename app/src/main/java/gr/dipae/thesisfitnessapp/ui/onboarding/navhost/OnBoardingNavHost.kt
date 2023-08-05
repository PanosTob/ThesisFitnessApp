package gr.dipae.thesisfitnessapp.ui.onboarding.navhost

import android.content.IntentSender
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import gr.dipae.thesisfitnessapp.ui.lobby.home.navigation.HomeRoute
import gr.dipae.thesisfitnessapp.ui.lobby.home.navigation.navigateToHome
import gr.dipae.thesisfitnessapp.ui.splash.navigation.SplashRoute
import gr.dipae.thesisfitnessapp.ui.splash.navigation.splashScreen
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.loginScreen
import gr.dipae.thesisfitnessapp.ui.wizard.navigation.wizardScreen

internal typealias OnGoogleSignInClicked = (IntentSender) -> Unit

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun OnBoardingNavHost(
    navController: NavHostController,
    onGoogleSignInClicked: OnGoogleSignInClicked,
    onUserAlreadySignIn: () -> Unit,
    onUserNotSignedIn: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        splashScreen {
            if (it == HomeRoute) {
                onUserAlreadySignIn()
            } else {
                onUserNotSignedIn()
            }
        }
        loginScreen(
            onGoogleSignInClicked = { onGoogleSignInClicked(it) }
        )
        wizardScreen {
            navController.navigateToHome()
        }
    }
}