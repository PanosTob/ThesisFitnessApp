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
import gr.dipae.thesisfitnessapp.ui.welcome.composable.LoginContent
import gr.dipae.thesisfitnessapp.ui.welcome.viewmodel.LoginViewModel
import gr.dipae.thesisfitnessapp.util.ext.singleNavigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull


private const val LoginRoute = "welcome"

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

        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }

        LoginContent(
            uiState = viewModel.uiState.value,
            onGoogleSignInClicked = { viewModel.onGoogleSignInClicked(it) }
        )
    }
}

fun NavController.navigateToLogin() {
    singleNavigate(LoginRoute)
}