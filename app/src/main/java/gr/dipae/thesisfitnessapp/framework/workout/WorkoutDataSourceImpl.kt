package gr.dipae.thesisfitnessapp.framework.workout

import com.google.firebase.firestore.FirebaseFirestore
import gr.dipae.thesisfitnessapp.data.workout.WorkoutDataSource
import gr.dipae.thesisfitnessapp.data.workout.model.RemoteWorkout
import gr.dipae.thesisfitnessapp.util.WORKOUTS_COLLECTION
import gr.dipae.thesisfitnessapp.util.ext.getDocumentsResponse
import javax.inject.Inject

class WorkoutDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : WorkoutDataSource {
    override suspend fun getWorkouts(): List<RemoteWorkout> {
        return fireStore.collection(WORKOUTS_COLLECTION).getDocumentsResponse()
    }
}