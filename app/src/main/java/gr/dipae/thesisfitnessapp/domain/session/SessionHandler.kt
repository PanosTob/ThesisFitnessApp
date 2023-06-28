package gr.dipae.thesisfitnessapp.domain.session

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SessionHandler @Inject constructor() {

    var loggedInOnCurrentSession: Boolean = false

    companion object {

        @Volatile
        private var INSTANCE: SessionHandler? = null

        fun getInstance(): SessionHandler {
            return INSTANCE ?: synchronized(this) {
                SessionHandler().also { INSTANCE = it }
            }
        }
    }
}