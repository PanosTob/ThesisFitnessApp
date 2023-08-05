package gr.dipae.thesisfitnessapp.ui.lobby.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class HomeUiState(
    val selectedBottomNavItemIndex: MutableState<Int> = mutableStateOf(0),
    val userDetails: UserDetailsUiItem
)

data class UserDetailsUiItem(
    val username: String
)