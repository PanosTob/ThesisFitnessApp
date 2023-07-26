package gr.dipae.thesisfitnessapp.ui.sport.customize.navigation

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
import gr.dipae.thesisfitnessapp.ui.sport.customize.composable.SportCustomizeContent
import gr.dipae.thesisfitnessapp.ui.sport.customize.viemodel.SportCustomizeViewModel
import gr.dipae.thesisfitnessapp.util.ext.getComposeNavigationArgs
import gr.dipae.thesisfitnessapp.util.ext.replaceRouteStringWithArgumentPlaceholders
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

internal val SportCustomizeArgumentKeys = listOf("sportId")
internal val SportCustomizeRoute = "sports_customize${SportCustomizeArgumentKeys.getComposeNavigationArgs()}"

@ExperimentalComposeUiApi
fun NavGraphBuilder.sportCustomizeScreen(
    onSportCustomizeShown: () -> Unit,
    onStartClicked: (Pair<String, String>) -> Unit
) {
    composable(route = SportCustomizeRoute, arguments = sportCustomizeArguments()) {
        val viewModel: SportCustomizeViewModel = hiltViewModel()

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(viewModel, lifecycleOwner) {
            snapshotFlow { viewModel.uiState.value?.navigateToSportSession?.value }
                .filterNotNull()
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collectLatest {
                    onStartClicked(it)
                }
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onSportCustomizeShown()
        }

        viewModel.uiState.value?.let { uiState ->
            SportCustomizeContent(
                uiState = uiState,
                onStartClicked = { viewModel.onStartClicked() }
            )
        }
    }
}

fun NavController.navigateToSportCustomize(sportId: String) {
    singleNavigate(String.format(SportCustomizeRoute.replaceRouteStringWithArgumentPlaceholders(SportCustomizeArgumentKeys), sportId))
}

private fun sportCustomizeArguments(): List<NamedNavArgument> {
    return SportCustomizeArgumentKeys.map { (navArgument(it) { type = NavType.StringType }) }
}