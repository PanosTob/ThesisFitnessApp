package gr.dipae.thesisfitnessapp.ui.history.model

import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.ui.sport.model.SportUiItem
import gr.dipae.thesisfitnessapp.ui.workout.model.WorkoutUiItem

data class HistoryUiState(
    val daySummary: DaySummaryUiItem?
)

data class DaySummaryUiItem(
    val steps: String,
    val date: String,
    val dailyDiet: DailyDietUiItem,
    val sportActionsDone: List<SportUiItem>,
    val workoutsDone: List<WorkoutUiItem>
)

data class DailyDietUiItem(
    val calories: String,
    val carbohydrates: String,
    val fats: String,
    val proteins: String,
    val waterML: String
)

fun DailyDiet.toDailyDietUiItem(): DailyDietUiItem =
    DailyDietUiItem(
        calories = calories.toString(),
        carbohydrates = carbohydrates.toString(),
        fats = fats.toString(),
        proteins = proteins.toString(),
        waterML = waterML.toString()
    )