package gr.dipae.thesisfitnessapp.usecase.sport

import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class GetUserPreviousLocationUseCase @Inject constructor(
    private val sportsRepository: SportsRepository
) : UseCase {
    operator fun invoke(): LatLng {
        return sportsRepository.getUserPreviousLocation()
    }
}