package gr.dipae.thesisfitnessapp.ui.lobby.home.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.lobby.home.composable.HomeContent
import gr.dipae.thesisfitnessapp.ui.lobby.home.viewmodel.HomeViewModel
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.LoginRoute
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive

internal const val HomeRoute = "home"

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
fun NavGraphBuilder.homeScreen(
    onHomeShown: () -> Unit
) {
    composable(HomeRoute) {
        val viewModel: HomeViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onHomeShown()
        }

        viewModel.uiState.value?.let {
            HomeContent(
                uiState = it
            )
        }
    }
}

fun NavController.navigateToHome() {
    singleNavigateWithPopInclusive(HomeRoute, LoginRoute)
}