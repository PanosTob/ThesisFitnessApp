package gr.dipae.thesisfitnessapp.ui.lobby.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

data class HomeUiState(
    val selectedBottomNavItemIndex: MutableState<Int> = mutableStateOf(0),
    val userDetails: UserDetailsUiItem,
    val sportChallenges: List<UserSportChallengeUiItem>,
    val userMovementTracking: UserMovementTrackingUiItems
)

data class UserSportChallengeUiItem(
    val sportId: String,
    val sportName: String,
    val sportImgUrl: String,
    val goalName: String,
    val goalValue: String,
    val completed: Boolean,
    val progress: Float,
    val color: Color
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