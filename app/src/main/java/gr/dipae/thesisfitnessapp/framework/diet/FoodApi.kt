package gr.dipae.thesisfitnessapp.framework.diet

import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET("v1/foods/list?dataType=Foundation")
    suspend fun getFoodList(
        @Query("pageSize") pageSize: Int = 25,
        @Query("pageNumber") page: Int
    ): Response<List<RemoteFood>>


}