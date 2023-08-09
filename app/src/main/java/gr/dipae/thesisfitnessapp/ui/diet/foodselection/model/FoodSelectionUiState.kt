package gr.dipae.thesisfitnessapp.ui.diet.foodselection.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class FoodSelectionUiState(
    val foodList: MutableList<FoodUiItem> = mutableListOf(),
    val page: MutableState<Int> = mutableStateOf(1),
)

data class FoodUiItem(
    val id: String,
    val name: String,
    val proteins: String,
    val carbohydrates: String,
    val fats: String,
    val calories: String,
    val water: String,
    val fiber: String
)