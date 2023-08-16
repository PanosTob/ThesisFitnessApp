package gr.dipae.thesisfitnessapp.usecase.app

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class GetUserIsRunningStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return try {
            userRepository.getUserIsRunning()
        } catch (ex: Exception) {
            Timber.tag(GetUserIsRunningStatusUseCase::class.java.simpleName).e(ex)
            flowOf()
        }
    }
}