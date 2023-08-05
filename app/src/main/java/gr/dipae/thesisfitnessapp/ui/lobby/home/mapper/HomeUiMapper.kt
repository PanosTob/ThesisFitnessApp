package gr.dipae.thesisfitnessapp.ui.lobby.home.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserDetailsUiItem
import javax.inject.Inject

class HomeUiMapper @Inject constructor() : Mapper {

    operator fun invoke(user: User?): HomeUiState {
        return HomeUiState(
            userDetails = UserDetailsUiItem(
                username = user?.name ?: ""
            )
        )
    }
}