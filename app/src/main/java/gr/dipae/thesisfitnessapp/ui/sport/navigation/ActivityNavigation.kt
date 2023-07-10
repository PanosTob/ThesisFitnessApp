package gr.dipae.thesisfitnessapp.ui.sport.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.sport.composable.SportsContent
import gr.dipae.thesisfitnessapp.ui.sport.viewmodel.ActivityViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val SportsRoute = "sports"

@ExperimentalComposeUiApi
fun NavGraphBuilder.sportsScreen() {
    composable(SportsRoute) {
        val viewModel: ActivityViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        SportsContent(
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToSports() {
    singleNavigate(SportsRoute)
}