package gr.dipae.thesisfitnessapp.ui.history.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import co.yml.charts.common.model.Point
import co.yml.charts.ui.piechart.models.PieChartData
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDoneParameter

data class HistoryUiState(
    val title: @Composable () -> Unit,
    val daySummaries: List<DaySummaryUiItem>,
    val sportsUiState: HistorySportsUiState?,
    val dietUiState: HistoryDietUiState?
)

data class HistorySportsUiState(
    val totalTime: String,
    val totalDistance: String,
    val pieChart: MutableState<HistorySportPieChartUiItem?> = mutableStateOf(null),
    val sportsDone: MutableState<List<SportDoneUiItem>> = mutableStateOf(emptyList())
)

data class HistorySportPieChartUiItem(
    val data: PieChartData,
    val totalDays: String,
)

data class HistoryDietUiState(
    val lineCharts: MutableState<List<HistoryDietLineChartUiItem>> = mutableStateOf(emptyList())
)

data class HistoryDietLineChartUiItem(
    val titleRes: Int,
    val points: List<Point>,
    val xAxisData: (Int) -> String,
    val yAxisData: (Int) -> String,
)

data class DaySummaryUiItem(
    val steps: String,
    val calories: String,
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
    val date: String,
    val sportId: String,
    val sportName: String,
    val parameters: List<SportDoneParameter>,
    val text: @Composable () -> Unit
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