package gr.dipae.thesisfitnessapp.di.module.core

import android.content.SharedPreferences
import androidx.compose.ui.layout.Placeable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.di.module.qualifier.CookieSharedPrefs
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*@Singleton
    @Provides
    fun providePersistentCookieJar(moshi: Moshi, @CookieSharedPrefs prefs: SharedPreferences):  {
        return (moshi, prefs)
    }*/

    @Singleton
    @Provides
    fun provideGeneralOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectionSpec: ConnectionSpec
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionSpecs(listOf(connectionSpec))

        /*if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(httpLoggingInterceptor)
        }*/

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
            .allEnabledTlsVersions()
            .allEnabledCipherSuites()
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideMoshiConverter(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)
}