package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class CreateSportParameterNavigationArgumentUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {
    operator fun invoke(parameter: SportParameter?): String? {
        parameter ?: return null
        return try {
            repository.sportParameterAsArgumentString(parameter)
        } catch (ex: Exception) {
            null
        }
    }
}