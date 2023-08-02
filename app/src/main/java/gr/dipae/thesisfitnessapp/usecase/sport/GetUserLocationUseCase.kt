package gr.dipae.thesisfitnessapp.usecase.sport

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val sportsRepository: SportsRepository,
    private val getUserPreviousLocationUseCase: GetUserPreviousLocationUseCase,
    private val setSportSessionDistanceUseCase: SetSportSessionDistanceUseCase
) : UseCase {

    suspend operator fun invoke(): Flow<LatLng> {
        return sportsRepository.getUserLocation().onEach {
            val distance = calculateTravelledDistance(getUserPreviousLocationUseCase(), it)
            setSportSessionDistanceUseCase(distance)
        }
    }

    private fun calculateTravelledDistance(userPreviousLocation: LatLng, userNewLocation: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(userPreviousLocation, userNewLocation)
    }
}