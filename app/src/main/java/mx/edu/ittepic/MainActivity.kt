package mx.edu.ittepic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    var p = ArrayList<Float>()
    var p2 = ArrayList<Float>()
    var proximitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            var temp = ArrayList<Float>()
            (event.values.indices).forEach {
                temp.add(event.values[it])
            }
            p2 = temp
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL)
        title = "Laberinto"

        val proximitySensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if(proximitySensor == null) {
            finish()
        }
        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2 * 1000 * 1000);

        var lienzo = Lienzo(this)
        setContentView(lienzo)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onSensorChanged(event: SensorEvent) {
        var temp = ArrayList<Float>()
        (event.values.indices).forEach {
            temp.add(event.values[it])
        }
        this.p = temp
    }

}