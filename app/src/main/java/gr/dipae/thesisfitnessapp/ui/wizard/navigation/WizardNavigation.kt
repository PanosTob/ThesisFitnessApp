package gr.dipae.thesisfitnessapp.ui.wizard.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.LoginRoute
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardContent
import gr.dipae.thesisfitnessapp.ui.wizard.viewmodel.WizardViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

internal const val WizardRoute = "wizard"

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.wizardScreen(
    navigateToDashboardAction: () -> Unit
) {
    composable(WizardRoute) {
        val viewModel: WizardViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(viewModel, lifecycleOwner) {
            snapshotFlow { viewModel.uiState.value?.goToDashboardState?.value }
                .filterNotNull()
                .filter { it }
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collectLatest {
                    navigateToDashboardAction()
                }
        }

        viewModel.uiState.value?.let {
            WizardContent(
                uiState = it,
                onFinishWizard = { viewModel.saveWizardDetails() }
            )
        }
    }
}

fun NavController.navigateToWizard() {
    singleNavigateWithPopInclusive(WizardRoute, LoginRoute)
}