package gr.dipae.thesisfitnessapp.data.user.broadcast

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.sqrt


class AccelerometerBroadcast(private val context: Context) {

    private var _isRunning: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    var mAccel = 0f
    var mAccelCurrent = SensorManager.GRAVITY_EARTH
    var mAccelLast = SensorManager.GRAVITY_EARTH

    private val accelerometerListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val mGravity = event.values.toList()
                // Shake detection
                val x = mGravity[0]
                val y: Float = mGravity[1]
                val z: Float = mGravity[2]
                mAccelLast = mAccelCurrent
                mAccelCurrent = sqrt(x * x + y * y + z * z)
                val delta: Float = mAccelCurrent - mAccelLast
                mAccel = mAccel * 0.9f + delta

                // Make this higher or lower according to how much motion you want to detect
                _isRunning.value = mAccel > 3
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    }

    fun startMonitoringAccelerometer() {
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(accelerometerListener, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun clear() {
        _isRunning.value = false
        sensorManager.unregisterListener(accelerometerListener)
    }
}