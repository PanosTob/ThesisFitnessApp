package gr.dipae.thesisfitnessapp.util.ext

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

suspend inline fun <reified T> Task<DocumentSnapshot>.documentToResponse(): T? {
    return await().toObject<T>()
}