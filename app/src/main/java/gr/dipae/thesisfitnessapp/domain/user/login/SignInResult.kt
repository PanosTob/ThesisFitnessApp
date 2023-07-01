package gr.dipae.thesisfitnessapp.domain.user.login

sealed class SignInResult {
    object Success : SignInResult()
    object Failure : SignInResult()
}