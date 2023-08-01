package gr.dipae.thesisfitnessapp.framework.sports.session.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType

class SportParameterTypeAdapter {

    @FromJson
    fun fromJson(value: String?): SportParameterType {
        return when (value) {
            "duration" -> SportParameterType.Duration()
            "distance" -> SportParameterType.Distance()
            else -> SportParameterType.Unknown
        }
    }

    @ToJson
    fun toJson(type: SportParameterType?): String {
        return when (type) {
            is SportParameterType.Duration -> "duration"
            is SportParameterType.Distance -> "distance"
            else -> ""
        }
    }
}