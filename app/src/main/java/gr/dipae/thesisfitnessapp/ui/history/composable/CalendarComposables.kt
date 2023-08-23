package gr.dipae.thesisfitnessapp.ui.history.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.util.ext.toDate

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HistoryDateRangeBottomSheet(
    modifier: Modifier,
    onDismiss: () -> Unit,
    onDateRangePicked: (Long?, Long?) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        onDismissRequest = {
            onDismiss()
        },
        scrimColor = Color.Black.copy(alpha = 0.5f),
        shape = RoundedCornerShape(topStart = SpacingDefault_16dp, topEnd = SpacingDefault_16dp)
    ) {
        HistoryDateRangePicker { startDate, endDate -> onDateRangePicked(startDate, endDate) }
    }
}


@ExperimentalMaterial3Api
@Composable
fun HistoryDateRangePicker(
    onDateRangePicked: (Long?, Long?) -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState().apply {
        DateRangePicker(
            state = this,
            dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
            dateValidator = { true },
            title = {
                ThesisFitnessHLAutoSizeText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.history_date_picker_activity_title),
                    maxLines = 1,
                    maxFontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            },
            headline = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpacingDefault_16dp)
                ) {
                    Box(Modifier.weight(1f)) {
                        (if (selectedStartDateMillis != null) selectedStartDateMillis?.toDate() else stringResource(id = R.string.history_date_picker_start_date))?.let {
                            ThesisFitnessBMText(
                                text = it
                            )
                        }
                    }
                    Box(Modifier.weight(1f)) {
                        (if (selectedEndDateMillis != null) selectedEndDateMillis?.toDate() else stringResource(id = R.string.history_date_picker_end_date))?.let {
                            ThesisFitnessBMText(
                                text = it
                            )
                        }
                    }
                    Box(Modifier.weight(0.2f)) {
                        Icon(modifier = Modifier.clickable {
                            onDateRangePicked(selectedStartDateMillis, selectedEndDateMillis)
                        }, imageVector = Icons.Default.Done, contentDescription = "")
                    }
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = Color.Black,
                titleContentColor = Color.Black,
                headlineContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                yearContentColor = Color.Black,
                currentYearContentColor = Color.Black,
                selectedYearContainerColor = Color.Black,
                disabledDayContentColor = Color.Black,
                todayDateBorderColor = MaterialTheme.colorScheme.primary,
                dayInSelectionRangeContainerColor = Color.Black,
                dayInSelectionRangeContentColor = Color.Black,
                selectedDayContainerColor = Color.Black
            )
        )
    }
}