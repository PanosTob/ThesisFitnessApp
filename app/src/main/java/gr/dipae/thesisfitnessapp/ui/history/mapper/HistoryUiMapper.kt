package gr.dipae.thesisfitnessapp.ui.history.mapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.piechart.models.PieChartData
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerHalf
import gr.dipae.thesisfitnessapp.ui.history.model.DailyDietUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.DaySummaryUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryDietLineChartUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryDietUiState
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportPieChartUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportsUiState
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryUiState
import gr.dipae.thesisfitnessapp.ui.history.model.SportDoneUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.WorkoutDoneUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.WorkoutExerciseDoneUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.mapper.HomeUiMapper
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.openSansFontFamily
import gr.dipae.thesisfitnessapp.util.ext.toDate
import javax.inject.Inject

class HistoryUiMapper @Inject constructor() : Mapper {

    operator fun invoke(fromSports: Boolean, daySummaries: List<DaySummary>): HistoryUiState {

        val daySummaryUiItems = daySummaries.mapNotNull { mapDaySummaryUiItem(it) }
        return HistoryUiState(
            title = @Composable {
                ThesisFitnessBLAutoSizeText(
                    text = stringResource(id = if (fromSports) R.string.history_activities_title else R.string.history_diet_title),
                    maxFontSize = 22.sp,
                    maxLines = 1,
                    style = TextStyle(fontFamily = openSansFontFamily)
                )
            },
            daySummaries = daySummaryUiItems,
            sportsUiState = getSportUiState(fromSports, daySummaries, daySummaryUiItems),
            dietUiState = getDietUiState(fromSports, daySummaries)
        )
    }
    /*operator fun invoke(daySummary: DaySummary?): HistoryUiState {
        return HistoryUiState(
            daySummaries = mapDaySummaryUiItem(daySummary)
        )
    }*/

    private fun getSportUiState(fromSports: Boolean, daySummaries: List<DaySummary>, daySummaryUiItems: List<DaySummaryUiItem>): HistorySportsUiState? {
        if (!fromSports) {
            return null
        }

        val sportParameters = daySummaries.flatMap { summary -> summary.sportsDone.flatMap { it.sportParameters } }
        val totalDuration = toSecondsString(sportParameters.filter { it.type == SportParameterType.Duration }.sumOf { it.value })
        val totalDistanceMeters = sportParameters.filter { it.type == SportParameterType.Distance }.sumOf { it.value }.toString()

        val totalDaysOfSummary = daySummaries.count()
        val totalDistinctSports = daySummaries.flatMap { it.sportsDone }.distinctBy { it.sportId }

        val daySummaryGroups = mutableMapOf<String, List<DaySummary>>()

        totalDistinctSports.forEach { sport ->
            val summaryGroup = daySummaries.filter { it.sportsDone.contains(sport) }
            daySummaryGroups[sport.sportId] = summaryGroup
        }

        return HistorySportsUiState(
            totalTime = totalDuration,
            totalDistance = totalDistanceMeters,
            pieChart = mutableStateOf(
                HistorySportPieChartUiItem(
                    data = populateSportsChartData(daySummaryGroups, totalDaysOfSummary),
                    totalDays = totalDaysOfSummary.toString()
                )
            ),
            sportsDone = mutableStateOf(daySummaryUiItems.flatMap { it.sportsDone })
        )
    }

    private fun populateSportsChartData(groupedSummariesBySport: Map<String, List<DaySummary>>, totalDays: Int): PieChartData {
        return PieChartData(
            slices = groupedSummariesBySport.map {
                PieChartData.Slice(
                    label = SportsMapper.sportNamesMap[it.key] ?: "Unknown",
                    value = it.value.count().toFloat() / totalDays,
                    color = HomeUiMapper.sportColorMap[it.key] ?: ColorPrimary
                )
            },
            plotType = PlotType.Pie
        )
    }

