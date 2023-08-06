package gr.dipae.thesisfitnessapp.data.diet

import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood

interface DietDataSource {
    suspend fun getFoodList(page: Int): List<RemoteFood>
}