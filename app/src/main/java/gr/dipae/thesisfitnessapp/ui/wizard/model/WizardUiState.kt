package gr.dipae.thesisfitnessapp.ui.wizard.model

data class WizardUiState(
    val sports: List<SportUiItem>
)

data class SportUiItem(
    val name: String,
    val imageUrl: String,
    val parameters: List<String>
)