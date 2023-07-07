package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.app.entity.FirebaseWriteDocumentResult
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class SetUserFitnessProfileUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(
        userName: String,
        fitnessLevel: FitnessLevel,
        favoriteSports: List<String>,
        calories: String,
        carbs: String,
        fats: String,
        proteins: String,
        waterML: String
    ): FirebaseWriteDocumentResult {
        return try {
            repository.setUserFitnessProfile(
                userName,
                fitnessLevel,
                favoriteSports,
                calories,
                carbs,
                fats,
                proteins,
                waterML
            )
            FirebaseWriteDocumentResult.Success
        } catch (ex: Exception) {
            FirebaseWriteDocumentResult.Failure
        }
    }
}