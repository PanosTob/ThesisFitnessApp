package gr.dipae.thesisfitnessapp.ui.app

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.dipae.thesisfitnessapp.ui.app.composable.ActivityProgressContainer
import gr.dipae.thesisfitnessapp.ui.base.BaseActivity
import gr.dipae.thesisfitnessapp.ui.livedata.LoadingLiveData
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class AppActivity : BaseActivity() {

    private val viewModel: AppViewModel by viewModels()

    private var googleSignInIntentListener: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
        registerGoogleSignInIntentListener()
        setContent {
            ThesisFitnessAppTheme(statusBarColor = Color.Black) {
                ActivityProgressContainer(progressDisplayed = LoadingLiveData.observeAsState(false).value) {
                    AppNavHost(viewModel)
                }
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            restartApp.observe(this@AppActivity, Observer(this@AppActivity::restartApp))
            recreateApp.observe(this@AppActivity, Observer(this@AppActivity::recreateApp))
            submitLanguageChange.observe(this@AppActivity, Observer(::submitLanguageChange))
            initiateGoogleSignInWindow.observe(this@AppActivity, Observer(::initiateGoogleSignInWindow))
        }
    }

    private fun restartApp(unit: Unit) {
        restartApp()
    }

    private fun recreateApp(unit: Unit) {
        recreate()
    }

    private fun submitLanguageChange(unit: Unit) {
        viewModel.recreateApp()
    }

    private fun registerGoogleSignInIntentListener() {
        googleSignInIntentListener = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {
            viewModel.signInUser(it.data, it.resultCode)
        }
    }

    private fun initiateGoogleSignInWindow(intentSender: IntentSender) {
        val intent = IntentSenderRequest.Builder(intentSender).build()
        googleSignInIntentListener?.launch(intent)
    }

    companion object {
        fun Activity.restartApp() {
            finishAffinity()
            val intent = Intent(this, AppActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}