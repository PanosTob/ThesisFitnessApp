package gr.dipae.thesisfitnessapp.usecase.login

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import java.util.Calendar
import javax.inject.Inject

class isGoogleSignInBlockedUseCase @Inject constructor(
    private val repository: UserRepository,
    private val enableGoogleSignInUseCase: EnableGoogleSignInUseCase
) : UseCase {

    suspend operator fun invoke(): Boolean {
        val lastTimeStamp = repository.getGoogleSignInBlockedTime()

        val nowTimeStamp = Calendar.getInstance().timeInMillis
        val isPassed24HourBlockage = nowTimeStamp - lastTimeStamp > ONE_DAY_IN_MILLIS
        if (isPassed24HourBlockage) {
            enableGoogleSignInUseCase()
        }
        return !isPassed24HourBlockage
    }

    companion object {
        private const val ONE_DAY_IN_MILLIS = 86400000
    }
}