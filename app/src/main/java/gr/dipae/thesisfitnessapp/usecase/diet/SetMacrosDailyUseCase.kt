package gr.dipae.thesisfitnessapp.usecase.diet

import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.history.GetTodaySummaryUseCase
import javax.inject.Inject

class SetMacrosDailyUseCase @Inject constructor(
    private val dietRepository: DietRepository,
    private val getTodaySummaryUseCase: GetTodaySummaryUseCase
) : UseCase {

    suspend operator fun invoke(
        calories: String,
        protein: String,
        carbs: String,
        fats: String,
        water: String
    ) {
        val todaySummary = getTodaySummaryUseCase()
        dietRepository.setMacrosDaily(calories, protein, carbs, fats, water, todaySummary)
    }
}