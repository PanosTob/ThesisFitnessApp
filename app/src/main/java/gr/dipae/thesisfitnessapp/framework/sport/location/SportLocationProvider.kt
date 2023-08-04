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
import timber.log.Timber

class SportLocationProvider(private val context: Context) {

    private val client by lazy { LocationServices.getFusedLocationProviderClient(context) }

    private var _userPreviousLocation: LatLng = LatLng(0.0, 0.0)
    val userPreviousLocation: LatLng
        get() = _userPreviousLocation

    private val _userLiveLocation: MutableStateFlow<LatLng> = MutableStateFlow(LatLng(0.0, 0.0))
    val userLiveLocation = _userLiveLocation.asStateFlow()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Timber.tag(SportLocationProvider::class.java.toString()).i("New Location: Lat - ${locationResult.lastLocation?.latitude}, Long - ${locationResult.lastLocation?.longitude}")

            locationResult.lastLocation?.let {
                _userLiveLocation.value = LatLng(it.latitude, it.longitude)
            }
        }
    }

    fun setUserPreviousLocation(location: LatLng) {
        Timber.tag(SportLocationProvider::class.java.toString()).i("Previous Location: Lat - ${location.latitude}, Long - ${location.longitude}")
        _userPreviousLocation = location
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

