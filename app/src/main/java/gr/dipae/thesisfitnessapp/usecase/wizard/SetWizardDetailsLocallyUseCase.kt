package gr.dipae.thesisfitnessapp.usecase.wizard

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import javax.inject.Inject

class SetWizardDetailsLocallyUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(userWizardDetails: UserWizardDetails?) {
        userWizardDetails ?: return

        repository.setUserWizardDetails(userWizardDetails)
    }
}