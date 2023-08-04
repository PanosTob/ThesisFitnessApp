package gr.dipae.thesisfitnessapp.ui.sport.session.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiMapState
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import javax.inject.Inject

class SportSessionUiMapper @Inject constructor(
) : Mapper {

    operator fun invoke(sportParameterToBeAchieved: SportParameter?, sportHasMap: Boolean?): SportSessionUiState? {
        sportHasMap ?: return null

        return SportSessionUiState(
            hasMap = sportHasMap,
            mapState = SportSessionUiMapState(),
            goalParameter = sportParameterToBeAchieved
        )
    }


    fun toHundredsOfASecond(millis: Long): String {
        if (millis == 0L) {
            return "00:00:00:00"
        }

        val hundredsOfASecond = (millis / 10).mod(100)
        val seconds = (millis / 1000).mod(60)
        val minutes = ((millis / (1000 * 60)).mod(60))
        val hours = ((millis / (1000 * 60 * 60)).mod(24))

        return "${String.format("%02d", hours)}:${String.format("%02d", minutes)}:${String.format("%02d", seconds)}:${String.format("%02d", hundredsOfASecond)}"
    }

    fun toSecondsString(millis: Long): String {
        if (millis == 0L) {
            return "-"
        }
        val seconds = (millis / 1000).mod(60)
        val minutes = ((millis / (1000 * 60)).mod(60))
        val hours = ((millis / (1000 * 60 * 60)).mod(24))

        return "${String.format("%02d", hours)}:${String.format("%02d", minutes)}:${String.format("%02d", seconds)}"
    }
}