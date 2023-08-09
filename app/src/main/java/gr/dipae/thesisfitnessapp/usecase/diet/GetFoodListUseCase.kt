package gr.dipae.thesisfitnessapp.usecase.diet

import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class GetFoodListUseCase @Inject constructor(
    private val dietRepository: DietRepository
) : UseCase {
    suspend operator fun invoke(page: Int = 1): List<Food> {
        return try {
            dietRepository.getFoodList(page)
        } catch (ex: Exception) {
            emptyList()
        }
    }
}