package gr.dipae.thesisfitnessapp.util.ext

import com.google.android.gms.tasks.Task

fun <T : Any> Task<T>.requireNotNull(): T {
    require(isSuccessful && result != null)
    return result!!
}