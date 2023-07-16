package gr.dipae.thesisfitnessapp.domain.user.workout.entity

data class UserWorkout(
    val id: String,
    val name: String,
    val breakSeconds: Long,
    val durationSeconds: Long,
    val userWorkoutExercises: List<UserWorkoutExercise>
)

data class UserWorkoutExercise(
    val id: String,
    val name: String,
    val description: String,
    val repetitions: Long,
    val sets: Long,
    val videoUrl: String
)