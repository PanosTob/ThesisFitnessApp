package gr.dipae.thesisfitnessapp.ui.lobby.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.ui.lobby.model.LobbyUiState
import gr.dipae.thesisfitnessapp.ui.lobby.model.UserDetailsUiItem
import javax.inject.Inject

class LobbyUiMapper @Inject constructor() : Mapper {

    operator fun invoke(user: User?): LobbyUiState {
        return LobbyUiState(
            userDetails = UserDetailsUiItem(
                username = user?.name ?: ""
            )
        )
    }
}