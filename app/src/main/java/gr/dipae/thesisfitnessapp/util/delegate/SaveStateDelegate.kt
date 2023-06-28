package gr.dipae.thesisfitnessapp.util.delegate

import androidx.annotation.Keep
import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Keep
class SaveStateDelegate<T>(private val savedStateHandle: SavedStateHandle, private val key: String) : ReadWriteProperty<Any?, T?> {

    @Keep
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return savedStateHandle[key]
    }

    @Keep
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        savedStateHandle[key] = value
    }
}