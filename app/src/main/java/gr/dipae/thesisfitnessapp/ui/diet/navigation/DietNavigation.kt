package gr.dipae.thesisfitnessapp.ui.diet.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.diet.composable.DietContent
import gr.dipae.thesisfitnessapp.ui.diet.viewmodel.DietViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val DietRoute = "diet"

internal typealias OnFoodSelectionFabClicked = () -> Unit
internal typealias OnMacrosFabClicked = () -> Unit

@ExperimentalComposeUiApi
fun NavGraphBuilder.dietScreen(
    onDietShown: () -> Unit,
    onFoodSelectionFabClicked: OnFoodSelectionFabClicked,
    onMacrosFabClicked: OnMacrosFabClicked
) {
    composable(DietRoute) {
        val viewModel: DietViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onDietShown()
        }

        DietContent(
            onFoodSelectionFabClicked = { onFoodSelectionFabClicked() },
            onMacrosFabClicked = { onMacrosFabClicked() }
        )
    }
}

fun NavController.navigateToDiet() {
    singleNavigate(DietRoute)
}