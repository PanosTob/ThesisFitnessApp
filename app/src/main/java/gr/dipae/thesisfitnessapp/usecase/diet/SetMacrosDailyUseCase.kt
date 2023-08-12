package gr.dipae.thesisfitnessapp.usecase.diet

import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.history.GetTodaySummaryUseCase
import javax.inject.Inject

class SetMacrosDailyUseCase @Inject constructor(
    private val dietRepository: DietRepository,
    private val getTodaySummaryUseCase: GetTodaySummaryUseCase
) : UseCase {

    suspend operator fun invoke(
        calories: Long,
        protein: Double,
        carbs: Double,
        fats: Double,
        water: Double
    ) {
        val todaySummary = getTodaySummaryUseCase()
        val dailyCalorieAccumulation = calories.plus(todaySummary?.dailyDiet?.calories ?: 0)
        val dailyProteinAccumulation = protein.plus(todaySummary?.dailyDiet?.proteins ?: 0.0)
        val dailyCarbAccumulation = carbs.plus(todaySummary?.dailyDiet?.carbohydrates ?: 0.0)
        val dailyFatAccumulation = fats.plus(todaySummary?.dailyDiet?.fats ?: 0.0)
        val dailyWaterAccumulation = water.plus(todaySummary?.dailyDiet?.waterML ?: 0.0)

        dietRepository.setMacrosDaily(
            DailyDietRequest(
                dailyCalorieAccumulation,
                dailyProteinAccumulation,
                dailyCarbAccumulation,
                dailyFatAccumulation,
                dailyWaterAccumulation
            ),
            todaySummary?.id
        )
    }
}