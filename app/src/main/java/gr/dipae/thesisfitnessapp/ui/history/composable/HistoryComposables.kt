package gr.dipae.thesisfitnessapp.ui.history.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryLineChartUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryMovementUiState
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportPieChartUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportsLineCharsUiState
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportsUiState
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryUiState
import gr.dipae.thesisfitnessapp.ui.history.model.SportDoneUiItem
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

@Composable
fun HistoryContent(
    uiState: HistoryUiState,
    onSportFilterClicked: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp)
    ) {
        when {
            uiState.emptyView -> {
                HistoryEmptyView()
            }

            uiState.sportsUiState != null -> {
                HistorySportsContent(uiState.sportsUiState)

                if (uiState.sportsUiState.showFilterSportsDialog.value) {
                    HistoryFilterSportsDialog(
                        uiState.sportsUiState.sportsToFilter.value,
                        onCloseClick = { uiState.sportsUiState.showFilterSportsDialog.value = false },
                        onSportClicked = { onSportFilterClicked(it) }
                    )
                }
            }

            uiState.dietUiState != null -> {
                HistoryDietContent(uiState.dietUiState.lineCharts.value)
            }
        }
    }
}

@Composable
fun HistoryEmptyView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ThesisFitnessHLText(
            text = stringResource(id = R.string.history_empty_view_title),
            fontSize = 26.sp
        )
    }
}

@Composable
fun HistoryDietContent(
    item: List<HistoryLineChartUiItem>
) {
    ThesisFitnessBLAutoSizeText(
        text = stringResource(id = R.string.history_diet_title),
        maxFontSize = 22.sp,
        maxLines = 1,
    )
    VerticalSpacerDefault()
    item.forEach {
        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = it.titleRes),
            maxLines = 1,
            maxFontSize = 24.sp,
            color = MaterialTheme.colorScheme.surface
        )
        val m3ChartStyle = m3ChartStyle(
            axisGuidelineColor = MaterialTheme.colorScheme.primary
        )
        val chartEntryModel = entryModelOf(it.points)

        ProvideChartStyle(chartStyle = remember { m3ChartStyle }) {
            Chart(
                autoScaleUp = AutoScaleUp.None,
                chart = lineChart(
                    persistentMarkers = it.points.associate { point -> point.x to rememberMarker() }
                ),
                model = chartEntryModel,
                startAxis = rememberStartAxis(
                    axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape)
                ),
                bottomAxis = rememberBottomAxis(
                    axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape),
                    valueFormatter = { value, _ -> it.xAxisLabelMap[value] ?: "" }
                ),
                marker = rememberMarker()
            )
        }

        VerticalSpacerDefault()
        WidthAdjustedDivider(Modifier.fillMaxWidth())
        VerticalSpacerDefault()
    }
}

@Composable
fun HistorySportsContent(
    uiState: HistorySportsUiState
) {
    ThesisFitnessBLAutoSizeText(
        text = stringResource(id = R.string.history_activities_title),
        maxFontSize = 22.sp,
        maxLines = 1,
    )
    VerticalSpacerDefault()

    HistoryGenericSportDetails(uiState)

    VerticalSpacerDefault()
    VerticalSpacerDefault()

    HistoryMovementLineChart(uiState.movement)
    HistorySportsPieChart(uiState.pieChart.value)
    HistorySportsDistanceLineChart(uiState.lineCharts.value)

    VerticalSpacerDefault()
    WidthAdjustedDivider(Modifier.fillMaxWidth())
    VerticalSpacerDefault()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        ThesisFitnessHMAutoSizeText(text = stringResource(id = R.string.history_activities_records), color = Color.White, maxFontSize = 22.sp)
        VerticalSpacerDefault()

        uiState.sportsDone.value.forEach {
            HistorySportDoneItem(it)
        }
    }
}

@Composable
fun HistorySportDoneItem(
    item: SportDoneUiItem
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(SpacingHalf_8dp))
            .border(BorderStroke(2.dp, item.sportColor), RoundedCornerShape(SpacingHalf_8dp))
            .clip(RoundedCornerShape(SpacingHalf_8dp))
            .padding(SpacingDefault_16dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ThesisFitnessHMAutoSizeText(text = item.sportName, color = item.sportColor, maxFontSize = 24.sp)
                item.statisticsText.invoke()
            }
            Column(
                Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item.goalParameterText.invoke()
            }
        }
    }
}

@Composable
fun HistorySportsPieChart(
    item: HistorySportPieChartUiItem?
) {
    if (item?.data != null) {
        ThesisFitnessBMAutoSizeText(
            text = stringResource(id = R.string.history_activities_pie_chart_total_days, item.totalDays),
            maxLines = 1,
            maxFontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
        )
        VerticalSpacerDefault()

        PieChart(
            modifier = Modifier
                .fillMaxWidth(),
            pieChartData = item.data,
            pieChartConfig = PieChartConfig(
                labelVisible = true,
                isAnimationEnable = true,
                showSliceLabels = true,
                animationDuration = 1500,
                backgroundColor = MaterialTheme.colorScheme.background,
                sliceLabelTextSize = 12.sp,
            )
        )
    }
}

