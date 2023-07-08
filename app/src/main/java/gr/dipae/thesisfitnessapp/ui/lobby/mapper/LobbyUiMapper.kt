package gr.dipae.thesisfitnessapp.ui.lobby.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.ui.lobby.model.LobbyUiState
import gr.dipae.thesisfitnessapp.ui.lobby.viewmodel.LobbyViewModel.Companion.bottomAppBarUiItems
import javax.inject.Inject

class LobbyUiMapper @Inject constructor() : Mapper {

    operator fun invoke(): LobbyUiState {
        return LobbyUiState(
            username = "Panagiotis Toumpas",
            bottomAppBarItems = bottomAppBarUiItems
        )
    }
}