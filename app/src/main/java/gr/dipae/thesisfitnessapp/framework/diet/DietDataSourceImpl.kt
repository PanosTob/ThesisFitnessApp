package gr.dipae.thesisfitnessapp.framework.diet

import gr.dipae.thesisfitnessapp.data.diet.DietDataSource
import gr.dipae.thesisfitnessapp.data.diet.broadcast.DailyDietBroadcast
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteSearchFood
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.util.ext.requireNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DietDataSourceImpl @Inject constructor(
    private val api: FoodApi,
    private val dailyDietBroadcast: DailyDietBroadcast
) : DietDataSource {

    override suspend fun getFoodList(page: Int): List<RemoteFood> {
        return withContext(Dispatchers.IO) {
            api.getFoodList(page = page).requireNotNull()
        }
    }

    override suspend fun searchFoodByName(foodNameQuery: String): List<RemoteSearchFood> {
        return withContext(Dispatchers.IO) {
            api.getFoodByName(foodNameQuery).requireNotNull().foods
        }
    }

    override suspend fun setDailyDiet(dailyDiet: DailyDiet?) {
        dailyDietBroadcast.refreshDailyDietState(dailyDiet)
    }
}