package gr.dipae.thesisfitnessapp.data.sport.location.composable

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

internal typealias OnLocationPermissionsAccepted = () -> Unit

@ExperimentalPermissionsApi
@Composable
fun LocationPermissionsLifecycleStateObserver(
    permissionStates: MultiplePermissionsState
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}

@ExperimentalPermissionsApi
@Composable
fun LocationPermissionsHandler(
    onLocationPermissionsAccepted: OnLocationPermissionsAccepted
) {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(permissionStates.allPermissionsGranted) {
        if (permissionStates.allPermissionsGranted) {
            onLocationPermissionsAccepted()
        }
    }

    LocationPermissionsLifecycleStateObserver(permissionStates)
}