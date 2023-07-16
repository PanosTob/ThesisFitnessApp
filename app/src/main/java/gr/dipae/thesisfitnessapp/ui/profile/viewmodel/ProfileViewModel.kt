package gr.dipae.thesisfitnessapp.ui.profile.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.profile.model.ProfileUiState
import gr.dipae.thesisfitnessapp.usecase.user.GetUserDetailsLocally
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserDetailsLocally: GetUserDetailsLocally
) : BaseViewModel() {

    private val _uiState: MutableState<ProfileUiState?> = mutableStateOf(null)
    val uiState: State<ProfileUiState?> = _uiState

    fun init() {

    }
}