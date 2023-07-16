package gr.dipae.thesisfitnessapp.usecase.user.diet

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.diet.entity.UserScannedFood
import javax.inject.Inject

class GetUserScannedFoodsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<UserScannedFood>? {
        return try {
            repository.getUserScannedFoods()
        } catch (ex: Exception) {
            null
        }
    }
}