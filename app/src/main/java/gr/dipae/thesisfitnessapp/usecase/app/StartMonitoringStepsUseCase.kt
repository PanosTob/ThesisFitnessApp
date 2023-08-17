package gr.dipae.thesisfitnessapp.usecase.app

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class StartMonitoringStepsUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase {

    operator fun invoke() {
        userRepository.startMonitoringSteps()
    }
}