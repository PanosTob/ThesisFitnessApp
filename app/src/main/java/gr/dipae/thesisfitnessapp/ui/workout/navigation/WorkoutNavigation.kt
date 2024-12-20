package gr.dipae.thesisfitnessapp.ui.workout.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.workout.composable.WorkoutContent
import gr.dipae.thesisfitnessapp.ui.workout.viewmodel.WorkoutViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate

internal const val WorkoutRoute = "workout"

@ExperimentalComposeUiApi
fun NavGraphBuilder.workoutScreen(
    onWorkoutShown: () -> Unit
) {
    composable(WorkoutRoute) {
        val viewModel: WorkoutViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onWorkoutShown()
        }

        WorkoutContent(
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateToWorkout() {
    singleNavigate(WorkoutRoute)
}