package gr.dipae.thesisfitnessapp.ui.app

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.session.SessionHandler
import gr.dipae.thesisfitnessapp.ui.app.model.PoDHelper
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.livedata.NetworkLiveData
import gr.dipae.thesisfitnessapp.ui.livedata.SingleLiveEvent
import gr.dipae.thesisfitnessapp.util.SAVE_STATE_APP
import gr.dipae.thesisfitnessapp.util.delegate.SaveStateDelegate
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val networkLiveData: NetworkLiveData,
    private val sessionHandler: SessionHandler,
    private val podHelper: PoDHelper,
) : BaseViewModel() {

    private val _navigateId = SingleLiveEvent<Triple<Int, Bundle?, NavOptions?>>()
    val navigateId: LiveData<Triple<Int, Bundle?, NavOptions?>> = _navigateId

    private val _restartApp = SingleLiveEvent<Unit>()
    val restartApp: LiveData<Unit> = _restartApp

    private val _recreateApp = SingleLiveEvent<Unit>()
    val recreateApp: LiveData<Unit> = _recreateApp

    private val _submitLanguageChange = MutableLiveData<Unit>()
    val submitLanguageChange: LiveData<Unit> = _submitLanguageChange

    private val _initApp = SingleLiveEvent<Boolean>()

    private var isAlreadyCreated by SaveStateDelegate<Boolean>(savedStateHandle = savedStateHandle, key = SAVE_STATE_APP)

    fun initApp() {
        // catch PoD & DkA cases - restart app
        val processOfDeathCase = isAlreadyCreated == true && !podHelper.isAlreadyCreated
        val dontKeepActivitiesCase = isAlreadyCreated == true && podHelper.isAlreadyCreated && _initApp.value == null
        if (processOfDeathCase || dontKeepActivitiesCase) {
            _restartApp.value = Unit
        }

        isAlreadyCreated = true
        _initApp.value = true
        podHelper.initPoDHelper()
    }


    fun recreateApp() {
        launch {
            delay(200)
            _recreateApp.value = Unit
        }
    }
}