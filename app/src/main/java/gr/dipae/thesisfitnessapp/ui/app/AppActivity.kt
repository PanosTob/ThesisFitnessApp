package gr.dipae.thesisfitnessapp.ui.app

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.dipae.thesisfitnessapp.ui.base.BaseActivity
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@ExperimentalComposeUiApi
@AndroidEntryPoint
class AppActivity : BaseActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
        setContent {
            ThesisFitnessAppTheme(statusBarColor = Color.Black) {
                AppNavHost(viewModel)
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

    private fun initiateGoogleSignInWindow(intent: IntentSender) {
        startIntentSenderForResult(
            intent, REQ_ONE_TAP,
            null, 0, 0, 0, null
        )
        /*startActivityForResult(intent, REQ_ONE_TAP)
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.signInUser(it.data)
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_ONE_TAP) {
            viewModel.signInUser(data)
        }
    }

    companion object {

        const val REQ_ONE_TAP = 1

        fun Activity.restartApp() {
            finishAffinity()
            val intent = Intent(this, AppActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}