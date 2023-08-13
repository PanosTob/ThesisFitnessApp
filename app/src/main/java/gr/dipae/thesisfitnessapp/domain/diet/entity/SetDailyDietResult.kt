package gr.dipae.thesisfitnessapp.domain.diet.entity

sealed class SetDailyDietResult {
    object Success : SetDailyDietResult()
    object Failure : SetDailyDietResult()
}