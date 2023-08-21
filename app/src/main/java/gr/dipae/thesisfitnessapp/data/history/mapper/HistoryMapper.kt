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
import gr.dipae.thesisfitnessapp.util.ext.toDate
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
                dateTime = date.time.toDate(),
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
                waterML = water
            )
        }
    }

    private fun RemoteSportDone.toSportDone() =
        SportDone(
            id = id,
            sportId = sportId,
            breakTime = breakTime,
            sportParameters = activityStatistics.map { it.toSportDoneParameter() }
        )

    private fun RemoteSportDoneParameter.toSportDoneParameter() =
        SportDoneParameter(
            type = sportsMapper.mapSportParameterType(name),
            name = name,
            value = value,
            achieved = achieved
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