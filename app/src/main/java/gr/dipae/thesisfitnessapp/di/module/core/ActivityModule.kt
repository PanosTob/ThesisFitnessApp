package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.activity.ActivityDataSource
import gr.dipae.thesisfitnessapp.data.activity.ActivityRepositoryImpl
import gr.dipae.thesisfitnessapp.domain.activity.ActivityRepository
import gr.dipae.thesisfitnessapp.framework.activity.ActivityDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object ActivityModule

@Module
@InstallIn(ActivityRetainedComponent::class)
interface ActivityBindsModule {
    @Binds
    fun bindActivityRepository(repository: ActivityRepositoryImpl): ActivityRepository

    @Binds
    fun bindActivityDataSource(dataSource: ActivityDataSourceImpl): ActivityDataSource
}