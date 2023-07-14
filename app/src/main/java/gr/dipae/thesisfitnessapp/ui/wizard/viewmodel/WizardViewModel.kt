package gr.dipae.thesisfitnessapp.ui.wizard.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.app.entity.FirebaseWriteDocumentResult
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.wizard.mapper.WizardUiMapper
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardUiState
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportsUseCase
import gr.dipae.thesisfitnessapp.usecase.user.SetUserFitnessProfileUseCase
import gr.dipae.thesisfitnessapp.usecase.wizard.SetWizardDetailsLocallyUseCase
import javax.inject.Inject

@HiltViewModel
class WizardViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val setWizardDetailsLocallyUseCase: SetWizardDetailsLocallyUseCase,
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
                val result = setUserFitnessProfileUseCase(uiState.value?.toUserDetails())
                if (result is FirebaseWriteDocumentResult.Success) {
                    goToDashboardState.value = true
                }
            }
        }
    }

    fun onNextStep() {
        launch {
            setWizardDetailsLocallyUseCase(uiState.value?.toUserDetails())
        }
    }
}