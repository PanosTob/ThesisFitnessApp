package gr.dipae.thesisfitnessapp.framework.sport.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SportLocationProvider(private val context: Context) {

    private val client by lazy { LocationServices.getFusedLocationProviderClient(context) }

    private var _userLastLocation: LatLng = LatLng(0.0, 0.0)
    val userLastLocation = _userLastLocation

    private val _userLiveLocation: MutableStateFlow<LatLng> = MutableStateFlow(LatLng(0.0, 0.0))
    val userLiveLocation = _userLiveLocation.asStateFlow()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.lastOrNull()?.let {
                _userLastLocation = LatLng(it.longitude, it.longitude)
            }

            locationResult.lastLocation?.let {
                _userLiveLocation.update { LatLng(it.longitude, it.longitude) }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startUserLocationUpdates(locationUpdateIntervalMillis: Long) {
        client.requestLocationUpdates(
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, locationUpdateIntervalMillis).build(),
            locationCallback,
            Looper.getMainLooper()
        )
    }


    fun stopTracking() {
        client.removeLocationUpdates(locationCallback)
    }
}

