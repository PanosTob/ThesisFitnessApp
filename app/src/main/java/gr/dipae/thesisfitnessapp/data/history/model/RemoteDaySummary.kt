package gr.dipae.thesisfitnessapp.data.history.model

import com.google.firebase.firestore.DocumentId
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.workout.model.RemoteWorkout
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.util.ext.toDate

data class RemoteDaySummary(
    @DocumentId
    val id: String = "",
    val steps: Long = 0,
    val dateTime: Long = 0,
    val dailyDiet: RemoteDailyDiet = RemoteDailyDiet(),
    val sportActionsDone: List<RemoteSport> = emptyList(),
    val workoutsDone: List<RemoteWorkout> = emptyList()
) {
    fun toDaySummary(): DaySummary =
        DaySummary(
            id = id,
            steps = steps,
            dateTime = dateTime.toDate(),
            dailyDiet = dailyDiet.toDailyDiet(),
            sportActionsDone = sportActionsDone.map { it.toSport() },
            workoutsDone = workoutsDone.map { it.toWorkout() }
        )
}

data class RemoteDailyDiet(
    val calories: Long = 0,
    val carbohydrates: Double = 0.0,
    val fats: Double = 0.0,
    val proteins: Double = 0.0,
    val waterML: Long = 0
) {
    fun toDailyDiet(): DailyDiet =
        DailyDiet(
            calories = calories,
            carbohydrates = carbohydrates,
            fats = fats,
            proteins = proteins,
            waterML = waterML
        )
}