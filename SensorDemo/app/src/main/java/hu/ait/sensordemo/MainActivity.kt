package hu.ait.sensordemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager =
                getSystemService(Context.SENSOR_SERVICE) as SensorManager

        btnList.setOnClickListener {
            listAllSensors()
        }

        btnStart.setOnClickListener {
            startSensor()
        }
    }

    fun listAllSensors() {
        tvData.text = ""

        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        sensorList.forEach {
            tvData.append("${it.name} \n")
        }
    }

    fun startSensor() {
        val mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorManager.registerListener(this, mySensor,
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        sensorManager.unregisterListener(this) // Turn off the sensor monitoring
        super.onStop()
    }

    override fun onSensorChanged(event: SensorEvent) {
        tvData.text="""
            X: ${event.values[0]}
            Y: ${event.values[1]}
            Z: ${event.values[2]}
        """.trimIndent()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}
