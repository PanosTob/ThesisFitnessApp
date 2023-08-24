package gr.dipae.thesisfitnessapp.data.history.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.history.model.RemoteDailyDiet
import gr.dipae.thesisfitnessapp.data.history.model.RemoteDaySummary
import gr.dipae.thesisfitnessapp.data.history.model.RemoteSportDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteSportDoneParameter
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutExerciseDone
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDone
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDoneParameter
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import javax.inject.Inject

class HistoryMapper @Inject constructor(
    private val sportsMapper: SportsMapper
) : Mapper {

    operator fun invoke(remoteDaySummaries: List<RemoteDaySummary>): List<DaySummary> {
        return remoteDaySummaries.mapNotNull { mapDaySummary(it) }
    }

    fun mapDaySummary(remoteDaySummary: RemoteDaySummary?): DaySummary? {
        remoteDaySummary ?: return null

        remoteDaySummary.apply {
            return DaySummary(
                id = id,
                steps = steps,
                calories = calories,
                dateTime = date.time,
                dailyDiet = mapDailyDiet(dailyDiet),
                sportsDone = activitiesDone.map { it.toSportDone() },
                workoutsDone = workoutsDone.map { it.toWorkoutDone() }
            )
        }
    }

    private fun mapDailyDiet(remoteDailyDiet: RemoteDailyDiet): DailyDiet {
        remoteDailyDiet.apply {
            return DailyDiet(
                calories = calories,
                carbohydrates = carbohydrates,
                fats = fats,
                proteins = protein,
                water = water
            )
        }
    }

    private fun RemoteSportDone.toSportDone() =
        SportDone(
            id = id,
            sportId = activityId,
            breakTime = breakTime,
            date = date.time,
            sportParameters = activityStatistics.map { it.toSportDoneParameter() },
            goalParameter = goalParameter.toSportDoneParameter()
        )

    private fun RemoteSportDoneParameter.toSportDoneParameter() =
        SportDoneParameter(
            type = sportsMapper.mapSportParameterType(name),
            name = name,
            value = value
        )

    private fun RemoteWorkoutDone.toWorkoutDone() =
        WorkoutDone(
            id = id,
            name = name,
            durationSeconds = durationSeconds,
            breakSeconds = breakSeconds,
            exercisesDone = exercisesDone.map { it.toWorkoutExerciseDone() }
        )

    private fun RemoteWorkoutExerciseDone.toWorkoutExerciseDone() =
        WorkoutExerciseDone(
            id = id,
            name = name,
            description = description,
            repetitions = repetitions,
            sets = sets,
            videoUrl = videoUrl,
            completed = completed
        )
}