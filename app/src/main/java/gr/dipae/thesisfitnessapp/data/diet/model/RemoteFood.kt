package gr.dipae.thesisfitnessapp.data.diet.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import gr.dipae.thesisfitnessapp.domain.diet.entity.FoodNutrientType
import gr.dipae.thesisfitnessapp.domain.diet.entity.SearchFoodNutrientType

@JsonClass(generateAdapter = true)
data class RemoteSearchFoodResponse(
    val foods: List<RemoteSearchFood>
)

@JsonClass(generateAdapter = true)
data class RemoteFood(
    @Json(name = "fdcId")
    val id: Int?,
    @Json(name = "description")
    val name: String?,
    @Json(name = "foodNutrients")
    val nutrients: List<RemoteFoodNutrient?>?
)

@JsonClass(generateAdapter = true)
data class RemoteSearchFood(
    @Json(name = "fdcId")
    val id: Int?,
    @Json(name = "description")
    val name: String?,
    @Json(name = "foodNutrients")
    val nutrients: List<RemoteSearchFoodNutrient?>?
)

@JsonClass(generateAdapter = true)
data class RemoteFoodNutrient(
    @Json(name = "number")
    val type: FoodNutrientType?,
    val amount: Double?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class RemoteSearchFoodNutrient(
    @Json(name = "nutrientId")
    val type: SearchFoodNutrientType?,
    val value: Double?,
    val nutrientName: String?
)