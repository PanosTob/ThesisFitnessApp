package gr.dipae.thesisfitnessapp.usecase.wizard

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import timber.log.Timber
import javax.inject.Inject

class GetUserWizardDetailsLocallyUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(): UserWizardDetails? {
        return try {
            repository.getUserWizardDetails()
        } catch (ex: Exception) {
            Timber.tag(GetUserWizardDetailsLocallyUseCase::class.java.simpleName).e(ex)
            null
        }
    }
}