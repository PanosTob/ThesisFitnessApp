package gr.dipae.thesisfitnessapp.ui.welcome.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.welcome.composable.LoginContent
import gr.dipae.thesisfitnessapp.ui.welcome.viewmodel.LoginViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate


private const val LoginRoute = "welcome"

@ExperimentalComposeUiApi
fun NavGraphBuilder.loginScreen() {
    composable(LoginRoute) {
        val viewModel: LoginViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        LoginContent(
            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToLogin() {
    singleNavigate(LoginRoute)
}