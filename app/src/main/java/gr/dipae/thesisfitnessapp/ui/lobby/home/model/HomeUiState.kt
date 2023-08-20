package gr.dipae.thesisfitnessapp.ui.lobby.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class HomeUiState(
    val selectedBottomNavItemIndex: MutableState<Int> = mutableStateOf(0),
    val userDetails: UserDetailsUiItem,
    val userMovementTracking: UserMovementTrackingUiItems
)

data class UserDetailsUiItem(
    val username: String,
    val imageUrl: String,
    val bodyWeight: String,
    val muscleMassPercent: String,
    val bodyFatPercent: String,
    val dailyStepGoal: Long,
    val dailyCaloricBurnGoal: Long
)

data class UserMovementTrackingUiItems(
    val stepsTracking: UserMovementTrackingUiItem,
    val caloriesTracking: UserMovementTrackingUiItem
)


data class UserMovementTrackingUiItem(
    val fulfillmentPercentage: MutableState<Float> = mutableStateOf(0f),
    val remaining: MutableState<String>
)