@Composable
fun HistoryMovementLineChart(
    movement: HistoryMovementUiState?
) {
    if (movement != null) {
        val m3ChartStyle = m3ChartStyle(axisGuidelineColor = MaterialTheme.colorScheme.primary)
        val stepsChartEntryModel = entryModelOf(movement.stepsLineChart.points)
        val caloriesChartEntryModel = entryModelOf(movement.caloricBurnLineChart.points)

        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = movement.stepsLineChart.titleRes),
            maxLines = 1,
            maxFontSize = 24.sp,
            color = MaterialTheme.colorScheme.surface
        )
        ProvideChartStyle(chartStyle = remember { m3ChartStyle }) {
            Chart(
                autoScaleUp = AutoScaleUp.None,
                chart = lineChart(
                    persistentMarkers = movement.stepsLineChart.points.associate { point -> point.x to rememberMarker() }
                ),
                model = stepsChartEntryModel,
                startAxis = rememberStartAxis(
                    axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape)
                ),
                bottomAxis = rememberBottomAxis(
                    axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape),
                    valueFormatter = { value, _ -> movement.stepsLineChart.xAxisLabelMap[value] ?: "" }
                ),
                marker = rememberMarker()
            )
        }

        VerticalSpacerDefault()
        WidthAdjustedDivider(Modifier.fillMaxWidth())
        VerticalSpacerDefault()

        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = movement.caloricBurnLineChart.titleRes),
            maxLines = 1,
            maxFontSize = 24.sp,
            color = MaterialTheme.colorScheme.surface
        )
        ProvideChartStyle(chartStyle = remember { m3ChartStyle }) {
            Chart(
                autoScaleUp = AutoScaleUp.None,
                chart = lineChart(
                    persistentMarkers = movement.caloricBurnLineChart.points.associate { point -> point.x to rememberMarker() }
                ),
                model = caloriesChartEntryModel,
                startAxis = rememberStartAxis(
                    axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape)
                ),
                bottomAxis = rememberBottomAxis(
                    axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape),
                    valueFormatter = { value, _ -> movement.caloricBurnLineChart.xAxisLabelMap[value] ?: "" }
                ),
                marker = rememberMarker()
            )
        }

        VerticalSpacerDefault()
        WidthAdjustedDivider(Modifier.fillMaxWidth())
        VerticalSpacerDefault()
    }
}

@Composable
fun HistorySportsDistanceLineChart(
    sportsDistanceLineCharts: HistorySportsLineCharsUiState?
) {
    if (sportsDistanceLineCharts != null) {
        ThesisFitnessBLAutoSizeText(
            text = stringResource(id = R.string.history_activities_pie_chart_total_days, sportsDistanceLineCharts.totalDays),
            maxFontSize = 22.sp,
            maxLines = 1,
        )
        VerticalSpacerDefault()
        sportsDistanceLineCharts.lineCharts.value.forEach {
            ThesisFitnessHLAutoSizeText(
                text = stringResource(id = it.titleRes),
                maxLines = 1,
                maxFontSize = 24.sp,
                color = MaterialTheme.colorScheme.surface
            )
            val m3ChartStyle = m3ChartStyle(
                axisGuidelineColor = MaterialTheme.colorScheme.primary
            )
            val chartEntryModel = entryModelOf(it.points)

            ProvideChartStyle(chartStyle = remember { m3ChartStyle }) {
                Chart(
                    autoScaleUp = AutoScaleUp.None,
                    chart = lineChart(
                        persistentMarkers = it.points.associate { point -> point.x to rememberMarker() }
                    ),
                    model = chartEntryModel,
                    startAxis = rememberStartAxis(
                        axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape)
                    ),
                    bottomAxis = rememberBottomAxis(
                        axis = lineComponent(color = MaterialTheme.colorScheme.outline, thickness = 2.dp, shape = Shapes.rectShape),
                        valueFormatter = { value, _ -> it.xAxisLabelMap[value] ?: "" }
                    ),
                    marker = rememberMarker()
                )
            }

            VerticalSpacerDefault()
            WidthAdjustedDivider(Modifier.fillMaxWidth())
            VerticalSpacerDefault()
        }
    }
}

@Composable
fun HistoryGenericSportDetails(
    uiState: HistorySportsUiState
) {
    Column(
        Modifier
            .fillMaxWidth()
            .aspectRatio(2.5f)
    ) {
        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = R.string.history_activities_generic_details, uiState.generalDetailsTitle.invoke()),
            maxLines = 1,
            maxFontSize = 18.sp,
            color = MaterialTheme.colorScheme.surface
        )
        VerticalSpacerDefault()
        Row(Modifier.fillMaxWidth()) {
            HistorySportsGenericDetail(
                modifier = Modifier.weight(0.5f),
                iconRes = R.drawable.ic_duration,
                title = stringResource(id = R.string.history_activities_total_time),
                value = uiState.totalTime
            )
            HistorySportsGenericDetail(
                modifier = Modifier.weight(0.5f),
                iconRes = R.drawable.ic_distance,
                title = stringResource(id = R.string.history_activities_total_distance),
                value = uiState.totalDistance
            )
        }
    }
}

@Composable
fun HistorySportsGenericDetail(
    modifier: Modifier,
    iconRes: Int,
    title: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingHalf_8dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
        ThesisFitnessBMAutoSizeText(
            text = title,
            maxLines = 1,
            maxFontSize = 16.sp
        )
        ThesisFitnessBMAutoSizeText(
            text = value,
            maxLines = 1,
            maxFontSize = 16.sp
        )
    }
}