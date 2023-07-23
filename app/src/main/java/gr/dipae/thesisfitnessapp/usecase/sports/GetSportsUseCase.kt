package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.sport.GetUserFavoriteSportsUseCase
import timber.log.Timber
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    private val repository: SportsRepository,
    private val getUserFavoriteSportsUseCase: GetUserFavoriteSportsUseCase
) : UseCase {

    suspend operator fun invoke(): List<Sport>? {
        return try {
            val favoriteSportIds = getUserFavoriteSportsUseCase()
            repository.getSports(favoriteSportIds)
        } catch (ex: Exception) {
            Timber.tag(GetSportsUseCase::class.simpleName.toString()).e(ex)
            null
        }
    }
}