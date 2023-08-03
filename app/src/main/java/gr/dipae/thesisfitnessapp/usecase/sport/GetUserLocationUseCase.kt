package gr.dipae.thesisfitnessapp.usecase.sport

import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val sportsRepository: SportsRepository
) : UseCase {

    operator fun invoke(): Flow<LatLng> {
        return sportsRepository.getUserLocation().filter { it.longitude > 0 && it.latitude > 0 }
    }
}