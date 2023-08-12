package gr.dipae.thesisfitnessapp.usecase.diet

import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetFoodByNameUseCase @Inject constructor(
    private val dietRepository: DietRepository
) : UseCase {

    suspend operator fun invoke(foodNameQuery: String): List<Food> {
        return try {
            dietRepository.searchFoodByName(foodNameQuery)
        } catch (ex: Exception) {
            Timber.tag(GetFoodByNameUseCase::class.java.simpleName).e(ex)
            emptyList()
        }
    }
}