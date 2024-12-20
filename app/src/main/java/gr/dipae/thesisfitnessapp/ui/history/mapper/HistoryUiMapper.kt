package gr.dipae.thesisfitnessapp.ui.history.mapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.patrykandpatrick.vico.core.entry.entryOf
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
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
import gr.dipae.thesisfitnessapp.ui.theme.ColorDividerGrey
import gr.dipae.thesisfitnessapp.ui.theme.ColorGold
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.util.DATE
import gr.dipae.thesisfitnessapp.util.ENGLISH_DATE
import gr.dipae.thesisfitnessapp.util.ext.toDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HistoryUiMapper @Inject constructor() : Mapper {

    operator fun invoke(fromSports: Boolean, daySummaries: List<DaySummary>, everyDayDate: List<Long>): HistoryUiState {

        val daySummaryUiItems = daySummaries.mapNotNull { mapDaySummaryUiItem(it) }
        return HistoryUiState(
            daySummaries = daySummaryUiItems,
            sportsUiState = getSportUiState(fromSports, daySummaries, daySummaryUiItems, everyDayDate),
            dietUiState = getDietUiState(fromSports, daySummaries, everyDayDate),
            emptyView = daySummaries.isEmpty()
        )
    }

    private fun getSportUiState(fromSports: Boolean, daySummaries: List<DaySummary>, daySummaryUiItems: List<DaySummaryUiItem>, everyDayDate: List<Long>): HistorySportsUiState? {
        if (!fromSports) {
            return null
        }

        val sportParameters = daySummaries.flatMap { summary -> summary.sportsDone.flatMap { it.sportParameters } }
        val totalDuration = toSecondsString(sportParameters.filter { it.type == SportParameterType.Duration }.sumOf { it.value })
        val totalDistanceMeters = sportParameters.filter { it.type == SportParameterType.Distance }.sumOf { it.value }.toString()

        val totalDaysOfSummary = everyDayDate.count()
        val totalDistinctSports = daySummaries.flatMap { it.sportsDone }.distinctBy { it.sportId }

        val daySummaryCountBySport = mutableMapOf<String, Int>()

        totalDistinctSports.forEach { sport ->
            val summaryGroup = daySummaries.flatMap { it.sportsDone }.count { it.sportId == sport.sportId }
            daySummaryCountBySport[sport.sportId] = summaryGroup
        }

        daySummaryCountBySport[""] = daySummaries.count { !everyDayDate.contains(it.dateTime) }

        return HistorySportsUiState(
            totalTime = totalDuration,
            totalDistance = totalDistanceMeters,
            pieChart = mutableStateOf(
                HistorySportPieChartUiItem(
                    data = populateSportsChartData(daySummaryCountBySport, totalDaysOfSummary),
                    totalDays = everyDayDate.count().toString()
                )
            ),
            sportsDone = mutableStateOf(daySummaryUiItems.flatMap { it.sportsDone })
        )
    }

    private fun populateSportsChartData(groupedSummariesBySport: Map<String, Int>, totalDays: Int): PieChartData? {
        if (groupedSummariesBySport.isEmpty()) return null


        return PieChartData(
            slices = groupedSummariesBySport.map {
                PieChartData.Slice(
                    label = (SportsMapper.sportNamesMap[it.key] ?: "No Sport") + " - " + it.value + " days",
                    value = it.value.toFloat() / totalDays,
                    color = SportsMapper.sportColorMap[it.key] ?: ColorDividerGrey
                )
            },
            plotType = PlotType.Pie
        )
    }

    private fun getDietUiState(fromSports: Boolean, daySummaries: List<DaySummary>, everyDayDate: List<Long>): HistoryDietUiState? {
        if (fromSports) {
            return null
        }

        val caloriePoints = mapLineChartEntries(everyDayDate, daySummaries) { dailyDiet -> dailyDiet?.calories?.toDouble() }
        val proteinPoints = mapLineChartEntries(everyDayDate, daySummaries) { dailyDiet -> dailyDiet?.proteins }
        val carbohydratesPoints = mapLineChartEntries(everyDayDate, daySummaries) { dailyDiet -> dailyDiet?.carbohydrates }
        val fatsPoints = mapLineChartEntries(everyDayDate, daySummaries) { dailyDiet -> dailyDiet?.fats }
        val waterPoints = mapLineChartEntries(everyDayDate, daySummaries) { dailyDiet -> dailyDiet?.water }

        return HistoryDietUiState(
            lineCharts = mutableStateOf(
                listOf(
                    getHistoryDietLineChart(caloriePoints, R.string.diet_nutrition_progress_bar_cal),
                    getHistoryDietLineChart(proteinPoints, R.string.diet_nutrition_progress_bar_protein),
                    getHistoryDietLineChart(carbohydratesPoints, R.string.diet_nutrition_progress_bar_carb),
                    getHistoryDietLineChart(fatsPoints, R.string.diet_nutrition_progress_bar_fats),
                    getHistoryDietLineChart(waterPoints, R.string.diet_nutrition_progress_bar_water)
                )
            )
        )
    }

    private fun mapLineChartEntries(dates: List<Long>, daySummariesFound: List<DaySummary>, getDietNutritionAction: (DailyDiet?) -> Double?): List<Pair<Float, Float>> {
        return dates.map { date ->
            Pair(
                LocalDate.parse(date.toDate(ENGLISH_DATE)).toEpochDay().toFloat(),
                getDietNutritionAction(daySummariesFound.find { it.dateTime == date }?.dailyDiet)?.toFloat() ?: 0f
            )
        }
    }

    private fun getHistoryDietLineChart(points: List<Pair<Float, Float>>, titleRes: Int): HistoryDietLineChartUiItem {
        val xAxisLabelMap = points.associate { it.first to LocalDate.ofEpochDay(it.first.toLong()).format(DateTimeFormatter.ofPattern(DATE)) }

        return HistoryDietLineChartUiItem(
            titleRes = titleRes,
            points = points.map { entryOf(it.first, it.second) },
            xAxisLabelMap = xAxisLabelMap
        )
    }


    private fun mapDaySummaryUiItem(daySummary: DaySummary?): DaySummaryUiItem? {
        return daySummary?.let {
            DaySummaryUiItem(
                steps = it.steps.toString(),
                date = it.dateTime.toDate(ENGLISH_DATE),
                dailyDiet = mapDaySummaryDailyDiet(it.dailyDiet),
                sportsDone = mapSportsDone(it.sportsDone, daySummary.dateTime),
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
            waterML = dailyDiet.water.toString()
        )
    }

    private fun mapSportsDone(sportsDone: List<SportDone>, date: Long): List<SportDoneUiItem> {
        return sportsDone.sortedByDescending { it.date }.map { sport ->
            val goalParameterAchieved = sport.sportParameters.find { it.type == sport.goalParameter.type }?.let {
                sport.goalParameter.value <= it.value
            } ?: false

            SportDoneUiItem(
                id = sport.id,
                date = date.toDate(),
                sportId = sport.sportId,
                sportColor = SportsMapper.sportColorMap[sport.sportId] ?: ColorPrimary,
                sportName = SportsMapper.sportNamesMap[sport.sportId] ?: "Unknown",
                goalParameterText = @Composable {
                    val text = stringResource(id = R.string.history_activities_sport_goal_parameter) + " " +
                            stringResource(
                                id = if (sport.goalParameter.type == SportParameterType.Duration) R.string.history_activities_sport_done_duration else R.string.history_activities_sport_done_distance,
                                sport.goalParameter.value
                            )
                    ThesisFitnessHLAutoSizeText(
                        text = text,
                        maxFontSize = 18.sp
                    )
                    VerticalSpacerDefault()
                    if (goalParameterAchieved) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ThesisFitnessHLAutoSizeText(
                                text = stringResource(R.string.history_activities_sport_goal_parameter_achieved),
                                maxFontSize = 18.sp,
                                maxLines = 1
                            )
                            Icon(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .aspectRatio(1f),
                                painter = painterResource(id = R.drawable.ic_trophy),
                                contentDescription = null,
                                tint = ColorGold
                            )
                        }
                    }
                },
                statisticsText = @Composable {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        sport.sportParameters.forEach {
                            ThesisFitnessHLAutoSizeText(
                                text = stringResource(
                                    id = if (it.type == SportParameterType.Duration) R.string.history_activities_sport_done_duration else R.string.history_activities_sport_done_distance,
                                    it.value
                                ),
                                maxFontSize = 18.sp,
                                color = MaterialTheme.colorScheme.surface
                            )
                            VerticalSpacerHalf()
                        }
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