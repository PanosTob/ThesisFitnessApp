package gr.dipae.thesisfitnessapp.usecase.sport

import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val sportsRepository: SportSessionRepository
) : UseCase {

    operator fun invoke(): Flow<LatLng> {
        return sportsRepository.getUserLocation().filterNotNull().filter { it.longitude > 0 && it.latitude > 0 }
    }
}