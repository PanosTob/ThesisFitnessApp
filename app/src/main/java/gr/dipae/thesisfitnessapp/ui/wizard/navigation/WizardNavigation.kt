package gr.dipae.thesisfitnessapp.ui.wizard.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardContent
import gr.dipae.thesisfitnessapp.ui.wizard.viewmodel.WizardViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

private const val WizardRoute = "wizard"

@ExperimentalComposeUiApi
fun NavGraphBuilder.wizardScreen() {
    composable(WizardRoute) {
        val viewModel: WizardViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        WizardContent(
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToWizard() {
    singleNavigate(WizardRoute)
}