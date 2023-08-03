package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class GetSportParameterNavigationArgumentUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {
    operator fun invoke(parameterJson: String?): SportParameter? {
        if (parameterJson.isNullOrBlank()) return null

        return try {
            repository.getSportParameterArgument(parameterJson)
        } catch (ex: Exception) {
            null
        }
    }
}