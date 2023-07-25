package gr.dipae.thesisfitnessapp.ui.sport.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.ui.theme.ColorAccent
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondaryDark

data class SportsUiState(
    val sports: List<SportUiItem>
)

data class SportUiItem(
    val id: String,
    val name: String,
    val imageUrl: String,
    val favorite: MutableState<Boolean> = mutableStateOf(false),
    val editable: MutableState<Boolean> = mutableStateOf(false),
) {
    val backgroundColor = if (favorite.value) ColorSecondaryDark else ColorAccent

    fun determineClickAction(callback: (String) -> Unit) = if (editable.value) favorite.value = !favorite.value else callback(id)
}