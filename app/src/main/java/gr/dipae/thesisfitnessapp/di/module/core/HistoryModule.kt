package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.history.HistoryDataSource
import gr.dipae.thesisfitnessapp.data.history.HistoryRepositoryImpl
import gr.dipae.thesisfitnessapp.domain.history.HistoryRepository
import gr.dipae.thesisfitnessapp.framework.history.HistoryDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule

@Module
@InstallIn(ActivityRetainedComponent::class)
interface HistoryBindsRepository {
    @Binds
    fun bindHistoryRepository(repository: HistoryRepositoryImpl): HistoryRepository

    @Binds
    fun bindHistoryDataSource(dataSource: HistoryDataSourceImpl): HistoryDataSource
}