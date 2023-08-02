package gr.dipae.thesisfitnessapp.ui.sport.session.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import kotlinx.coroutines.flow.MutableStateFlow

data class SportSessionUiState(
    val sportId: String,
    val mapState: SportSessionUiMapState,
    val goalParameter: SportParameter?,
    val mainTimerValue: MutableState<String> = mutableStateOf(""),
    val breakTimerValue: MutableState<String> = mutableStateOf(""),
    val playBreakBtnState: PlayBreakBtnState = PlayBreakBtnState(),
    val backToLogin: MutableState<Boolean> = mutableStateOf(false)
)

data class SportSessionUiMapState(
    val showMap: Boolean,
    val isInitialized: MutableState<Boolean> = mutableStateOf(false),
    val userLocation: MutableStateFlow<LatLng> = MutableStateFlow(LatLng(0.0, 0.0))
)

data class PlayBreakBtnState(
    val timerState: MutableState<ContinuationState> = mutableStateOf(ContinuationState.Running),
    val iconRes: MutableState<Int> = mutableStateOf(R.drawable.ic_pause)
)

sealed class ContinuationState {
    object Running : ContinuationState()
    object Stopped : ContinuationState()
}