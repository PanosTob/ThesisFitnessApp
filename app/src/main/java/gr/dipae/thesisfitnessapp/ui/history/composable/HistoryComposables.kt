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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.pie.PieChart
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportPieChartUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistorySportsUiState
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryUiState
import gr.dipae.thesisfitnessapp.ui.history.model.SportDoneUiItem
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.openSansFontFamily

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
        uiState.title.invoke()
        VerticalSpacerDefault()

        when {
            uiState.sportsUiState != null -> {
                HistorySportsContent(uiState.sportsUiState)
            }
        }
    }
}

@Composable
fun HistorySportsContent(
    uiState: HistorySportsUiState
) {
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
            .aspectRatio(3f)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.surface), RoundedCornerShape(SpacingHalf_8dp))
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
                ThesisFitnessHMAutoSizeText(text = item.date, color = Color.White)
                ThesisFitnessHMAutoSizeText(text = item.sportName, color = Color.White)
            }
            Column(
                Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                item.sportId
                item.text.invoke()
            }
        }
    }
}

@Composable
fun HistorySportsPieChart(
    item: HistorySportPieChartUiItem?
) {
    item?.let {
        ThesisFitnessBMAutoSizeText(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = stringResource(id = R.string.history_activities_pie_chart_total_days, it.totalDays),
            maxLines = 1,
            maxFontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            style = TextStyle(fontFamily = openSansFontFamily)
        )

        PieChart(
            modifier = Modifier
                .fillMaxWidth(),
            dataCollection = it.data
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