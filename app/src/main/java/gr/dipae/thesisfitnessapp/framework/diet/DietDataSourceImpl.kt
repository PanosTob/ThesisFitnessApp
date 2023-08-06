package gr.dipae.thesisfitnessapp.framework.diet

import gr.dipae.thesisfitnessapp.data.diet.DietDataSource
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import gr.dipae.thesisfitnessapp.util.ext.requireNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DietDataSourceImpl @Inject constructor(
    private val api: FoodApi
) : DietDataSource {
    override suspend fun getFoodList(page: Int): List<RemoteFood> {
        return withContext(Dispatchers.IO) {
            api.getFoodList(page = page).requireNotNull()
        }
    }
}