package gr.dipae.thesisfitnessapp.usecase.user.diet

import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetDailyDietUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase {
    suspend operator fun invoke(date: Long?): DailyDiet? {
        return try {
            userRepository.getDaySummary(date)?.dailyDiet
        } catch (ex: Exception) {
            Timber.tag(GetDailyDietUseCase::class.java.simpleName).e(ex)
            null
        }
    }
}