package gr.dipae.thesisfitnessapp.ui.wizard.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.app.entity.FirebaseWriteDocumentResult
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.wizard.mapper.WizardUiMapper
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardUiState
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportsUseCase
import gr.dipae.thesisfitnessapp.usecase.user.SetUserFitnessProfileUseCase
import javax.inject.Inject

@HiltViewModel
class WizardViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val setUserFitnessProfileUseCase: SetUserFitnessProfileUseCase,
    private val wizardUiMapper: WizardUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<WizardUiState?> = mutableStateOf(null)
    val uiState: State<WizardUiState?> = _uiState

    fun init() {
        launchWithProgress {
            _uiState.value = wizardUiMapper(getSportsUseCase())
        }
    }

    fun saveWizardDetails() {
        launchWithProgress {
            _uiState.value?.apply {
                val result = setUserFitnessProfileUseCase(
                    userName = usernameState.value,
                    fitnessLevel = fitnessLevels.find { it.isSelectedState.value }?.fitnessLevel ?: FitnessLevel.Unknown,
                    favoriteSports = sports.filter { it.selected.value }.map { it.id },
                    calories = dailyDietGoal.caloriesState.value,
                    carbs = dailyDietGoal.carbohydratesState.value,
                    fats = dailyDietGoal.fatsState.value,
                    proteins = dailyDietGoal.proteinsState.value,
                    waterML = dailyDietGoal.waterMLState.value
                )
                if (result is FirebaseWriteDocumentResult.Success) {
                    goToDashboardState.value = true
                }
            }
        }
    }
}