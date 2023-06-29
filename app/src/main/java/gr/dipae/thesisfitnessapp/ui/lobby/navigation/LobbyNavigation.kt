package gr.dipae.thesisfitnessapp.ui.lobby.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.lobby.composable.LobbyContent
import gr.dipae.thesisfitnessapp.ui.lobby.viewmodel.LobbyViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

private const val LobbyRoute = "lobby"

@ExperimentalComposeUiApi
fun NavGraphBuilder.lobbyScreen() {
    composable(LobbyRoute) {
        val viewModel: LobbyViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        LobbyContent(
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToLobby() {
    singleNavigate(LobbyRoute)
}