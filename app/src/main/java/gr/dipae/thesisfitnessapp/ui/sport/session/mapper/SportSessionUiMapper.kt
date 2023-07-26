package gr.dipae.thesisfitnessapp.ui.sport.session.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import javax.inject.Inject

class SportSessionUiMapper @Inject constructor(
) : Mapper {

    operator fun invoke(sportId: String, parameters: List<SportParameter>, sportParameterToBeAchieved: SportParameter?): SportSessionUiState? {
        sportParameterToBeAchieved ?: return null

        return SportSessionUiState(sportId, parameters, sportParameterToBeAchieved)
    }

    operator fun invoke(millis: Long): String {
        val tensOfSeconds = (millis / 100) % 60
        val seconds = (millis / 1000) % 60
        val minutes = ((millis / (1000 * 60)) % 60)
        val hours = ((millis / (1000 * 60 * 60)) % 24)

        return "${String.format("%02d", hours)}:${String.format("%02d", minutes)}:${String.format("%02d", seconds)}:${String.format("%02d", tensOfSeconds)}"
    }

    /*operator fun invoke(timeString: String): Long {
        val hours = timeString.substringBefore(":"),
    }*/
}