package gr.dipae.thesisfitnessapp.ui.lobby.home.mapper

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserDetailsUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItems
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserSportChallengeUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorBoxing
import gr.dipae.thesisfitnessapp.ui.theme.ColorClimbing
import gr.dipae.thesisfitnessapp.ui.theme.ColorCycling
import gr.dipae.thesisfitnessapp.ui.theme.ColorDividerGrey
import gr.dipae.thesisfitnessapp.ui.theme.ColorIndoorRunning
import gr.dipae.thesisfitnessapp.ui.theme.ColorJumpingRope
import gr.dipae.thesisfitnessapp.ui.theme.ColorPingPong
import gr.dipae.thesisfitnessapp.ui.theme.ColorRunning
import gr.dipae.thesisfitnessapp.ui.theme.ColorSwimming
import gr.dipae.thesisfitnessapp.ui.theme.ColorWalking
import gr.dipae.thesisfitnessapp.util.ext.toDoubleWithSpecificDecimals
import javax.inject.Inject

class HomeUiMapper @Inject constructor() : Mapper {

    operator fun invoke(user: User?): HomeUiState {
        return HomeUiState(
            userDetails = UserDetailsUiItem(
                username = user?.name ?: "",
                imageUrl = "${user?.imgUrl}",
                bodyWeight = user?.let { "${user.bodyWeight}kg" } ?: "",
                bodyFatPercent = user?.let { "${user.bodyFatPercent}%" } ?: "",
                muscleMassPercent = user?.let { "${user.muscleMassPercent}%" } ?: "",
                dailyStepGoal = user?.dailyStepsGoal ?: 0,
                dailyCaloricBurnGoal = user?.dailyCaloricBurnGoal ?: 0
            ),
            userMovementTracking = UserMovementTrackingUiItems(
                stepsTracking = UserMovementTrackingUiItem(remaining = mutableStateOf(user?.dailyStepsGoal?.toString() ?: "")),
                caloriesTracking = UserMovementTrackingUiItem(remaining = mutableStateOf(user?.dailyCaloricBurnGoal?.toString() ?: ""))
            ),
            sportChallenges = mapUserSportChallenges(user?.sportChallenges).sortedBy { it.sportId }
        )
    }

    private fun mapUserSportChallenges(sportChallenges: List<UserSportChallenge>?): List<UserSportChallengeUiItem> {
        sportChallenges ?: return emptyList()

        return sportChallenges.map {
            UserSportChallengeUiItem(
                sportId = it.sportId,
                sportName = it.sportName,
                sportImgUrl = it.sportImgUrl,
                goalName = it.goal.name,
                goalAmount = it.goal.value.toString(),
                amount = "${it.progress}/${it.goal.value}",
                progressPercentage = (it.progress.toDouble() / it.goal.value).toDoubleWithSpecificDecimals(4).toFloat(),
                completed = it.progress.toFloat() == 1f,
                color = sportColorMap[it.sportId] ?: ColorDividerGrey
            )
        }
    }

    companion object {
        val sportColorMap: LinkedHashMap<String, Color> =
            linkedMapOf(
                "B7i1ymOBSpyug4JpVCUY" to ColorPingPong,
                "DeycT4SHRC7oXICdz8Eu" to ColorIndoorRunning,
                "GQwtuStM2lK3K9HPPbuL" to ColorWalking,
                "YjBbZzm1SjahJnFryDjN" to ColorRunning,
                "hT4SiXXhJNLTZcLh2Ftw" to ColorJumpingRope,
                "khuM7tOiXxq3GAzd6vG7" to ColorCycling,
                "nbLxd82u7iq0vGxzFsf5" to ColorBoxing,
                "rOGkpF0m5WxCxWOhwQg9" to ColorClimbing,
                "w3S0w3zexZ75j0vm2ziK" to ColorSwimming,
            )
    }
}