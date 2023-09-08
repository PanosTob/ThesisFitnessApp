package gr.dipae.thesisfitnessapp.usecase.user.history

import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import timber.log.Timber
import javax.inject.Inject

class RefreshDailyDietUseCase @Inject constructor(
    private val dietRepository: DietRepository
) {
    suspend operator fun invoke(dailyDiet: DailyDiet?) {
        return try {
            dietRepository.setDailyDiet(dailyDiet)
        } catch (ex: Exception) {
            Timber.tag(RefreshDailyDietUseCase::class.java.simpleName).e(ex)
        }
    }
}