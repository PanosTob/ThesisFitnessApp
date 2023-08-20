package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.app.entity.FirebaseWriteDocumentResult
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class SetUserProfileDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(userWizardDetails: UserWizardDetails?): FirebaseWriteDocumentResult {
        return try {
            userWizardDetails ?: return FirebaseWriteDocumentResult.Failure

            repository.setUserProfileDetails(userWizardDetails)
            FirebaseWriteDocumentResult.Success
        } catch (ex: Exception) {
            FirebaseWriteDocumentResult.Failure
        }
    }
}