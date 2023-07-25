package gr.dipae.thesisfitnessapp.ui.splash.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.splash.composable.SplashContent
import gr.dipae.thesisfitnessapp.ui.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

internal const val SplashRoute = "splash"

@ExperimentalComposeUiApi
fun NavGraphBuilder.splashScreen(
    onSplashNavigateAction: (String) -> Unit
) {
    composable(SplashRoute) {
        val viewModel: SplashViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(viewModel, lifecycleOwner) {
            snapshotFlow { viewModel.uiState.value.navigateToRoute.value }
                .filterNotNull()
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collectLatest {
                    onSplashNavigateAction(it)
                }
        }

        SplashContent()
    }
}