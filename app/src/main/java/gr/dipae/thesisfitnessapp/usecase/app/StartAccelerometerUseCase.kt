package gr.dipae.thesisfitnessapp.usecase.app

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import javax.inject.Inject

class StartAccelerometerUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() {
        userRepository.startAccelerometer()
    }
}