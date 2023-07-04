package gr.dipae.thesisfitnessapp.domain.workout.entity

data class Workout(
    val name: String,
    val durationSeconds: Long,
    val breakSeconds: Long,
    val exercises: List<Exercise>
)

data class Exercise(
    val name: String,
    val description: String,
    val repetitions: Long,
    val sets: Long,
    val videoUrl: String,
    val completed: Boolean
)