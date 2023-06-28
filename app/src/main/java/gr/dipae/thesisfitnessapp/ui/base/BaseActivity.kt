package gr.dipae.thesisfitnessapp.ui.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.CallSuper
import androidx.security.crypto.EncryptedSharedPreferences
import gr.dipae.thesisfitnessapp.domain.profile.language.entity.LanguageResult
import gr.dipae.thesisfitnessapp.util.PREFS_LANGUAGE
import gr.dipae.thesisfitnessapp.util.THESIS_FITNESS_PREFS
import gr.dipae.thesisfitnessapp.util.encryptor.Encryptor
import gr.dipae.thesisfitnessapp.util.ext.get
import gr.dipae.thesisfitnessapp.util.ext.updateLocale

abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateLocale(getSavedLanguage(this))
    }

    @CallSuper
    override fun attachBaseContext(newBase: Context) {
        val localeToSwitchTo = getSavedLanguage(newBase)
        val localeUpdatedContext = newBase.updateLocale(localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    private fun getSavedLanguage(context: Context): String {
        val prefs = EncryptedSharedPreferences.create(
            context,
            THESIS_FITNESS_PREFS,
            Encryptor.masterKeyAlias(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return prefs[PREFS_LANGUAGE] ?: LanguageResult.ENGLISH.value
    }
}


