package gr.dipae.thesisfitnessapp.ui.app.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PoDHelper @Inject constructor() {
    var isAlreadyCreated = false

    fun initPoDHelper() {
        isAlreadyCreated = true
    }
}