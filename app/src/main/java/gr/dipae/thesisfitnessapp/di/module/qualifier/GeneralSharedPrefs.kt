package gr.dipae.thesisfitnessapp.di.module.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralSharedPrefs

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CookieSharedPrefs