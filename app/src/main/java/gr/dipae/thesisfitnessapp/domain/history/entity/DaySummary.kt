package gr.dipae.thesisfitnessapp.domain.history.entity

import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout

data class DaySummary(
    val id: String,
    val steps: Long,
    val dateTime: String,
    val dailyDiet: DailyDiet,
    val sportActionsDone: List<Sport>,
    val workoutsDone: List<Workout>
)

data class DailyDiet(
    val calories: Long,
    val carbohydrates: Double,
    val fats: Double,
    val proteins: Double,
    val waterML: Long
)