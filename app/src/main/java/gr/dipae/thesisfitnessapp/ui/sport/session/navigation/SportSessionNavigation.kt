package gr.dipae.thesisfitnessapp.ui.sport.session.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import gr.dipae.thesisfitnessapp.ui.sport.session.composable.SportSessionContent
import gr.dipae.thesisfitnessapp.ui.sport.session.viewmodel.SportSessionViewModel
import gr.dipae.thesisfitnessapp.util.ext.getComposeNavigationArgs
import gr.dipae.thesisfitnessapp.util.ext.replaceRouteStringWithArgumentPlaceholders
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal val SportSessionArgumentKeys = listOf("sportId")
internal val SportSessionRoute = "sports_session${SportSessionArgumentKeys.getComposeNavigationArgs()}"

@ExperimentalComposeUiApi
fun NavGraphBuilder.sportSessionScreen(
    onSportSessionShown: () -> Unit
) {
    composable(route = SportSessionRoute, arguments = sportCustomizeArguments()) {
        val viewModel: SportSessionViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onSportSessionShown()
        }

        viewModel.uiState.value?.let { uiState ->
            SportSessionContent(
                uiState = uiState
            )
        }
    }
}

fun NavController.navigateToSportSession(sportId: String) {
    singleNavigate(String.format(SportSessionRoute.replaceRouteStringWithArgumentPlaceholders(SportSessionArgumentKeys), sportId))
}

private fun sportCustomizeArguments(): List<NamedNavArgument> {
    return SportSessionArgumentKeys.map { (navArgument(it) { type = NavType.StringType }) }
}