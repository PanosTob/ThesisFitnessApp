package gr.dipae.thesisfitnessapp.ui.profile.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.profile.composable.ProfileContent
import gr.dipae.thesisfitnessapp.ui.profile.viewmodel.ProfileViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate


internal const val ProfileRoute = "profile"

@ExperimentalComposeUiApi
fun NavGraphBuilder.profileScreen(
    onProfileShown: () -> Unit,
    onLogout: () -> Unit
) {
    composable(ProfileRoute) {
        val viewModel: ProfileViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
            onProfileShown()
        }

        ProfileContent(
            onLogout = { onLogout() }
//            uiState = viewModel.uiState.value
        )
    }
}

fun NavController.navigateProfile() {
    singleNavigate(ProfileRoute)
}