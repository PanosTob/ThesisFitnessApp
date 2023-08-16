package gr.dipae.thesisfitnessapp.usecase.app

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class GetStepCounterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Long> {
        return try {
            userRepository.getStepCounter()
        } catch (ex: Exception) {
            Timber.tag(GetStepCounterUseCase::class.java.simpleName).e(ex)
            flowOf()
        }
    }
}