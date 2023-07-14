package gr.dipae.thesisfitnessapp.ui.sport.model

import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport

data class SportsUiState(
    val sports: List<SportUiItem>
)

data class SportUiItem(
    val id: String,
    val name: String,
    val imageUrl: String,
    val parameters: List<String>
)

fun Sport.toSportUiItem(): SportUiItem =
    SportUiItem(
        id = id,
        name = name,
        imageUrl = imageUrl,
        parameters = parameters
    )