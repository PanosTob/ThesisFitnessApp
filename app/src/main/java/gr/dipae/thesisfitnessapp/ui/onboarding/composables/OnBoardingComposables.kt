package gr.dipae.thesisfitnessapp.ui.onboarding.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import gr.dipae.thesisfitnessapp.ui.onboarding.navhost.OnBoardingNavHost
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@Composable
fun OnBoardingScreen() {
    OnBoardingContent()
}

@Composable
fun OnBoardingContent() {
    val navController = rememberNavController()
    OnBoardingNavHost(
        navController = navController
    )
}

@Composable
@Preview(showBackground = true)
fun OnBoardingScreenPreview() {
    ThesisFitnessAppTheme {
        OnBoardingContent()
    }
}