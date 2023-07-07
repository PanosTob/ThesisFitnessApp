package gr.dipae.thesisfitnessapp.data.user.login.broadcast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class LoginBroadcast {

    private var _googleSignInEnabledState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val googleSignInEnabledState = _googleSignInEnabledState.asStateFlow()

    suspend fun refreshGoogleSignInEnabledState(state: Boolean) {
        Timber.tag(LoginBroadcast::class.simpleName.toString()).e("Emitting google sign in enabled state $state")
        _googleSignInEnabledState.emit(state)
    }

    fun clear() {
        _googleSignInEnabledState.value = null
    }

    companion object {

        @Volatile
        private var INSTANCE: LoginBroadcast? = null

        fun getInstance(): LoginBroadcast {
            return INSTANCE ?: synchronized(this) {
                LoginBroadcast().also { INSTANCE = it }
            }
        }
    }
}