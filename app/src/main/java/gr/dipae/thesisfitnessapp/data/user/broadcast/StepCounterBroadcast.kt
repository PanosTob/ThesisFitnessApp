package gr.dipae.thesisfitnessapp.data.user.broadcast

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class StepCounterBroadcast(private val context: Context) {

    private var _stepCounterValue: MutableStateFlow<Long?> = MutableStateFlow(null)
    val stepCounterValue = _stepCounterValue.asStateFlow()

    private val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    private val stepCounterListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                _stepCounterValue.value = event.values[0].toLong()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    }

    suspend fun updateStepCounter(counter: Long) {
        Timber.tag(StepCounterBroadcast::class.simpleName.toString()).e("Emitting step counter value $counter")
        _stepCounterValue.emit(counter)
    }

    fun startMonitoringSteps() {
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(stepCounterListener, stepSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun clear() {
        _stepCounterValue.value = null
        sensorManager.unregisterListener(stepCounterListener)
    }
}