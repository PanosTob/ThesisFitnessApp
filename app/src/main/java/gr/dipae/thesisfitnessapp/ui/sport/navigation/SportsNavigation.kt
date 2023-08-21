package gr.dipae.thesisfitnessapp.ui.sport.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.history.composable.HistoryDateRangeBottomSheet
import gr.dipae.thesisfitnessapp.ui.sport.composable.SportsContent
import gr.dipae.thesisfitnessapp.ui.sport.viewmodel.SportsViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val SportsRoute = "sports"
typealias OnSportSelected = (String) -> Unit

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
fun NavGraphBuilder.sportsScreen(
    onSportsShown: (() -> Unit, () -> Unit, () -> Unit, Boolean) -> Unit,
    onSportSelected: OnSportSelected,
    onDateRangePicked: (Long?, Long?) -> Unit
) {
    composable(SportsRoute) {
        val viewModel: SportsViewModel = hiltViewModel()

        val editState = remember {
            mutableStateOf(false)
        }

        val openCalendar = remember {
            mutableStateOf(false)
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
            viewModel.init()
        }

        LaunchedEffect(key1 = editState.value)
        {
            onSportsShown(
                {
                    viewModel.updateEditState(true)
                    editState.value = true
                },
                {
                    viewModel.setFavoriteSports()
                    viewModel.updateEditState(false)
                    editState.value = false
                },
                {
                    openCalendar.value = true
                },
                editState.value
            )
        }


        viewModel.uiState.value?.let { uiState ->
            Box(Modifier.fillMaxSize()) {
                SportsContent(
                    uiState = uiState,
                    onSportSelected = { onSportSelected(it) }
                )

                if (openCalendar.value) {
                    HistoryDateRangeBottomSheet(
                        modifier = Modifier,
                        onDismiss = { openCalendar.value = false },
                        onDateRangePicked = { startDate, endDate -> onDateRangePicked(startDate, endDate) }
                    )
                }
            }
        }
    }
}

fun NavController.navigateToSports() {
    singleNavigate(SportsRoute)
}