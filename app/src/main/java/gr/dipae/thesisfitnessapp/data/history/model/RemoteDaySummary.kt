package gr.dipae.thesisfitnessapp.data.history.model

import com.google.firebase.firestore.DocumentId
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDone
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDoneDetails
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import gr.dipae.thesisfitnessapp.util.ext.toDate
import java.util.Date

data class RemoteDaySummary(
    @DocumentId
    val id: String = "",
    val steps: Long = 0,
    val date: Date = Date(),
    val dailyDiet: RemoteDailyDiet = RemoteDailyDiet(),
    val sportsDone: List<RemoteSportDone> = emptyList(),
    val workoutsDone: List<RemoteWorkoutDone> = emptyList()
) {
    fun toDaySummary(): DaySummary =
        DaySummary(
            id = id,
            steps = steps,
            dateTime = date.time.toDate(),
            dailyDiet = dailyDiet.toDailyDiet(),
            sportsDone = sportsDone.map { it.toSportDone() },
            workoutsDone = workoutsDone.map { it.toWorkoutDone() }
        )
}

data class RemoteDailyDiet(
    val calories: Long = 0,
    val protein: Double = 0.0,
    val carbohydrates: Double = 0.0,
    val fats: Double = 0.0,
    val water: Double = 0.0
) {
    fun toDailyDiet(): DailyDiet =
        DailyDiet(
            calories = calories,
            carbohydrates = carbohydrates,
            fats = fats,
            proteins = protein,
            waterML = water
        )
}

data class RemoteSportDone(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val details: RemoteSportDoneDetails = RemoteSportDoneDetails()
) {
    fun toSportDone() =
        SportDone(
            id = id,
            name = name,
            details = details.toSportDoneDetails()
        )
}

data class RemoteSportDoneDetails(
    val distanceMeters: Long = 0,
    val durationSeconds: Long = 0
) {
    fun toSportDoneDetails() =
        SportDoneDetails(
            distanceMeters = distanceMeters,
            durationSeconds = durationSeconds
        )
}

data class RemoteWorkoutDone(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val durationSeconds: Long = 0,
    val breakSeconds: Long = 0,
    val exercisesDone: List<RemoteWorkoutExerciseDone> = emptyList()
) {
    fun toWorkoutDone() =
        WorkoutDone(
            id = id,
            name = name,
            durationSeconds = durationSeconds,
            breakSeconds = breakSeconds,
            exercisesDone = exercisesDone.map { it.toWorkoutExerciseDone() }
        )
}

data class RemoteWorkoutExerciseDone(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val repetitions: Long = 0,
    val sets: Long = 0,
    val videoUrl: String = "",
    val completed: Boolean = false
) {
    fun toWorkoutExerciseDone() =
        WorkoutExerciseDone(
            id = id,
            name = name,
            description = description,
            repetitions = repetitions,
            sets = sets,
            videoUrl = videoUrl,
            completed = completed
        )
}