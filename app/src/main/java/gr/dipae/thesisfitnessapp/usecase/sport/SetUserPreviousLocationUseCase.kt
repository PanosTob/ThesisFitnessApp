package gr.dipae.thesisfitnessapp.usecase.sport

import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class SetUserPreviousLocationUseCase @Inject constructor(
    private val sportsRepository: SportSessionRepository
) : UseCase {
    operator fun invoke(location: LatLng) {
        sportsRepository.setUserPreviousLocation(location)
    }
}