package gr.dipae.thesisfitnessapp.domain.history.entity

import gr.dipae.thesisfitnessapp.domain.activity.entity.Sport
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout
import java.util.Date

data class DaySummary(
    val steps: Long,
    val date: Date,
    val dailyDiet: DailyDiet,
    val sportActionsDone: List<Sport>,
    val workoutSDone: List<Workout>
)

data class DailyDiet(
    val calories: Long,
    val carbohydrates: Double,
    val fats: Double,
    val proteins: Double,
    val waterML: Long
)