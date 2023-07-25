package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportSessionSaveResult
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class InitializeSportSessionUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {

    suspend operator fun invoke(sportId: String, parameter: SportParameter?): SportSessionSaveResult {
        parameter ?: return SportSessionSaveResult.Failure()

        return try {
            repository.initializeSportSession(sportId, parameter)

            return SportSessionSaveResult.Success
        } catch (ex: Exception) {
            Timber.tag(GetSportByIdUseCase::class.simpleName.toString()).e(ex)
            SportSessionSaveResult.Failure(ex)
        }
    }
}