package gr.dipae.thesisfitnessapp.ui.sport.customize.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
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

internal val SportCustomizeArgumentKeys = listOf("sportId")
internal val SportCustomizeRoute = "sports_customize${SportCustomizeArgumentKeys.getComposeNavigationArgs()}"

@ExperimentalComposeUiApi
fun NavGraphBuilder.sportCustomizeScreen(
    onSportCustomizeShown: () -> Unit
) {
    composable(route = SportCustomizeRoute, arguments = sportCustomizeArguments()) {
        val viewModel: SportCustomizeViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onSportCustomizeShown()
        }

        viewModel.uiState.value?.let { uiState ->
            SportCustomizeContent(
                uiState = uiState
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