package gr.dipae.thesisfitnessapp.util.ext

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

fun Context.updateLocale(lang: String): ContextWrapper {
    val configuration: Configuration = resources.configuration
    val localeToSwitchTo = Locale(lang)
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
        configuration.setLocale(localeToSwitchTo)
        val localeList = LocaleList(localeToSwitchTo)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
        ContextWrapper(createConfigurationContext(configuration))
    } else {
        Locale.setDefault(localeToSwitchTo)
        configuration.locale = localeToSwitchTo
        resources.updateConfiguration(configuration, resources.displayMetrics)
        ContextWrapper(this)
    }
}