package gr.dipae.thesisfitnessapp.util.ext

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

suspend inline fun <reified T> DocumentReference.getDocumentResponse(): T? {
    return get().await().toObject<T>()
}

suspend inline fun <reified T> CollectionReference.getDocumentsResponse(): List<T> {
    return get().await().toObjects(T::class.java)
}