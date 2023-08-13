package gr.dipae.thesisfitnessapp.usecase.user.diet

import gr.dipae.thesisfitnessapp.domain.user.entity.DietGoal
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.GetUserDetailsUseCase
import timber.log.Timber
import javax.inject.Inject

class GetDietGoalUseCase @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : UseCase {

    suspend operator fun invoke(): DietGoal? {
        return try {
            getUserDetailsUseCase()?.dietGoal
        } catch (ex: Exception) {
            Timber.tag(GetDietGoalUseCase::class.java.simpleName).e(ex)
            null
        }
    }
}