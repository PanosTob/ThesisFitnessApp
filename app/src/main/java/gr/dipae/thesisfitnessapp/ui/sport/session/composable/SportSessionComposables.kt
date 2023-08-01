package gr.dipae.thesisfitnessapp.ui.sport.session.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.HorizontalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.sport.session.model.ContinuationState
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

internal typealias OnSessionFinish = () -> Unit
internal typealias OnSportSessionTimerResume = () -> Unit
internal typealias OnSportSessionTimerPause = () -> Unit
internal typealias OnSportSessionTimerStop = () -> Unit

@Composable
fun SportSessionContent(
    uiState: SportSessionUiState,
    onSessionFinish: OnSessionFinish,
    onSportSessionTimerResume: OnSportSessionTimerResume,
    onSportSessionTimerPause: OnSportSessionTimerPause
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
            ) {
                ThesisFitnessHLText(
                    text = uiState.mainTimerValue.value,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 64.sp
                )
                if (uiState.breakTimerValue.value.isNotBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ThesisFitnessHLText(
                            text = stringResource(id = R.string.sport_session_stop_watch_break_timer_label),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 32.sp
                        )
                        HorizontalSpacerDefault()
                        ThesisFitnessHLText(
                            text = uiState.breakTimerValue.value,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 32.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .aspectRatio(1f)
                        .align(Alignment.CenterHorizontally)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable {
                            if (uiState.playBreakBtnState.timerState.value is ContinuationState.Stopped) {
                                onSportSessionTimerResume()
                            } else {
                                onSportSessionTimerPause()
                            }
                        }
                        .padding(SpacingHalf_8dp)
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(uiState.playBreakBtnState.iconRes.value),
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = ""
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(5f)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(SpacingDefault_16dp))
                .clickable { onSessionFinish() }
                .padding(SpacingHalf_8dp),
            contentAlignment = Alignment.Center
        ) {
            ThesisFitnessHLText(
                text = stringResource(R.string.sport_session_button),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}