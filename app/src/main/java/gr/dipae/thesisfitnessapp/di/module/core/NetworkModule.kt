package gr.dipae.thesisfitnessapp.di.module.core

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.BuildConfig
import gr.dipae.thesisfitnessapp.data.diet.typeadapter.FoodNutrientSearchTypeAdapter
import gr.dipae.thesisfitnessapp.data.diet.typeadapter.FoodNutrientTypeAdapter
import gr.dipae.thesisfitnessapp.framework.network.TokenInterceptor
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
    fun provideFoodClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectionSpec: ConnectionSpec,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionSpecs(listOf(connectionSpec))
            .addInterceptor(tokenInterceptor)

        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(httpLoggingInterceptor)
        }

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
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(FoodNutrientTypeAdapter())
        .add(FoodNutrientSearchTypeAdapter())
        .build()

    @Singleton
    @Provides
    fun provideMoshiConverter(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)
}