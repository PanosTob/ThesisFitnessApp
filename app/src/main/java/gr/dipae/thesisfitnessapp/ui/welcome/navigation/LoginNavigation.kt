package gr.dipae.thesisfitnessapp.ui.welcome.navigation

import android.content.IntentSender
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gr.dipae.thesisfitnessapp.ui.splash.navigation.SplashRoute
import gr.dipae.thesisfitnessapp.ui.welcome.composable.LoginContent
import gr.dipae.thesisfitnessapp.ui.welcome.viewmodel.LoginViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigateWithPopInclusive
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull


internal const val LoginRoute = "login"

@ExperimentalComposeUiApi
fun NavGraphBuilder.loginScreen(
    onGoogleSignInClicked: (IntentSender) -> Unit
) {
    composable(LoginRoute) {
        val viewModel: LoginViewModel = hiltViewModel()

        val googleSignInIntent by viewModel.initiateGoogleSignInWindow.observeAsState()
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(viewModel, lifecycleOwner.lifecycle) {
            snapshotFlow { googleSignInIntent }.filterNotNull().flowWithLifecycle(lifecycleOwner.lifecycle).collectLatest {
                onGoogleSignInClicked(it)
            }
        }

        viewModel.uiState.value?.let {
            LoginContent(
                uiState = it,
                onGoogleSignInClicked = { viewModel.onGoogleSignInClicked() }
            )
        }
    }
}

fun NavController.navigateToLogin() {
    singleNavigateWithPopInclusive(LoginRoute, SplashRoute)
}