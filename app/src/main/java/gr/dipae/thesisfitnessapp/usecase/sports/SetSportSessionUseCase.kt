package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class SetSportSessionUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {
    suspend operator fun invoke(sportId: String, sportParameters: List<SportParameter>, achievedParameter: SportParameter, sportParameterValue: String) {
        try {
            val sportParameterNumber = sportParameterValue.toLongOrNull() ?: return
            val sportSessionRequest = createSportSessionRequest(sportId)
            val sportParametersRequest = mapSportParameters(sportParameters, achievedParameter, sportParameterNumber)
            repository.setSportSession(sportSessionRequest, sportParametersRequest)
        } catch (ex: Exception) {
            Timber.tag(SetSportSessionUseCase::class.simpleName.toString()).e(ex)
        }
    }

    private fun createSportSessionRequest(sportId: String): SportSessionRequest {
        return SportSessionRequest(sportId)
    }

    private fun mapSportParameters(sportParameter: List<SportParameter>, achievedParameter: SportParameter, sportParameterNumber: Long): List<SportParameterRequest> {
        return sportParameter.map {
            if (it.name == "duration") {
                SportParameterRequest(it.name, sportParameterNumber, achievedParameter.name == it.name && achievedParameter.value == sportParameterNumber)
            } else {
                SportParameterRequest(it.name, it.value, true)
            }
        }
    }
}