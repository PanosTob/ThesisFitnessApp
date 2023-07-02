package gr.dipae.thesisfitnessapp.util.ext

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.util.APP_NAME
import timber.log.Timber

fun FirebaseException.handleFirebaseException() {
    Timber.tag(APP_NAME).e(this)
    return when (this) {
        is FirebaseNetworkException -> {}
        is FirebaseAuthInvalidUserException -> {}
        is FirebaseAuthInvalidCredentialsException -> {}
        is FirebaseAuthUserCollisionException -> {}
        is FirebaseTooManyRequestsException -> {}
        is FirebaseNoSignedInUserException -> {}
        else -> {}
    }
}