package gr.dipae.thesisfitnessapp.usecase.app

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class UpdateStepCounterUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase {

    suspend operator fun invoke(stepCounterValue: Long) {
        userRepository.updateStepCounterValue(stepCounterValue)
    }
}