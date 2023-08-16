package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.app.entity.FirebaseWriteDocumentResult
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.wizard.GetUserWizardDetailsLocallyUseCase
import javax.inject.Inject

class SetUserFitnessProfileUseCase @Inject constructor(
    private val repository: UserRepository,
    private val getUserWizardDetailsLocallyUseCase: GetUserWizardDetailsLocallyUseCase
) : UseCase {

    suspend operator fun invoke(): FirebaseWriteDocumentResult {
        return try {
            val userWizardDetails = getUserWizardDetailsLocallyUseCase() ?: return FirebaseWriteDocumentResult.Failure

            repository.setUserFitnessProfile(userWizardDetails)
            FirebaseWriteDocumentResult.Success
        } catch (ex: Exception) {
            FirebaseWriteDocumentResult.Failure
        }
    }
}