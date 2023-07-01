package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.workout.WorkoutDataSource
import gr.dipae.thesisfitnessapp.data.workout.WorkoutRepositoryImpl
import gr.dipae.thesisfitnessapp.domain.workout.WorkoutRepository
import gr.dipae.thesisfitnessapp.framework.workout.WorkoutDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object WorkoutModule

@Module
@InstallIn(ActivityRetainedComponent::class)
interface WorkoutBindsModule {
    @Binds
    fun bindWorkoutRepository(repository: WorkoutRepositoryImpl): WorkoutRepository

    @Binds
    fun bindWorkoutDataSource(dataSource: WorkoutDataSourceImpl): WorkoutDataSource
}