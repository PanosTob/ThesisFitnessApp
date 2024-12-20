package gr.dipae.thesisfitnessapp.data.sport.mapper

import androidx.compose.ui.graphics.Color
import gr.dipae.thesisfitnessapp.BuildConfig
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSportDetails
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportDetails
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import gr.dipae.thesisfitnessapp.ui.theme.ColorBoxing
import gr.dipae.thesisfitnessapp.ui.theme.ColorClimbing
import gr.dipae.thesisfitnessapp.ui.theme.ColorCycling
import gr.dipae.thesisfitnessapp.ui.theme.ColorIndoorRunning
import gr.dipae.thesisfitnessapp.ui.theme.ColorJumpingRope
import gr.dipae.thesisfitnessapp.ui.theme.ColorPingPong
import gr.dipae.thesisfitnessapp.ui.theme.ColorRunning
import gr.dipae.thesisfitnessapp.ui.theme.ColorSwimming
import gr.dipae.thesisfitnessapp.ui.theme.ColorWalking
import javax.inject.Inject

class SportsMapper @Inject constructor() : Mapper {

    operator fun invoke(remoteSports: List<RemoteSport>, favoriteSportIds: List<String>): List<Sport> {
        return remoteSports.mapNotNull { mapSport(it, favoriteSportIds) }
    }

    fun mapSport(remoteSport: RemoteSport?, favoriteSportIds: List<String>): Sport? {
        remoteSport ?: return null
        return Sport(
            id = remoteSport.id,
            name = remoteSport.name,
            hasMap = remoteSport.showMap,
            imageUrl = "${BuildConfig.GOOGLE_STORAGE_FIREBASE}${remoteSport.imageUrl}${BuildConfig.GOOGLE_STORAGE_FIREBASE_QUERY_PARAMS}",
            parameters = mapSportParameters(remoteSport.parameters),
            details = mapSportDetails(remoteSport.details),
            favorite = favoriteSportIds.contains(remoteSport.id)
        )
    }

    private fun mapSportParameters(parameters: List<String>): List<SportParameter> {
        return parameters.map { SportParameter(name = it, type = mapSportParameterType(it)) }
    }

    fun mapSportParameterType(parameter: String): SportParameterType {
        return sportParameterTypesMap[parameter.lowercase()] ?: SportParameterType.Unknown
    }

    private fun mapSportDetails(details: RemoteSportDetails): SportDetails {
        return SportDetails(
            distanceMeters = details.distanceMeters,
            durationSeconds = details.distanceMeters
        )
    }


    companion object {
        val sportNamesMap = linkedMapOf(
            "B7i1ymOBSpyug4JpVCUY" to "Ping Pong",
            "DeycT4SHRC7oXICdz8Eu" to "Indoor Running",
            "GQwtuStM2lK3K9HPPbuL" to "Walking",
            "YjBbZzm1SjahJnFryDjN" to "Running",
            "hT4SiXXhJNLTZcLh2Ftw" to "Jumping Rope",
            "khuM7tOiXxq3GAzd6vG7" to "Cycling",
            "nbLxd82u7iq0vGxzFsf5" to "Boxing",
            "rOGkpF0m5WxCxWOhwQg9" to "Climbing",
            "w3S0w3zexZ75j0vm2ziK" to "Swimming",
        )
        val sportColorMap: LinkedHashMap<String, Color> =
            linkedMapOf(
                "B7i1ymOBSpyug4JpVCUY" to ColorPingPong,
                "DeycT4SHRC7oXICdz8Eu" to ColorIndoorRunning,
                "GQwtuStM2lK3K9HPPbuL" to ColorWalking,
                "YjBbZzm1SjahJnFryDjN" to ColorRunning,
                "hT4SiXXhJNLTZcLh2Ftw" to ColorJumpingRope,
                "khuM7tOiXxq3GAzd6vG7" to ColorCycling,
                "nbLxd82u7iq0vGxzFsf5" to ColorBoxing,
                "rOGkpF0m5WxCxWOhwQg9" to ColorClimbing,
                "w3S0w3zexZ75j0vm2ziK" to ColorSwimming,
            )
        private val sportParameterTypesMap = linkedMapOf(
            "duration" to SportParameterType.Duration,
            "distance" to SportParameterType.Distance
        )
    }
}