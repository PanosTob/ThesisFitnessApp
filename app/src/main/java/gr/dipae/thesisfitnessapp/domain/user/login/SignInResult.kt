package gr.dipae.thesisfitnessapp.domain.user.login

import com.google.firebase.FirebaseException
import okio.IOException

sealed class SignInResult {
    object Success : SignInResult()
    data class FirebaseFailure(val firebaseException: FirebaseException) : SignInResult()
    data class Failure(val exception: Exception = IOException()) : SignInResult()
}