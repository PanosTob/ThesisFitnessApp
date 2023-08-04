package gr.dipae.thesisfitnessapp.usecase.sport

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class CalculateTravelledDistanceUseCase @Inject constructor() : UseCase {

    operator fun invoke(userPreviousLocation: LatLng, userNewLocation: LatLng, travelledDistanceSoFar: Double): Double {
        if (userPreviousLocation.latitude == 0.0 && userPreviousLocation.longitude == 0.0) return 0.0
        return travelledDistanceSoFar + SphericalUtil.computeDistanceBetween(userPreviousLocation, userNewLocation)
    }
}