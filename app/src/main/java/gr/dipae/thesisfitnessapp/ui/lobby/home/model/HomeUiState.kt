package gr.dipae.thesisfitnessapp.ui.lobby.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class HomeUiState(
    val selectedBottomNavItemIndex: MutableState<Int> = mutableStateOf(0),
    val userDetails: UserDetailsUiItem,
    val userActivityTracking: UserActivityTrackingUiItem = UserActivityTrackingUiItem()
)

data class UserDetailsUiItem(
    val username: String,
    val imageUrl: String,
    val bodyWeight: String
)

data class UserActivityTrackingUiItem(
    val stepCounter: MutableState<String> = mutableStateOf(""),
    val caloricBurn: MutableState<String> = mutableStateOf("")
)