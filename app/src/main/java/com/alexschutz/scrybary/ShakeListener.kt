package com.alexschutz.scrybary

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

interface ShakeListener : SensorEventListener {

    override fun onSensorChanged(event: SensorEvent?) {

        var acceleration = 0f
        var currentAcceleration = 0f

        val x = event?.values?.get(0)
        val y = event?.values?.get(1)
        val z = event?.values?.get(2)

        val lastAcceleration: Float = currentAcceleration

        if (x != null && y != null && z != null)
            currentAcceleration = kotlin.math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()

        val delta: Float = currentAcceleration - lastAcceleration

        acceleration = acceleration * 0.9f + delta

        if (acceleration > 20) {
            shakeDetected()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing.
    }

    fun shakeDetected()
}