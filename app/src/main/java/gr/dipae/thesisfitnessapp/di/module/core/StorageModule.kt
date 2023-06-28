package gr.dipae.thesisfitnessapp.di.module.core

import android.content.Context
import android.content.SharedPreferences
import android.location.LocationManager
import androidx.security.crypto.EncryptedSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.di.module.qualifier.CookieSharedPrefs
import gr.dipae.thesisfitnessapp.di.module.qualifier.GeneralSharedPrefs
import gr.dipae.thesisfitnessapp.util.COOKIE_PREFS
import gr.dipae.thesisfitnessapp.util.THESIS_FITNESS_PREFS
import gr.dipae.thesisfitnessapp.util.encryptor.Encryptor

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    /*@Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }*/

    @Provides
    @GeneralSharedPrefs
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            THESIS_FITNESS_PREFS,
            Encryptor.masterKeyAlias(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @CookieSharedPrefs
    fun provideCookiePreferences(@ApplicationContext context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            COOKIE_PREFS,
            Encryptor.masterKeyAlias(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /*@Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }*/

    @Provides
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}