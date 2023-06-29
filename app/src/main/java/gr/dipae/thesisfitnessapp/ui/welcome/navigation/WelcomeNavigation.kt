package gr.dipae.thesisfitnessapp.ui.welcome.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.welcome.composable.WelcomeContent
import gr.dipae.thesisfitnessapp.ui.welcome.viewmodel.WelcomeViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate


private const val WelcomeRoute = "welcome"

@ExperimentalComposeUiApi
fun NavGraphBuilder.welcomeScreen() {
    composable(WelcomeRoute) {
        val viewModel: WelcomeViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        WelcomeContent(
            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToWelcome() {
    singleNavigate(WelcomeRoute)
}