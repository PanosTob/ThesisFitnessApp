package gr.dipae.thesisfitnessapp.ui.splash.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.splash.composable.SplashContent
import gr.dipae.thesisfitnessapp.ui.splash.viewmodel.SplashViewModel

internal const val SplashSubscriptionRoute = "splash_subscription"

@ExperimentalComposeUiApi
fun NavGraphBuilder.splashScreen() {
    composable(SplashSubscriptionRoute) {
        val viewModel: SplashViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        viewModel.uiState.value?.let {
            SplashContent(
                uiState = it
            )
        }
    }
}