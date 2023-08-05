package gr.dipae.thesisfitnessapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.dipae.thesisfitnessapp.ui.livedata.LoadingLiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun launch(function: suspend () -> Unit): Job {
        return viewModelScope.launch {
            function.invoke()
        }
    }

    protected fun launchWithProgress(
        preload: suspend () -> Unit = {},
        postload: () -> Unit = {},
        function: suspend () -> Unit
    ) {
        viewModelScope.launch {
            preload.invoke()
            LoadingLiveData.postValue(true)
            function.invoke()
        }.apply {
            invokeOnCompletion {
                postload.invoke()
                LoadingLiveData.postValue(false)
            }
        }
    }
}