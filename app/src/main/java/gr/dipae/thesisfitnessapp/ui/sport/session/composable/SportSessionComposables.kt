package gr.dipae.thesisfitnessapp.ui.sport.session.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.sport.location.composable.OnLocationPermissionsAccepted
import gr.dipae.thesisfitnessapp.ui.base.compose.HorizontalSpacerHalf
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionRealTimeDataUiItem
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp

internal typealias OnSportSessionTimerResume = () -> Unit
internal typealias OnSportSessionTimerPause = () -> Unit
internal typealias OnSportSessionTimerStop = () -> Unit
internal typealias OnMapLoaded = () -> Unit
internal typealias OnMyLocationButtonClick = () -> Unit
internal typealias OnStartAnimationComplete = () -> Unit
internal typealias OnStopSession = () -> Unit

@ExperimentalPermissionsApi
@Composable
fun SportSessionContent(
    uiState: State<SportSessionUiState?>,
    onLocationPermissionsAccepted: OnLocationPermissionsAccepted,
    onStartAnimationComplete: OnStartAnimationComplete,
    onStopSession: OnStopSession,
    onMapLoaded: OnMapLoaded,
    onSportSessionTimerResume: OnSportSessionTimerResume,
    onSportSessionTimerPause: OnSportSessionTimerPause,
    onMyLocationButtonClick: OnMyLocationButtonClick,
    onSportSessionShown: () -> Unit
) {
    uiState.value?.apply {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            ThesisFitnessHLText(text = sportTitle, fontSize = 24.sp, maxLines = 1)
            VerticalSpacerDefault()

            var startAnimation by remember { mutableStateOf(false) }
            LottieCountDownAnimationOverlay(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f),
                animationVisibility = startAnimation,
                onStartAnimationComplete = {
                    onStartAnimationComplete()
                    startAnimation = false
                }
            )
            if (hasMap) {
                SportSessionMapContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                    mapState = mapState,
                    showContent = showContent.value,
                    onLocationPermissionsAccepted = { onLocationPermissionsAccepted() },
                    onMapLoaded = { onMapLoaded() },
                    onMyLocationButtonClick = { onMyLocationButtonClick() },
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpacingDefault_16dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showContent.value) {
                    onSportSessionShown()
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
                            ) {
                                SportSessionDurationTime(sportSessionRealTimeDataUiItem)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = SpacingCustom_24dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SportSessionBreakTime(sportSessionRealTimeDataUiItem)
                                    SportSessionRunningPace(sportSessionRealTimeDataUiItem)
                                    SportSessionDistanceTravelled(sportSessionRealTimeDataUiItem)
                                }
                            }
                        }
                    }
                }

                HorizontalSpacerHalf()
                SportSessionControlButtons(
                    uiState = this@apply,
                    onSportSessionTimerResume = { onSportSessionTimerResume() },
                    onSportSessionTimerPause = { onSportSessionTimerPause() },
                    onStartAnimation = { startAnimation = true },
                    onStopSession = { onStopSession() }
                )
            }
        }
    }
}

@Composable
fun SportSessionDurationTime(
    sportSessionRealTimeDataUiItem: SportSessionRealTimeDataUiItem
) {
    ThesisFitnessHLText(
        text = sportSessionRealTimeDataUiItem.duration.value.invoke(),
        color = MaterialTheme.colorScheme.background,
        fontSize = 48.sp,
    )
}

@Composable
fun SportSessionBreakTime(
    sportSessionRealTimeDataUiItem: SportSessionRealTimeDataUiItem
) {
    Column(
        Modifier.fillMaxWidth(0.3f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = R.string.sport_session_stop_watch_break_timer_label),
            color = MaterialTheme.colorScheme.surface,
            maxFontSize = 16.sp,
        )
        HorizontalSpacerHalf()
        ThesisFitnessHLAutoSizeText(
            text = sportSessionRealTimeDataUiItem.breakTime.value.invoke(),
            color = MaterialTheme.colorScheme.surface,
            maxFontSize = 16.sp,
            maxLines = 1,
        )
    }
}

@Composable
fun SportSessionRunningPace(
    sportSessionRealTimeDataUiItem: SportSessionRealTimeDataUiItem
) {
    Column(
        Modifier.fillMaxWidth(0.3f / 0.7f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = R.string.sport_session_pace_label),
            color = MaterialTheme.colorScheme.surface,
            maxFontSize = 16.sp,
        )
        HorizontalSpacerHalf()
        ThesisFitnessHLAutoSizeText(
            text = sportSessionRealTimeDataUiItem.pace.value,
            color = MaterialTheme.colorScheme.surface,
            maxFontSize = 16.sp,
            maxLines = 1,
        )
    }
}

@Composable
fun SportSessionDistanceTravelled(
    sportSessionRealTimeDataUiItem: SportSessionRealTimeDataUiItem
) {
    Column(
        Modifier.fillMaxWidth(0.3f / 0.4f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThesisFitnessHLAutoSizeText(
            text = stringResource(id = R.string.sport_session_distance_label),
            color = MaterialTheme.colorScheme.surface,
            maxFontSize = 16.sp,
        )
        HorizontalSpacerHalf()
        ThesisFitnessHLAutoSizeText(
            text = sportSessionRealTimeDataUiItem.distance.value,
            color = MaterialTheme.colorScheme.surface,
            maxFontSize = 16.sp,
            maxLines = 1,
        )
    }
}

@Composable
fun SportSessionControlButtons(
    uiState: SportSessionUiState,
    onSportSessionTimerResume: OnSportSessionTimerResume,
    onSportSessionTimerPause: OnSportSessionTimerPause,
    onStartAnimation: () -> Unit,
    onStopSession: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = SpacingCustom_24dp), horizontalArrangement = Arrangement.SpaceAround
    ) {
        SportSessionPlayButton(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(1f),
            buttonItem = uiState.playStateBtn,
            sessionStarted = uiState.showContent.value,
            onSportSessionTimerResume = { onSportSessionTimerResume() },
            onSportSessionTimerPause = { onSportSessionTimerPause() },
            onStartAnimation = { onStartAnimation() },
        )

        SportSessionStopButton(
            modifier = Modifier
                .fillMaxWidth(0.25f / 0.75f)
                .aspectRatio(1f),
            isEnabled = uiState.stopBtnEnabled.value,
            onClick = {
                onStopSession()
            }
        )
    }
}