package gr.dipae.thesisfitnessapp.ui.history.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import gr.dipae.thesisfitnessapp.ui.history.composable.HistoryContent
import gr.dipae.thesisfitnessapp.ui.history.viewmodel.HistoryViewModel
import gr.dipae.thesisfitnessapp.util.ext.getComposeNavigationArgs
import gr.dipae.thesisfitnessapp.util.ext.replaceRouteStringWithArgumentPlaceholders
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull


internal val HistoryRouteArgs = listOf("startDate", "endDate", "fromSports")
internal val HistoryRoute = "history${HistoryRouteArgs.getComposeNavigationArgs()}"

@ExperimentalComposeUiApi
fun NavGraphBuilder.historyScreen(
    onHistoryShown: (Boolean, Boolean, () -> Unit) -> Unit
) {
    composable(HistoryRoute, arguments = historyArguments()) {
        val viewModel: HistoryViewModel = hiltViewModel()

        val fromSports = it.arguments?.getBoolean(HistoryRouteArgs[2]) ?: false

        LaunchedEffect(key1 = Unit) {
            viewModel.init(fromSports)
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(viewModel, lifecycleOwner) {
            snapshotFlow { viewModel.uiState.value?.filteredSport }
                .filterNotNull()
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collectLatest { filteredSport ->
                    if (filteredSport) {
                        onHistoryShown(fromSports, true) {
                            viewModel.showHistoryContent(fromSports)
                        }
                    } else {
                        onHistoryShown(fromSports, false) {
                            viewModel.showHistoryDialog()
                        }
                    }
                }
        }

        viewModel.uiState.value?.let { uiState ->
            HistoryContent(
                uiState = uiState,
                onSportFilterClicked = { sportId -> viewModel.filterHistoryBySport(sportId) }
            )
        }
    }
}

fun NavController.navigateToHistory(startDate: Long, endDate: Long, fromSports: Boolean) {
    singleNavigate(String.format(HistoryRoute.replaceRouteStringWithArgumentPlaceholders(HistoryRouteArgs), startDate, endDate, fromSports))
}

private fun historyArguments(): List<NamedNavArgument> {
    return listOf(
        navArgument(HistoryRouteArgs[0]) {
            type = NavType.LongType
        },
        navArgument(HistoryRouteArgs[1]) {
            type = NavType.LongType
        },
        navArgument(HistoryRouteArgs[2]) {
            type = NavType.BoolType
        }
    )
}