package gr.dipae.thesisfitnessapp.usecase.sport

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class CalculateTravelledDistanceMetersUseCase @Inject constructor() : UseCase {

    operator fun invoke(userPreviousLocation: LatLng, userNewLocation: LatLng, travelledDistanceSoFar: Long): Long {
        if (userPreviousLocation.latitude == 0.0 && userPreviousLocation.longitude == 0.0) return 0
        return (travelledDistanceSoFar + (SphericalUtil.computeDistanceBetween(userPreviousLocation, userNewLocation)).toLong())
    }
}