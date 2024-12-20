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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import gr.dipae.thesisfitnessapp.framework.service.SportSessionService
import gr.dipae.thesisfitnessapp.ui.app.composable.ActivityProgressContainer
import gr.dipae.thesisfitnessapp.ui.base.BaseActivity
import gr.dipae.thesisfitnessapp.ui.livedata.LoadingLiveData
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme
import gr.dipae.thesisfitnessapp.util.connectivity.ConnectivityStatus
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@DelicateCoroutinesApi
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
            networkLiveData.observe(this@AppActivity, Observer(this@AppActivity::onNetworkConnectivityChange))
            submitLanguageChange.observe(this@AppActivity, Observer(::submitLanguageChange))
            initiateGoogleSignInWindow.observe(this@AppActivity, Observer(::initiateGoogleSignInWindow))
            startStopWatch.observe(this@AppActivity, Observer(::startStopWatch))
            pauseStopWatch.observe(this@AppActivity, Observer(::pauseStopWatch))
            stopStopWatch.observe(this@AppActivity, Observer(::stopStopWatch))
        }
    }

    private fun restartApp(unit: Unit) {
        restartApp()
    }

    private fun recreateApp(unit: Unit) {
        recreate()
    }

    private fun onNetworkConnectivityChange(status: ConnectivityStatus) {
//        binding.connectivityTxt.isVisible = status == ConnectivityStatus.DISCONNECTED
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

    //STOP WATCH SERVICE
    private fun startStopWatch(unit: Unit) {
        startService(
            Intent(this, SportSessionService::class.java).apply { action = SportSessionService.STOP_WATCH_ACTION_START }
        )
    }

    private fun pauseStopWatch(unit: Unit) {
        startService(
            Intent(this, SportSessionService::class.java).apply { action = SportSessionService.STOP_WATCH_ACTION_PAUSE }
        )
    }

    private fun stopStopWatch(unit: Unit) {
        stopService(Intent(this, SportSessionService::class.java))
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