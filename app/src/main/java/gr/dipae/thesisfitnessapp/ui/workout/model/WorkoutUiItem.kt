package gr.dipae.thesisfitnessapp.ui.workout.model

import gr.dipae.thesisfitnessapp.domain.workout.entity.Exercise
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout

data class WorkoutsUiState(
    val workoutList: List<WorkoutUiItem>
)

data class WorkoutUiItem(
    val name: String,
    val durationSeconds: String,
    val breakSeconds: String,
    val exercises: List<ExerciseUiItem>
)

fun Workout.toWorkoutUiItem(): WorkoutUiItem =
    WorkoutUiItem(
        name = name,
        durationSeconds = durationSeconds.toString(),
        breakSeconds = breakSeconds.toString(),
        exercises = exercises.map { it.toExerciseUiItem() }
    )

data class ExerciseUiItem(
    val name: String,
    val description: String,
    val repetitions: String,
    val sets: String,
    val videoUrl: String,
    val completed: Boolean
)

fun Exercise.toExerciseUiItem(): ExerciseUiItem =
    ExerciseUiItem(
        name = name,
        description = description,
        repetitions = repetitions.toString(),
        sets = sets.toString(),
        videoUrl = videoUrl,
        completed = completed
    )