    private fun getDietUiState(fromSports: Boolean, daySummaries: List<DaySummary>): HistoryDietUiState? {
        if (fromSports) {
            return null
        }
        return HistoryDietUiState(
            lineCharts = mutableStateOf(
                listOf(
                    getHistoryDietLineChart(daySummaries, daySummaries.map { Point(x = it.dateTime.toFloat(), y = it.dailyDiet.calories.toFloat()) }, R.string.diet_nutrition_progress_bar_cal),
                    getHistoryDietLineChart(daySummaries, daySummaries.map { Point(x = it.dateTime.toFloat(), y = it.dailyDiet.proteins.toFloat()) }, R.string.diet_nutrition_progress_bar_protein),
                    getHistoryDietLineChart(daySummaries, daySummaries.map { Point(x = it.dateTime.toFloat(), y = it.dailyDiet.carbohydrates.toFloat()) }, R.string.diet_nutrition_progress_bar_carb),
                    getHistoryDietLineChart(daySummaries, daySummaries.map { Point(x = it.dateTime.toFloat(), y = it.dailyDiet.fats.toFloat()) }, R.string.diet_nutrition_progress_bar_fats),
                    getHistoryDietLineChart(daySummaries, daySummaries.map { Point(x = it.dateTime.toFloat(), y = it.dailyDiet.waterML.toFloat()) }, R.string.diet_nutrition_progress_bar_water),
                )
            )
        )
    }

    private fun getHistoryDietLineChart(daySummaries: List<DaySummary>, dataPoints: List<Point>, titleRes: Int): HistoryDietLineChartUiItem {
        val xAxisData: (Int) -> String = { i ->
            if (i in daySummaries.indices) {
                daySummaries[i].dateTime.toDate()
            } else {
                "unknown date"
            }
        }

        val yAxisData: (Int) -> String = { i ->
            val yScale = dataPoints.maxOf { it.y } / dataPoints.size
            (i * yScale).formatToSinglePrecision()
        }

        return HistoryDietLineChartUiItem(
            titleRes = titleRes,
            points = dataPoints,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
        )
    }

    private fun mapDaySummaryUiItem(daySummary: DaySummary?): DaySummaryUiItem? {
        return daySummary?.let {
            DaySummaryUiItem(
                steps = it.steps.toString(),
                calories = it.calories.toString(),
                date = it.dateTime.toDate(),
                dailyDiet = mapDaySummaryDailyDiet(it.dailyDiet),
                sportsDone = mapSportsDone(it.sportsDone, daySummary.dateTime.toDate()),
                workoutsDone = mapWorkoutsDone(it.workoutsDone)
            )
        }
    }

    private fun mapDaySummaryDailyDiet(dailyDiet: DailyDiet): DailyDietUiItem {
        return DailyDietUiItem(
            calories = dailyDiet.calories.toString(),
            carbohydrates = dailyDiet.carbohydrates.toString(),
            fats = dailyDiet.fats.toString(),
            proteins = dailyDiet.proteins.toString(),
            waterML = dailyDiet.waterML.toString()
        )
    }

    private fun mapSportsDone(sportsDone: List<SportDone>, date: String): List<SportDoneUiItem> {
        return sportsDone.map { sport ->
            SportDoneUiItem(
                id = sport.id,
                date = date,
                sportId = sport.sportId,
                sportName = SportsMapper.sportNamesMap[sport.sportId] ?: "Unknown",
                parameters = sport.sportParameters,
                text = @Composable {
                    sport.sportParameters.forEach {
                        ThesisFitnessHLAutoSizeText(
                            text = stringResource(
                                id = if (it.type == SportParameterType.Duration) R.string.history_activities_sport_done_duration else R.string.history_activities_sport_done_distance,
                                it.value
                            )
                        )
                        VerticalSpacerHalf()
                    }
                }
            )
        }
    }

    private fun mapWorkoutsDone(workoutsDone: List<WorkoutDone>): List<WorkoutDoneUiItem> {
        return workoutsDone.map {
            WorkoutDoneUiItem(
                id = it.id,
                name = it.name,
                durationSeconds = it.durationSeconds,
                breakSeconds = it.breakSeconds,
                exercisesDone = mapWorkoutExercisesDone(it.exercisesDone)
            )
        }
    }

    private fun mapWorkoutExercisesDone(workoutDoneExercises: List<WorkoutExerciseDone>): List<WorkoutExerciseDoneUiItem> {
        return workoutDoneExercises.map {
            WorkoutExerciseDoneUiItem(
                id = it.id,
                name = it.name,
                description = it.description,
                repetitions = it.repetitions,
                sets = it.sets,
                videoUrl = it.videoUrl,
                completed = it.completed
            )
        }
    }

    fun toSecondsString(minutes: Long): String {
        if (minutes == 0L) {
            return "-"
        }
        val hours = ((minutes / (1000 * 60 * 60)).mod(24))

        return "${String.format("%02d", hours)} Hours and ${String.format("%02d", minutes)} minutes"
    }
}