package gr.dipae.thesisfitnessapp.ui.lobby.home.mapper

import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserDetailsUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItems
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserSportChallengeUiItem
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
            sportChallenges = mapUserSportChallenges(user?.sportChallenges)
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
                goalValue = it.goal.value.toString(),
                progress = it.progress.toFloat(),
                completed = it.completed
            )
        }
    }
}