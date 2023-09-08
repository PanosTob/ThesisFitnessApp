package gr.dipae.thesisfitnessapp.ui.history.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
import gr.dipae.thesisfitnessapp.ui.history.model.SportDoneUiItem
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_12dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp

@Composable
fun HistoryFilterSportsDialog(
    sportsToFilter: List<SportDoneUiItem>,
    onCloseClick: () -> Unit,
    onSportClicked: (String) -> Unit
) {
    Dialog(
        onDismissRequest = { onCloseClick() }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(
                    start = SpacingDefault_16dp,
                    top = SpacingDefault_16dp,
                    end = SpacingDefault_16dp
                )
        ) {
            Image(
                imageVector = Icons.Filled.Close,
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(CircleShape)
                    .clickable { onCloseClick() },
                contentDescription = ""
            )
            ThesisFitnessHLText(
                text = stringResource(id = R.string.history_sports_filter_dialog_title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.background
            )
            VerticalSpacerDefault()
            WidthAdjustedDivider(color = MaterialTheme.colorScheme.background)
            Column(
                modifier = Modifier.padding(top = SpacingQuarter_4dp, bottom = SpacingCustom_12dp),
                verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
            ) {
                sportsToFilter.forEach { item ->
                    ThesisFitnessHLText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSportClicked(item.sportId) }, text = item.sportName, textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}