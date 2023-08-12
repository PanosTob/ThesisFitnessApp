package gr.dipae.thesisfitnessapp.ui.diet.foodselection.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.viewmodel.FoodSelectionViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val FoodSelectionRoute = "food_selection"

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.foodSelectionScreen() {
    composable(FoodSelectionRoute) {
        val viewModel: FoodSelectionViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        FoodSelectionContent(
            uiState = viewModel.uiState.value,
            onFoodItemClicked = { viewModel.onFoodItemClicked(it) },
            onSearchFood = { viewModel.onSearchFood(it) },
            onClearSearch = { viewModel.onClearSearch() },
            onPageSizeReached = { viewModel.getFoodListNextPage() }
        )

        FoodItemNutrientsDialog(
            selectedFoodItem = viewModel.uiState.value.selectedFoodItem.value,
            onSaveClicked = { viewModel.onNutrientsSaveClicked(it) },
            onDismiss = { viewModel.uiState.value.selectedFoodItem.value = null }
        )
    }
}

fun NavController.navigateToFoodSelection() {
    singleNavigate(FoodSelectionRoute)
}