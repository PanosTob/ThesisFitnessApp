package gr.dipae.thesisfitnessapp.ui.diet.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.diet.composable.DietContent
import gr.dipae.thesisfitnessapp.ui.diet.viewmodel.DietViewModel
import gr.dipae.thesisfitnessapp.ui.history.composable.HistoryDateRangeBottomSheet
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val DietRoute = "diet"

internal typealias OnFoodSelectionFabClicked = () -> Unit

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
fun NavGraphBuilder.dietScreen(
    onDietShown: (() -> Unit) -> Unit,
    onFoodSelectionFabClicked: OnFoodSelectionFabClicked,
    onMacrosFabClicked: OnMacrosFabClicked,
    onDateRangePicked: (Long?, Long?) -> Unit
) {
    composable(DietRoute) {
        val viewModel: DietViewModel = hiltViewModel()

        val openCalendar = remember {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onDietShown({
                openCalendar.value = true
            })
        }

        Box(Modifier.fillMaxSize()) {
            DietContent(
                uiState = viewModel.uiState.value,
                onFoodSelectionFabClicked = { onFoodSelectionFabClicked() },
                onMacrosFabClicked = { onMacrosFabClicked() }
            )

            if (openCalendar.value) {
                HistoryDateRangeBottomSheet(
                    modifier = Modifier,
                    onDismiss = { openCalendar.value = false },
                    onDateRangePicked = { startDate, endDate ->
                        if (startDate == endDate) {
                            openCalendar.value = false
                            viewModel.init(startDate)
                        } else {
                            onDateRangePicked(startDate, endDate)
                        }
                    }
                )
            }
        }
    }
}

internal typealias OnMacrosFabClicked = () -> Unit

fun NavController.navigateToDiet() {
    singleNavigate(DietRoute)
}