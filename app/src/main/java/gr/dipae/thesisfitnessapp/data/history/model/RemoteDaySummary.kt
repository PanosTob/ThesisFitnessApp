package gr.dipae.thesisfitnessapp.data.history.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class RemoteDaySummary(
    @DocumentId
    val id: String = "",
    val steps: Long = 0,
    val calories: Long = 0,
    val date: Date = Date(),
    val dailyDiet: RemoteDailyDiet = RemoteDailyDiet(),
    var activitiesDone: List<RemoteSportDone> = emptyList(),
    var workoutsDone: List<RemoteWorkoutDone> = emptyList()
)

data class RemoteDailyDiet(
    val calories: Long = 0,
    val protein: Double = 0.0,
    val carbohydrates: Double = 0.0,
    val fats: Double = 0.0,
    val water: Double = 0.0
)

data class RemoteSportDone(
    @DocumentId
    val id: String = "",
    val sportId: String = "",
    val breakTime: Long = 0,
    var activityStatistics: List<RemoteSportDoneParameter> = emptyList()
)

data class RemoteSportDoneParameter(
    val name: String = "",
    val value: Long = 0,
    val achieved: Boolean? = null
)

data class RemoteWorkoutDone(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val durationSeconds: Long = 0,
    val breakSeconds: Long = 0,
    val exercisesDone: List<RemoteWorkoutExerciseDone> = emptyList()
)

data class RemoteWorkoutExerciseDone(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val repetitions: Long = 0,
    val sets: Long = 0,
    val videoUrl: String = "",
    val completed: Boolean = false
)