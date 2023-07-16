package gr.dipae.thesisfitnessapp.ui.history.model

data class HistoryUiState(
    val daySummary: DaySummaryUiItem?
)

data class DaySummaryUiItem(
    val steps: String,
    val date: String,
    val dailyDiet: DailyDietUiItem,
    val sportsDone: List<SportDoneUiItem>,
    val workoutsDone: List<WorkoutDoneUiItem>
)

data class DailyDietUiItem(
    val calories: String,
    val carbohydrates: String,
    val fats: String,
    val proteins: String,
    val waterML: String
)

data class SportDoneUiItem(
    val id: String,
    val name: String,
    val details: SportDoneDetailsUiItem
)

data class SportDoneDetailsUiItem(
    val distanceMeters: Long,
    val durationSeconds: Long
)

data class WorkoutDoneUiItem(
    val id: String,
    val name: String,
    val durationSeconds: Long,
    val breakSeconds: Long,
    val exercisesDone: List<WorkoutExerciseDoneUiItem>
)

data class WorkoutExerciseDoneUiItem(
    val id: String,
    val name: String,
    val description: String,
    val repetitions: Long,
    val sets: Long,
    val videoUrl: String,
    val completed: Boolean
)