package gr.dipae.thesisfitnessapp.domain.app.entity

sealed class FirebaseWriteDocumentResult {
    object Success : FirebaseWriteDocumentResult()
    object Failure : FirebaseWriteDocumentResult()
}