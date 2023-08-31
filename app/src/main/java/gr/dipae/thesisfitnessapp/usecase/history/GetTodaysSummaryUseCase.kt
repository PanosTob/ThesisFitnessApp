package gr.dipae.thesisfitnessapp.usecase.history

import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetTodaysSummaryUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase {
    suspend operator fun invoke(): DaySummary? {
        return try {
            userRepository.getDaySummary()
        } catch (ex: Exception) {
            Timber.tag(GetTodaysSummaryUseCase::class.java.simpleName).e(ex)
            null
        }
    }
}