package com.example.myapplication.model.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlin.math.sqrt

class ShakeDetector(private val onShake: () -> Unit) :
    SensorEventListener {

    private var lastTime = 0L

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = sqrt(x*x + y*y + z*z)

        if (acceleration > 15) {
            val now = System.currentTimeMillis()
            if (now - lastTime > 1500) {
                lastTime = now
                onShake()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}