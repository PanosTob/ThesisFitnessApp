package gr.dipae.thesisfitnessapp.usecase.user.sport

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import okio.IOException
import timber.log.Timber
import javax.inject.Inject

class SetUserFavoriteSportsUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(favoriteSports: List<String>?) {
        try {
            favoriteSports ?: throw IOException("favoriteSports was null")

            repository.setFavoriteSportIds(favoriteSports)
        } catch (ex: Exception) {
            Timber.tag(SetUserFavoriteSportsUseCase::class.simpleName.toString()).e(ex)
        }
    }
}