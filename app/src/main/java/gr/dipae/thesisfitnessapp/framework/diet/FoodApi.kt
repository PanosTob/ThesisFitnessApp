package gr.dipae.thesisfitnessapp.framework.diet

import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteSearchFoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET("v1/foods/list?dataType=Foundation")
    suspend fun getFoodList(
        @Query("pageSize") pageSize: Int = 25,
        @Query("pageNumber") page: Int
    ): Response<List<RemoteFood>>

    @GET("/fdc/v1/foods/search?dataType=Foundation")
    suspend fun getFoodByName(
        @Query("query") foodNameQuery: String
    ): Response<RemoteSearchFoodResponse>
}