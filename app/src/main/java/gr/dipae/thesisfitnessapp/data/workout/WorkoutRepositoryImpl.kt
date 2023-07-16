package gr.dipae.thesisfitnessapp.data.workout

import gr.dipae.thesisfitnessapp.data.workout.mapper.WorkoutMapper
import gr.dipae.thesisfitnessapp.domain.workout.WorkoutRepository
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val dataSource: WorkoutDataSource,
    private val workoutMapper: WorkoutMapper
) : WorkoutRepository {
    override suspend fun getWorkouts(): List<Workout> {
        return workoutMapper(dataSource.getWorkouts())
    }
}