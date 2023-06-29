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

private const val DietRoute = "diet"

@ExperimentalComposeUiApi
fun NavGraphBuilder.dietScreen() {
    composable(DietRoute) {
        val viewModel: DietViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        DietContent(
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToDiet() {
    singleNavigate(DietRoute)
}