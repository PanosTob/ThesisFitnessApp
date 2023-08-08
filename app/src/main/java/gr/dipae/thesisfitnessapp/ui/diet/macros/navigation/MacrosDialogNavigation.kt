package gr.dipae.thesisfitnessapp.ui.diet.macros.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import gr.dipae.thesisfitnessapp.ui.diet.macros.composables.MacrosDialogContent
import gr.dipae.thesisfitnessapp.ui.diet.macros.viewmodel.MacrosDialogViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val MacrosDialogRoute = "macros_dialog"
fun NavGraphBuilder.macrosDialog() {
    dialog(
        MacrosDialogRoute,
        dialogProperties = DialogProperties(
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        val viewModel: MacrosDialogViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }
        MacrosDialogContent(
            uiState = viewModel.uiState.value,

            onSaveClicked = { viewModel.onSaveClicked() }
        )
    }
}

fun NavController.navigateToMacrosDialog() {
    singleNavigate(MacrosDialogRoute)
}