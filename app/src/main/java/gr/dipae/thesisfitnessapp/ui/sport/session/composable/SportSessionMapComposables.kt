package gr.dipae.thesisfitnessapp.ui.sport.session.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import gr.dipae.thesisfitnessapp.data.sport.location.composable.LocationPermissionsHandler
import gr.dipae.thesisfitnessapp.data.sport.location.composable.OnLocationPermissionsAccepted
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiMapState
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@Composable
fun SportSessionMapContent(
    modifier: Modifier = Modifier,
    mapState: SportSessionUiMapState,
    showContent: Boolean,
    animationVisibility: Boolean,
    onLocationPermissionsAccepted: OnLocationPermissionsAccepted,
    onMapLoaded: OnMapLoaded,
    onMyLocationButtonClick: OnMyLocationButtonClick,
    onStartAnimationComplete: () -> Unit
) {
    LocationPermissionsHandler { onLocationPermissionsAccepted() }
    LottieCountDownAnimationOverlay(
        modifier = modifier,
        animationVisibility = animationVisibility,
        onStartAnimationComplete = { onStartAnimationComplete() }
    )

    if (showContent) {
        val cameraPositionState = sportSessionCameraPosition(mapState.userLocation.collectAsStateWithLifecycle().value)
        val coroutineScope = rememberCoroutineScope()
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(mapToolbarEnabled = false),
            onMyLocationButtonClick = {
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(cameraPositionState.position))
                }
                true
            },
            onMapLoaded = {
                onMapLoaded()
            }
        )
    }
}

@Composable
fun sportSessionCameraPosition(
    userLocation: LatLng
): CameraPositionState {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 14f)
    }

    LaunchedEffect(key1 = userLocation) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLng(userLocation), durationMs = 300)
    }

    return cameraPositionState
}