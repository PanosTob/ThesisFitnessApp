package gr.dipae.thesisfitnessapp.domain.sport.entity

import java.io.IOException

sealed class SportSessionSaveResult {
    object Success : SportSessionSaveResult()
    data class Failure(val exception: Exception = IOException()) : SportSessionSaveResult()
}