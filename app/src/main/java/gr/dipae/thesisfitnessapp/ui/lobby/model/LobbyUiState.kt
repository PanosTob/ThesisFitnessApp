package gr.dipae.thesisfitnessapp.ui.lobby.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LobbyUiState(
    val selectedBottomNavItemIndex: MutableState<Int> = mutableStateOf(0),
    val userDetails: UserDetailsUiItem
)

data class UserDetailsUiItem(
    val username: String
)