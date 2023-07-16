package gr.dipae.thesisfitnessapp.data.user.workout.model

import com.google.firebase.firestore.DocumentId
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkout
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkoutExercise

data class RemoteUserWorkout(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val breakSeconds: Long = 0,
    val durationSeconds: Long = 0,
    val userWorkoutExercises: List<RemoteUserWorkoutExercise>
) {
    fun toUserWorkout(): UserWorkout =
        UserWorkout(
            id = id,
            name = name,
            breakSeconds = breakSeconds,
            durationSeconds = durationSeconds,
            userWorkoutExercises = userWorkoutExercises.map { it.toUserWorkoutExercise() }
        )
}

data class RemoteUserWorkoutExercise(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val repetitions: Long = 0,
    val sets: Long = 0,
    val videoUrl: String = ""
) {
    fun toUserWorkoutExercise(): UserWorkoutExercise =
        UserWorkoutExercise(
            id = id,
            repetitions = 0,
            name = name,
            description = "",
            sets = 0,
            videoUrl = ""
        )

}

