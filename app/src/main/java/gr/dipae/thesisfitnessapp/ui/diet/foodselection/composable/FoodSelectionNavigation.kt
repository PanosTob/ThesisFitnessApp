package gr.dipae.thesisfitnessapp.ui.diet.foodselection.composable

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.viewmodel.FoodSelectionViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val FoodSelectionRoute = "food_selection"

fun NavGraphBuilder.foodSelectionScreen() {
    composable(FoodSelectionRoute) {
        val viewModel: FoodSelectionViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        FoodSelectionContent(
            uiState = viewModel.uiState.value,
            onFoodSelectionItemClicked = {},
            onPageSizeReached = { viewModel.getFoodListNextPage() }
        )
    }
}

fun NavController.navigateToFoodSelection() {
    singleNavigate(FoodSelectionRoute)
}