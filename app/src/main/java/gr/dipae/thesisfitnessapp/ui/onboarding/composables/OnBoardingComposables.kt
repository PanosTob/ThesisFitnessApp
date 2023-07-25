package gr.dipae.thesisfitnessapp.ui.onboarding.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import gr.dipae.thesisfitnessapp.ui.onboarding.navhost.OnBoardingNavHost
import gr.dipae.thesisfitnessapp.ui.onboarding.navhost.OnGoogleSignInClicked
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.navigateToLogin

@Composable
fun OnBoardingScreen(
    onGoogleSignInClicked: OnGoogleSignInClicked,
    onUserAlreadySignIn: () -> Unit
) {
    OnBoardingContent(
        onGoogleSignInClicked = { onGoogleSignInClicked(it) },
        onUserAlreadySignIn = { onUserAlreadySignIn() }
    )
}

@Composable
fun OnBoardingContent(
    onGoogleSignInClicked: OnGoogleSignInClicked = {},
    onUserAlreadySignIn: () -> Unit = {}
) {
    val navController = rememberNavController()
    OnBoardingNavHost(
        navController = navController,
        onGoogleSignInClicked = { onGoogleSignInClicked(it) },
        onUserAlreadySignIn = { onUserAlreadySignIn() },
        onUserNotSignedIn = { navController.navigateToLogin() }
    )
}

@Composable
@Preview(showBackground = true)
fun OnBoardingScreenPreview() {
    ThesisFitnessAppTheme {
        OnBoardingContent()
    }
}