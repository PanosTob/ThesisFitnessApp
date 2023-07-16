package gr.dipae.thesisfitnessapp.domain.history.entity

data class DaySummary(
    val id: String,
    val steps: Long,
    val dateTime: String,
    val dailyDiet: DailyDiet,
    var sportsDone: List<SportDone>,
    var workoutsDone: List<WorkoutDone>
)

data class DailyDiet(
    val calories: Long,
    val carbohydrates: Double,
    val fats: Double,
    val proteins: Double,
    val waterML: Long
)

data class SportDone(
    val id: String,
    val name: String,
    val details: SportDoneDetails
)

data class SportDoneDetails(
    val distanceMeters: Long,
    val durationSeconds: Long
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