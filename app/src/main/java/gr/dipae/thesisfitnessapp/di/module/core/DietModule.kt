package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.BuildConfig
import gr.dipae.thesisfitnessapp.data.diet.DietDataSource
import gr.dipae.thesisfitnessapp.data.diet.DietRepositoryImpl
import gr.dipae.thesisfitnessapp.data.diet.broadcast.DailyDietBroadcast
import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.framework.diet.DietDataSourceImpl
import gr.dipae.thesisfitnessapp.framework.diet.FoodApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DietModule {

    @Provides
    fun provideDailyDietBroadcast(): DailyDietBroadcast {
        return DailyDietBroadcast.getInstance()
    }

    @Provides
    fun provideFoodApi(
        converterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): FoodApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.FOOD_API_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(FoodApi::class.java)
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DietBindsModule {
    @Binds
    fun bindDietRepository(repository: DietRepositoryImpl): DietRepository

    @Binds
    fun bindDietDataSource(dataSource: DietDataSourceImpl): DietDataSource
}