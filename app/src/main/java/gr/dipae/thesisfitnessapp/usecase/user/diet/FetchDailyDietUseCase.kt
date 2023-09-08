package gr.dipae.thesisfitnessapp.usecase.user.diet

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.history.RefreshDailyDietUseCase
import timber.log.Timber
import javax.inject.Inject

class FetchDailyDietUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val refreshDailyDietUseCase: RefreshDailyDietUseCase
) : UseCase {
    suspend operator fun invoke(date: Long?) {
        return try {
            val dailyDiet = userRepository.getDaySummary(date)?.dailyDiet
            refreshDailyDietUseCase(dailyDiet)
        } catch (ex: Exception) {
            Timber.tag(FetchDailyDietUseCase::class.java.simpleName).e(ex)
        }
    }
}