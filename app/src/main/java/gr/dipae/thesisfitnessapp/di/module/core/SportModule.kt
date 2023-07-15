package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.sport.SportsDataSource
import gr.dipae.thesisfitnessapp.data.sport.SportsRepositoryImpl
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.framework.sports.SportsDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object SportModule

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SportBindsModule {
    @Binds
    fun bindSportRepository(repository: SportsRepositoryImpl): SportsRepository

    @Binds
    fun bindSportDataSource(dataSource: SportsDataSourceImpl): SportsDataSource
}