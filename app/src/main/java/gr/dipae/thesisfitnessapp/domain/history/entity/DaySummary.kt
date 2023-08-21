package gr.dipae.thesisfitnessapp.domain.history.entity

import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType

data class DaySummary(
    val id: String,
    val steps: Long,
    val calories: Long,
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
    val waterML: Double
)

data class SportDone(
    val id: String,
    val sportId: String,
    val breakTime: Long,
    val sportParameters: List<SportDoneParameter>
)

data class SportDoneParameter(
    val type: SportParameterType,
    val name: String,
    val value: Long,
    val achieved: Boolean
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