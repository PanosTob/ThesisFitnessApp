package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {

    suspend operator fun invoke(): List<Sport>? {
        return try {
            repository.getSports()
        } catch (ex: Exception) {
            Timber.tag(GetSportsUseCase::class.simpleName).e(ex)
            null
        }
    }
}