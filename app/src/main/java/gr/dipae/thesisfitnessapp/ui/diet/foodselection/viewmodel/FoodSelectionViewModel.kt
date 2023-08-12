package gr.dipae.thesisfitnessapp.ui.diet.foodselection.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.mapper.FoodSelectionUiMapper
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodSelectionUiState
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodUiItem
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.SearchBarActionType
import gr.dipae.thesisfitnessapp.usecase.diet.GetFoodByNameUseCase
import gr.dipae.thesisfitnessapp.usecase.diet.GetFoodListUseCase
import gr.dipae.thesisfitnessapp.usecase.diet.SetFoodNutrientsUseCase
import javax.inject.Inject

@HiltViewModel
class FoodSelectionViewModel @Inject constructor(
    private val getFoodListUseCase: GetFoodListUseCase,
    private val setFoodNutrientsUseCase: SetFoodNutrientsUseCase,
    private val getFoodByNameUseCase: GetFoodByNameUseCase,
    private val foodSelectionUiMapper: FoodSelectionUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<FoodSelectionUiState> = mutableStateOf(FoodSelectionUiState())
    val uiState: State<FoodSelectionUiState> = _uiState

    private val initialFoodList = mutableListOf<FoodUiItem>()

    private var pageFetched = 1

    fun init() {
        launchWithProgress {
            val foodList = foodSelectionUiMapper(getFoodListUseCase(pageFetched))
            _uiState.value = foodList
            initialFoodList.addAll(foodList.foodList)
        }
    }

    fun onClearSearch() {
        _uiState.value.foodList.clear()
        _uiState.value.foodList.addAll(initialFoodList)
        _uiState.value.searchBarTrailingActionType.value = SearchBarActionType.Search
    }

    fun getFoodListNextPage() {
        if (_uiState.value.searchBarTrailingActionType.value is SearchBarActionType.Search) {
            launchWithProgress {
                val nextPageFoods = foodSelectionUiMapper.mapFoods((getFoodListUseCase(++pageFetched)))
                _uiState.value.foodList.addAll(nextPageFoods)
                initialFoodList.addAll(nextPageFoods)
            }
        }
    }

    fun onFoodItemClicked(item: FoodUiItem) {
        _uiState.value.selectedFoodItem.value = item
    }

    fun onSearchFood(searchString: String) {
        launchWithProgress {
            _uiState.value.searchBarTrailingActionType.value = SearchBarActionType.Clear
            _uiState.value.foodList.clear()
            _uiState.value.foodList.addAll(foodSelectionUiMapper.mapFoods(getFoodByNameUseCase(searchString)))
        }
    }

    fun onNutrientsSaveClicked(grams: String) {
        launchWithProgress {
            setFoodNutrientsUseCase(foodSelectionUiMapper.mapFoodNutrientsRequest(_uiState.value.selectedFoodItem.value), grams.toLongOrNull())
        }
    }
}