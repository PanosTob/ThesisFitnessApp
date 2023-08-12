package gr.dipae.thesisfitnessapp.usecase.diet

import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.usecase.UseCase
import okio.IOException
import timber.log.Timber
import javax.inject.Inject

class SetFoodNutrientsUseCase @Inject constructor(
    private val setMacrosDailyUseCase: SetMacrosDailyUseCase
) : UseCase {

    suspend operator fun invoke(foodItem: Food?, grams: Long?) {
        try {
            if (foodItem == null || grams == null) throw IOException()
            setMacrosDailyUseCase(
                calories = foodItem.calories * (grams.div(100)),
                protein = foodItem.proteins * (grams.div(100)),
                carbs = foodItem.carbohydrates * (grams.div(100)),
                fats = foodItem.fats * (grams.div(100)),
                water = foodItem.water * (grams.div(100))
            )
        } catch (ex: Exception) {
            Timber.tag(SetFoodNutrientsUseCase::class.java.simpleName).e(ex)
        }
    }
}