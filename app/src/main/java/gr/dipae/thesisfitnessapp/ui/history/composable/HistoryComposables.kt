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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryDietLineChartUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportPieChartUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportsUiState
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryUiState
import gr.dipae.thesisfitnessapp.ui.history.model.SportDoneUiItem
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDouble_32dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuadruple_64dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp

@Composable
fun HistoryContent(
    uiState: HistoryUiState,
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
    item: List<HistoryDietLineChartUiItem>
) {
    ThesisFitnessBLAutoSizeText(
        text = stringResource(id = R.string.history_diet_title),
        maxFontSize = 22.sp,
        maxLines = 1,
    )
    VerticalSpacerDefault()
    val primaryColor = MaterialTheme.colorScheme.background
    item.forEach {
        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = it.titleRes),
            maxLines = 1,
            maxFontSize = 24.sp,
            color = MaterialTheme.colorScheme.surface
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            lineChartData = LineChartData(
                isZoomAllowed = true,
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = it.points,
                            LineStyle(
                                color = MaterialTheme.colorScheme.primary
                            ),
                            IntersectionPoint { },
                            SelectionHighlightPoint(color = MaterialTheme.colorScheme.background),
                            ShadowUnderLine(color = MaterialTheme.colorScheme.primary, alpha = 1f),
                            SelectionHighlightPopUp(backgroundColor = MaterialTheme.colorScheme.background, labelColor = MaterialTheme.colorScheme.surface, popUpLabel = { _, y -> y.toString() })
                        )
                    ),
                ),
                xAxisData = AxisData.Builder()
                    .steps(it.points.size * 2)
                    .axisLabelColor(MaterialTheme.colorScheme.secondary)
                    .backgroundColor(Color.Transparent)
                    .labelAndAxisLinePadding(SpacingQuarter_4dp)
                    .axisStepSize(SpacingQuadruple_64dp)
                    .axisLabelFontSize(12.sp)
                    .startPadding(SpacingDouble_32dp)
                    .startDrawPadding(SpacingDouble_32dp)
                    .endPadding(SpacingDouble_32dp)
                    .labelData(it.xAxisData).build(),
                yAxisData = AxisData.Builder()
                    .steps(it.points.size)
                    .labelAndAxisLinePadding(SpacingDefault_16dp)
                    .axisLabelFontSize(16.sp)
                    .axisLabelColor(MaterialTheme.colorScheme.primary)
                    .backgroundColor(Color.Transparent)
                    .axisStepSize(SpacingCustom_24dp)
                    .startPadding(SpacingQuarter_4dp)
                    .bottomPadding(SpacingQuadruple_64dp)
                    .labelData(it.yAxisData).build(),
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        )

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
    WidthAdjustedDivider(Modifier.fillMaxWidth())
    VerticalSpacerDefault()

    HistorySportsPieChart(uiState.pieChart.value)

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
fun HistoryGenericSportDetails(
    uiState: HistorySportsUiState
) {
    Column(
        Modifier
            .fillMaxWidth()
            .aspectRatio(2.5f)
    ) {
        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = R.string.history_activities_generic_details),
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