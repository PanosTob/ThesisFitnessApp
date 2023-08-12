package gr.dipae.thesisfitnessapp.ui.diet.foodselection.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.mapper.FoodSelectionUiMapper
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodSelectionUiState
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodUiItem
import gr.dipae.thesisfitnessapp.usecase.diet.GetFoodListUseCase
import gr.dipae.thesisfitnessapp.usecase.diet.SetFoodNutrientsUseCase
import javax.inject.Inject

@HiltViewModel
class FoodSelectionViewModel @Inject constructor(
    private val getFoodListUseCase: GetFoodListUseCase,
    private val setFoodNutrientsUseCase: SetFoodNutrientsUseCase,
    private val foodSelectionUiMapper: FoodSelectionUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<FoodSelectionUiState> = mutableStateOf(FoodSelectionUiState())
    val uiState: State<FoodSelectionUiState> = _uiState

    fun init() {
        launchWithProgress {
            _uiState.value = foodSelectionUiMapper(getFoodListUseCase())
        }
    }

    fun getFoodListNextPage() {
        launchWithProgress {
            _uiState.value.page.value += 1
            _uiState.value.foodList.addAll(foodSelectionUiMapper.mapFoods((getFoodListUseCase(_uiState.value.page.value))))
        }
    }

    fun onFoodItemClicked(item: FoodUiItem) {
        _uiState.value.selectedFoodItem.value = item
    }

    fun onNutrientsSaveClicked(grams: String) {
        launchWithProgress {
            setFoodNutrientsUseCase(foodSelectionUiMapper.mapFoodNutrientsRequest(_uiState.value.selectedFoodItem.value), grams.toLongOrNull())
        }
    }
}