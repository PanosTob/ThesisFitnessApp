package gr.dipae.thesisfitnessapp.ui.history.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import co.yml.charts.ui.piechart.models.PieChartData
import com.patrykandpatrick.vico.core.entry.FloatEntry
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDoneParameter

data class HistoryUiState(
    val sportsUiState: HistorySportsUiState?,
    val dietUiState: HistoryDietUiState?,
    val emptyView: Boolean,
    val filteredSport: Boolean = false
)

data class HistorySportsUiState(
    val generalDetailsTitle: @Composable () -> String,
    val movement: HistoryMovementUiState? = null,
    val totalTime: String,
    val totalDistance: String,
    val pieChart: MutableState<HistorySportPieChartUiItem?> = mutableStateOf(null),
    val lineCharts: MutableState<HistorySportsLineCharsUiState?> = mutableStateOf(null),
    val sportsDone: MutableState<List<SportDoneUiItem>> = mutableStateOf(emptyList()),
    val sportsToFilter: MutableState<List<SportDoneUiItem>> = mutableStateOf(emptyList()),
    val showFilterSportsDialog: MutableState<Boolean> = mutableStateOf(false)
)

data class HistoryMovementUiState(
    val stepsLineChart: HistoryLineChartUiItem,
    val caloricBurnLineChart: HistoryLineChartUiItem
)

data class HistorySportPieChartUiItem(
    val data: PieChartData?,
    val totalDays: String,
)

data class HistorySportsLineCharsUiState(
    val totalDays: String,
    val lineCharts: MutableState<List<HistoryLineChartUiItem>> = mutableStateOf(emptyList())
)

data class HistoryDietUiState(
    val lineCharts: MutableState<List<HistoryLineChartUiItem>> = mutableStateOf(emptyList())
)

data class HistoryLineChartUiItem(
    val titleRes: Int,
    val points: List<FloatEntry>,
    val xAxisLabelMap: Map<Float, String>,
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
    val date: String,
    val sportId: String,
    val sportName: String,
    val sportColor: Color,
    val sportParameters: List<SportDoneParameter>,
    val goalParameterText: @Composable () -> Unit,
    val statisticsText: @Composable () -> Unit
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