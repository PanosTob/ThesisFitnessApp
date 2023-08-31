package gr.dipae.thesisfitnessapp.usecase.diet

import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.domain.diet.entity.SetDailyDietResult
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.history.RefreshDailyDietUseCase
import timber.log.Timber
import javax.inject.Inject

class SetMacrosDailyUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val refreshDailyDietUseCase: RefreshDailyDietUseCase
) : UseCase {

    suspend operator fun invoke(
        calories: Long,
        protein: Double,
        carbs: Double,
        fats: Double,
        water: Double
    ): SetDailyDietResult {
        return try {
            val todaySummary = userRepository.getDaySummary()
            val dailyCalorieAccumulation = calories.plus(todaySummary?.dailyDiet?.calories ?: 0)
            val dailyProteinAccumulation = protein.plus(todaySummary?.dailyDiet?.proteins ?: 0.0)
            val dailyCarbAccumulation = carbs.plus(todaySummary?.dailyDiet?.carbohydrates ?: 0.0)
            val dailyFatAccumulation = fats.plus(todaySummary?.dailyDiet?.fats ?: 0.0)
            val dailyWaterAccumulation = water.plus(todaySummary?.dailyDiet?.water ?: 0.0)

            userRepository.setMacrosDaily(
                DailyDietRequest(
                    dailyCalorieAccumulation,
                    dailyProteinAccumulation,
                    dailyCarbAccumulation,
                    dailyFatAccumulation,
                    dailyWaterAccumulation
                ),
                todaySummary?.id
            )

            refreshDailyDietUseCase(
                DailyDiet(
                    calories = dailyCalorieAccumulation,
                    proteins = dailyProteinAccumulation,
                    carbohydrates = dailyCarbAccumulation,
                    fats = dailyFatAccumulation,
                    water = dailyWaterAccumulation
                )
            )

            SetDailyDietResult.Success
        } catch (ex: Exception) {
            Timber.tag(SetMacrosDailyUseCase::class.java.simpleName).e(ex)
            SetDailyDietResult.Failure
        }
    }
}