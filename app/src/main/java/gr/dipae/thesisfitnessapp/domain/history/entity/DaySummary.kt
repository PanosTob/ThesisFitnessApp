package gr.dipae.thesisfitnessapp.domain.history.entity

import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType

data class DaySummary(
    val id: String,
    val steps: Long,
    val caloriesBurned: Long,
    val dateTime: Long,
    val dailyDiet: DailyDiet,
    val sportsDone: List<SportDone>,
    val workoutsDone: List<WorkoutDone>
)

data class DailyDiet(
    val calories: Long,
    val carbohydrates: Double,
    val fats: Double,
    val proteins: Double,
    val water: Double
)

data class SportDone(
    val id: String,
    val sportId: String,
    val breakTime: Long,
    val date: Long,
    val sportParameters: List<SportDoneParameter>,
    val goalParameter: SportDoneParameter
)

data class SportDoneParameter(
    val type: SportParameterType,
    val name: String,
    val value: Long
)

data class WorkoutDone(
    val id: String,
    val name: String,
    val durationSeconds: Long,
    val breakSeconds: Long,
    var exercisesDone: List<WorkoutExerciseDone>
)

data class WorkoutExerciseDone(
    val id: String,
    val name: String,
    val description: String,
    val repetitions: Long,
    val sets: Long,
    val videoUrl: String,
    val completed: Boolean
)