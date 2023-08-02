package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetSportByIdUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {

    suspend operator fun invoke(sportId: String?): Sport? {
        return try {
            sportId ?: return null

            repository.getSportById(sportId)
        } catch (ex: Exception) {
            Timber.tag(GetSportByIdUseCase::class.simpleName.toString()).e(ex)
            null
        }
    }
}