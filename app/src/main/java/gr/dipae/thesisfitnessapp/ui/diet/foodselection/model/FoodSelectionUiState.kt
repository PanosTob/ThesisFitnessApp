package gr.dipae.thesisfitnessapp.ui.diet.foodselection.model

data class FoodSelectionUiState(
    val foodList: List<FoodUiItem> = emptyList()
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