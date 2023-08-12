package gr.dipae.thesisfitnessapp.ui.diet.foodselection.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class FoodSelectionUiState(
    val foodList: SnapshotStateList<FoodUiItem> = mutableStateListOf(),
    val selectedFoodItem: MutableState<FoodUiItem?> = mutableStateOf(null),
    val searchBarTrailingActionType: MutableState<SearchBarActionType> = mutableStateOf(SearchBarActionType.Search)
)

sealed class SearchBarActionType {
    object Search : SearchBarActionType()
    object Clear : SearchBarActionType()
}

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