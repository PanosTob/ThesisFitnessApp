package gr.dipae.thesisfitnessapp.ui.sport.session.navigation

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
import gr.dipae.thesisfitnessapp.ui.sport.session.composable.OnSportSessionTimerPause
import gr.dipae.thesisfitnessapp.ui.sport.session.composable.OnSportSessionTimerResume
import gr.dipae.thesisfitnessapp.ui.sport.session.composable.OnSportSessionTimerStop
import gr.dipae.thesisfitnessapp.ui.sport.session.composable.SportSessionContent
import gr.dipae.thesisfitnessapp.ui.sport.session.viewmodel.SportSessionViewModel
import gr.dipae.thesisfitnessapp.util.ext.getComposeNavigationArgs
import gr.dipae.thesisfitnessapp.util.ext.replaceRouteStringWithArgumentPlaceholders
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

internal val SportSessionArgumentKeys = listOf("sportId", "sportParameter")
internal val SportSessionRoute = "sports_session${SportSessionArgumentKeys.getComposeNavigationArgs()}"

@ExperimentalComposeUiApi
fun NavGraphBuilder.sportSessionScreen(
    onSportSessionShown: () -> Unit,
    popBackToSports: () -> Unit,
    onSportSessionTimerResume: OnSportSessionTimerResume,
    onSportSessionTimerPause: OnSportSessionTimerPause,
    onSportSessionTimerStop: OnSportSessionTimerStop
) {
    composable(route = SportSessionRoute, arguments = sportCustomizeArguments()) {
        val viewModel: SportSessionViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onSportSessionShown()
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(viewModel, lifecycleOwner) {
            snapshotFlow { viewModel.uiState.value?.backToLogin?.value }
                .filterNotNull()
                .filter { it }
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collectLatest {
                    onSportSessionTimerStop()
                    popBackToSports()
                }
        }

        viewModel.uiState.value?.let { uiState ->
            SportSessionContent(
                uiState = uiState,
                onSessionFinish = {
                    onSportSessionTimerStop()
                    viewModel.onSessionFinish()
                },
                onSportSessionTimerResume = {
                    onSportSessionTimerResume()
                    viewModel.pauseBreakTimer()
                },
                onSportSessionTimerPause = {
                    onSportSessionTimerPause()
                    viewModel.startBreakTimer()
                }
            )
        }
    }
}

fun NavController.navigateToSportSession(arguments: Pair<String, String?>) {
    singleNavigate(String.format(SportSessionRoute.replaceRouteStringWithArgumentPlaceholders(SportSessionArgumentKeys), arguments.first, arguments.second))
}

private fun sportCustomizeArguments(): List<NamedNavArgument> {
    return SportSessionArgumentKeys.map {
        (navArgument(it) {
            nullable = true
            type = NavType.StringType
        })
    }
}