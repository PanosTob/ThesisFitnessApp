package gr.dipae.thesisfitnessapp.ui.lobby.home.mapper

import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper.Companion.sportColorMap
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserDetailsUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItems
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserSportChallengeUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorDividerGrey
import gr.dipae.thesisfitnessapp.util.ext.toDoubleWithSpecificDecimals
import javax.inject.Inject

class HomeUiMapper @Inject constructor() : Mapper {

    operator fun invoke(user: User?, daySummary: DaySummary?): HomeUiState? {
        if (user == null || daySummary == null) {
            return null
        }
        return HomeUiState(
            userDetails = UserDetailsUiItem(
                username = user.name,
                imageUrl = user.imgUrl,
                bodyWeight = "${user.bodyWeight}kg",
                bodyFatPercent = "${user.bodyFatPercent}%",
                muscleMassPercent = "${user.muscleMassPercent}%",
                dailyStepGoal = user.dailyStepsGoal,
                dailyCaloricBurnGoal = user.dailyCaloricBurnGoal
            ),
            userMovementTracking = UserMovementTrackingUiItems(
                stepsTracking = UserMovementTrackingUiItem(
                    remaining = mutableStateOf(daySummary.steps.toString()),
                    goal = user.dailyStepsGoal.toString(),
                    fulfillmentPercentage = mutableStateOf((daySummary.steps.toFloat() / user.dailyStepsGoal).coerceAtMost(1f))
                ),
                caloriesTracking = UserMovementTrackingUiItem(
                    remaining = mutableStateOf(daySummary.caloriesBurned.toString()),
                    goal = user.dailyCaloricBurnGoal.toString(),
                    fulfillmentPercentage = mutableStateOf((daySummary.caloriesBurned.toFloat() / user.dailyCaloricBurnGoal).coerceAtMost(1f))
                )
            ),
            sportChallenges = mapUserSportChallenges(user.sportChallenges).sortedBy { it.sportId }
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
                goalUnit = if (it.goal.type == SportParameterType.Duration) R.string.home_user_challenge_goal_duration_unit else R.string.home_user_challenge_goal_distance_unit,
                goalAmount = it.goal.value.toString(),
                amount = "${it.progress}/${it.goal.value}",
                progressPercentage = (it.progress.toDouble() / it.goal.value).toDoubleWithSpecificDecimals(2).toFloat().coerceAtMost(1f),
                completed = it.progress.toFloat() == 1f,
                color = sportColorMap[it.sportId] ?: ColorDividerGrey
            )
        }
    }

}