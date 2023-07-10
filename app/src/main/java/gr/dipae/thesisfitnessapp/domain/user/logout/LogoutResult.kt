package gr.dipae.thesisfitnessapp.domain.user.logout

import com.google.firebase.FirebaseException
import okio.IOException

sealed class LogoutResult {
    object Success : LogoutResult()
    data class FirebaseFailure(val firebaseException: FirebaseException) : LogoutResult()
    data class Failure(val exception: Exception = IOException()) : LogoutResult()
}