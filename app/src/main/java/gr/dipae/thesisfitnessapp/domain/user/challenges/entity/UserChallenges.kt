package gr.dipae.thesisfitnessapp.domain.user.challenges.entity

import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType


data class UserSportChallenge(
    val sportId: String,
    val sportName: String,
    val sportImgUrl: String,
    val goal: SportChallenge,
    val completed: Boolean,
    val progress: Double
)

data class SportChallenge(
    val name: String,
    val type: SportParameterType,
    val value: Double
)