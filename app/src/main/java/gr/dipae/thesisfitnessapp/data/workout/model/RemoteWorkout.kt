package gr.dipae.thesisfitnessapp.data.workout.model

import com.google.firebase.firestore.DocumentId
import gr.dipae.thesisfitnessapp.domain.workout.entity.Exercise
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout

data class RemoteWorkout(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val durationSeconds: Long = 0,
    val breakSeconds: Long = 0,
    val exercises: List<RemoteExercise> = emptyList()
) {
    fun toWorkout(): Workout =
        Workout(
            name = name,
            durationSeconds = durationSeconds,
            breakSeconds = breakSeconds,
            exercises = exercises.map { it.toExercise() }
        )
}

data class RemoteExercise(
    val name: String = "",
    val description: String = "",
    val repetitions: Long = 0,
    val sets: Long = 0,
    val videoUrl: String = "",
    val completed: Boolean = false
) {
    fun toExercise(): Exercise =
        Exercise(
            name = name,
            description = description,
            repetitions = repetitions,
            sets = sets,
            videoUrl = videoUrl,
            completed = completed
        )
}