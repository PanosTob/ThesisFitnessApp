package gr.dipae.thesisfitnessapp.ui.sport.session.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter

data class SportSessionUiState(
    val hasMap: Boolean,
    val showContent: MutableState<Boolean> = mutableStateOf(false),
    val mapState: SportSessionUiMapState,
    val goalParameter: SportParameter?,
    val mainTimerValue: MutableState<String> = mutableStateOf(""),
    val breakTimerValue: MutableState<String> = mutableStateOf(""),
    val playStateBtn: PlayStateButtonUiItem = PlayStateButtonUiItem(),
    val backToLogin: MutableState<Boolean> = mutableStateOf(false)
)

data class SportSessionUiMapState(
    val userLocation: MutableState<LatLng> = mutableStateOf(LatLng(0.0, 0.0))
)

data class PlayStateButtonUiItem(
    val timerState: MutableState<ContinuationState> = mutableStateOf(ContinuationState.Running),
    val iconRes: MutableState<Int> = mutableStateOf(R.drawable.ic_play),
    val isEnabled: MutableState<Boolean> = mutableStateOf(false)
)

sealed class ContinuationState {
    object Running : ContinuationState()
    object Stopped : ContinuationState()
}

sealed class SportInfoType {
    object Map : SportInfoType()
    object Mapless : SportInfoType()
}