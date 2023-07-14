package gr.dipae.thesisfitnessapp.ui.lobby.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.lobby.composable.LobbyContent
import gr.dipae.thesisfitnessapp.ui.lobby.viewmodel.LobbyViewModel
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.LoginRoute
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive

internal const val LobbyRoute = "lobby"

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
fun NavGraphBuilder.lobbyScreen(
    onLobbyShown: () -> Unit
) {
    composable(LobbyRoute) {
        val viewModel: LobbyViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onLobbyShown()
        }

        viewModel.uiState.value?.let {
            LobbyContent(
                uiState = it
            )
        }
    }
}

fun NavController.navigateToLobby() {
    singleNavigateWithPopInclusive(LobbyRoute, LoginRoute)
}