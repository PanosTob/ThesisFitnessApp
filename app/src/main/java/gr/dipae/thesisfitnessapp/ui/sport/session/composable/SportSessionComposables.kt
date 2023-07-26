package gr.dipae.thesisfitnessapp.ui.sport.session.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondaryDark
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

internal typealias OnSessionFinish = () -> Unit

@Composable
fun SportSessionContent(
    uiState: SportSessionUiState,
    onSessionFinish: OnSessionFinish
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
        ) {
            ThesisFitnessHLAutoSizeText(
                text = uiState.mainTimerValue.value,
                maxFontSize = 42.sp
            )
        }

        ThesisFitnessHLText(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7f)
                .background(ColorSecondaryDark, RoundedCornerShape(SpacingDefault_16dp))
                .clickable { onSessionFinish() }
                .padding(SpacingHalf_8dp),
            text = stringResource(R.string.sport_session_button),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.background
        )
    }
}