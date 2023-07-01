package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.diet.DietDataSource
import gr.dipae.thesisfitnessapp.data.diet.DietRepositoryImpl
import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.framework.diet.DietDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object DietModule {
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DietBindsModule {
    @Binds
    fun bindDietRepository(repository: DietRepositoryImpl): DietRepository

    @Binds
    fun bindDietDataSource(dataSource: DietDataSourceImpl): DietDataSource
}