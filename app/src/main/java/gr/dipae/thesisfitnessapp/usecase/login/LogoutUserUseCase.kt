package gr.dipae.thesisfitnessapp.usecase.login

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.logout.LogoutResult
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {
    suspend operator fun invoke(): LogoutResult {
        return try {
            repository.logoutUser()
            LogoutResult.Success
        } catch (ex: Exception) {
            Timber.tag(LogoutUserUseCase::class.simpleName.toString()).e(ex)
            LogoutResult.Failure(ex)
        }
    }
}