package gr.dipae.thesisfitnessapp.util.ext

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.tasks.await
import timber.log.Timber

suspend inline fun <reified T> DocumentReference.getDocumentResponse(): T? {
    return get().await().toObject<T>()
}

suspend inline fun <reified T> CollectionReference.getDocumentsResponse(): List<T> {
    return get().await().toObjects(T::class.java)
}

inline fun <reified T> T.toJson(moshi: Moshi): String {
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
    return try {
        jsonAdapter.toJson(this)
    } catch (e: Exception) {
        ""
    }
}

fun <T> String.fromJson(moshi: Moshi, modelClass: Class<T>): T? {
    val jsonAdapter: JsonAdapter<T>? = moshi.adapter(modelClass)

    return try {
        jsonAdapter?.fromJson(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}