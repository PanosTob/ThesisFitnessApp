package gr.dipae.thesisfitnessapp.usecase.user.sport

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportsUseCase
import timber.log.Timber
import javax.inject.Inject

class GetUserFavoriteSportsUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(): List<String> {
        return try {
            repository.getFavoriteSportIds()
        } catch (ex: Exception) {
            Timber.tag(GetSportsUseCase::class.simpleName.toString()).e(ex)
            emptyList()
        }
    }
}