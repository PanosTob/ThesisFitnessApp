package gr.dipae.thesisfitnessapp.ui.history.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.history.composable.HistoryContent
import gr.dipae.thesisfitnessapp.ui.history.viewmodel.HistoryViewModel
import gr.dipae.thesisfitnessapp.util.ext.getComposeNavigationArgs
import gr.dipae.thesisfitnessapp.util.ext.replaceRouteStringWithArgumentPlaceholders
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate


internal val HistoryRouteArgs = listOf("userId")
internal val HistoryRoute = "history${HistoryRouteArgs.getComposeNavigationArgs()}"

@ExperimentalComposeUiApi
fun NavGraphBuilder.historyScreen() {
    composable(HistoryRoute) {
        val viewModel: HistoryViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        HistoryContent(
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToHistory(userId: String) {
    singleNavigate(String.format(HistoryRoute.replaceRouteStringWithArgumentPlaceholders(HistoryRouteArgs)))
}