package gr.dipae.thesisfitnessapp.ui.sport.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.sport.composable.ActivityContent
import gr.dipae.thesisfitnessapp.ui.sport.viewmodel.ActivityViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

private const val ActivityRoute = "activity"

@ExperimentalComposeUiApi
fun NavGraphBuilder.activityScreen() {
    composable(ActivityRoute) {
        val viewModel: ActivityViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        ActivityContent(
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToActivity() {
    singleNavigate(ActivityRoute)
}