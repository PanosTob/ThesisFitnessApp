package gr.dipae.thesisfitnessapp.ui.app

import android.app.Activity
import android.content.Intent
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

    companion object {
        fun Activity.restartApp() {
            finishAffinity()
            val intent = Intent(this, AppActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}