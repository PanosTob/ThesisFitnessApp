package gr.dipae.thesisfitnessapp

import android.app.Application
import android.content.SharedPreferences
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import gr.dipae.thesisfitnessapp.di.module.qualifier.GeneralSharedPrefs
import gr.dipae.thesisfitnessapp.domain.profile.language.entity.LanguageResult
import gr.dipae.thesisfitnessapp.util.PREFS_LANGUAGE
import gr.dipae.thesisfitnessapp.util.ext.get
import gr.dipae.thesisfitnessapp.util.ext.set
import io.grpc.android.BuildConfig
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class ThesisFitnessApplication : Application() {

    @Inject
    @GeneralSharedPrefs
    lateinit var prefs: Lazy<SharedPreferences>

    override fun onCreate() {
        super.onCreate()

        // Debug Only
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setupLocale(prefs.get())
    }
}

private fun setupLocale(prefs: SharedPreferences) {

    if (prefs[PREFS_LANGUAGE, ""] == "") {
        val language = Locale.getDefault().language
        prefs[PREFS_LANGUAGE] = when (language) {
            LanguageResult.GREEK.value -> LanguageResult.GREEK.value
            else -> LanguageResult.ENGLISH.value
        }
    }
}
