package gr.dipae.thesisfitnessapp.ui.sport.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.sport.composable.SportsContent
import gr.dipae.thesisfitnessapp.ui.sport.viewmodel.SportsViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val SportsRoute = "sports"
typealias OnSportSelected = (String) -> Unit

@ExperimentalComposeUiApi
fun NavGraphBuilder.sportsScreen(
    onSportsShown: (() -> Unit, () -> Unit, Boolean) -> Unit,
    onSportSelected: OnSportSelected
) {
    composable(SportsRoute) {
        val viewModel: SportsViewModel = hiltViewModel()

        val toolBarState = remember {
            mutableStateOf(true)
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.init()

            onSportsShown(
                {
                    toolBarState.value = false
                },
                {
                    toolBarState.value = true
                },
                toolBarState.value
            )
        }

        LaunchedEffect(key1 = toolBarState.value)
        {
            onSportsShown(
                {
                    toolBarState.value = false
                },
                {
                    toolBarState.value = true
                },
                toolBarState.value
            )
        }


        viewModel.uiState.value?.let { uiState ->
            SportsContent(
                uiState = uiState,
                onSportSelected = { onSportSelected(it) }
            )
        }
    }
}

fun NavController.navigateToSports() {
    singleNavigate(SportsRoute)
}