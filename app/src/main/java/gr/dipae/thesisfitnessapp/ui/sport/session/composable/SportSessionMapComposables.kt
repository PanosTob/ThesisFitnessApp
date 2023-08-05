package gr.dipae.thesisfitnessapp.ui.sport.session.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import gr.dipae.thesisfitnessapp.data.sport.location.composable.LocationPermissionsHandler
import gr.dipae.thesisfitnessapp.data.sport.location.composable.OnLocationPermissionsAccepted
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiMapState

@ExperimentalPermissionsApi
@Composable
fun SportSessionMapContent(
    modifier: Modifier = Modifier,
    mapState: SportSessionUiMapState,
    showContent: Boolean,
    onLocationPermissionsAccepted: OnLocationPermissionsAccepted,
    onMapLoaded: OnMapLoaded,
    onMyLocationButtonClick: OnMyLocationButtonClick,
) {
    LocationPermissionsHandler { onLocationPermissionsAccepted() }
    if (showContent && mapState.userLocation.value != null) {
        val cameraPositionState = sportSessionCameraPosition(mapState.userLocation.value)
        val userRoute = remember { mutableStateListOf(mapState.userLocation.value) }
        LaunchedEffect(key1 = mapState.userLocation.value) {
            userRoute.add(mapState.userLocation.value)
        }

        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(mapToolbarEnabled = false),
            content = {
                Polyline(points = userRoute.toList().filterNotNull(), color = Color.Black)
            }
        )

    }
}

@Composable
fun sportSessionCameraPosition(
    userLocation: LatLng?
): CameraPositionState {

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(key1 = userLocation) {
        if (userLocation != null) {
            cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(userLocation, 17f)), 1000)
        }
    }

    return cameraPositionState
}