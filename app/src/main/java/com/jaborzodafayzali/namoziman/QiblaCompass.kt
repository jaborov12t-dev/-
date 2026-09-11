package com.jaborzodafayzali.namoziman

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.roundToInt

class QiblaCompass(
    context: Context,
    private val onHeadingChanged: (Float) -> Unit
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val rotationSensor =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val magnetometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val rotationMatrix = FloatArray(9)
    private val orientation = FloatArray(3)

    private var gravity = FloatArray(3)
    private var magnetic = FloatArray(3)

    private var hasGravity = false
    private var hasMagnetic = false

    fun start() {

        if (rotationSensor != null) {

            sensorManager.registerListener(
                this,
                rotationSensor,
                SensorManager.SENSOR_DELAY_UI
            )

        } else {

            accelerometer?.let {
                sensorManager.registerListener(
                    this,
                    it,
                    SensorManager.SENSOR_DELAY_UI
                )
            }

            magnetometer?.let {
                sensorManager.registerListener(
                    this,
                    it,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {

            SensorManager.getRotationMatrixFromVector(
                rotationMatrix,
                event.values
            )

            calculateHeading()

            return
        }

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {

            gravity = event.values.clone()
            hasGravity = true
        }

        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {

            magnetic = event.values.clone()
            hasMagnetic = true
        }

        if (hasGravity && hasMagnetic) {

            val success =
                SensorManager.getRotationMatrix(
                    rotationMatrix,
                    null,
                    gravity,
                    magnetic
                )

            if (success) {
                calculateHeading()
            }
        }
    }

    private fun calculateHeading() {

        SensorManager.getOrientation(
            rotationMatrix,
            orientation
        )

        var heading =
            Math.toDegrees(
                orientation[0].toDouble()
            ).toFloat()

        if (heading < 0) {
            heading += 360f
        }

        onHeadingChanged(heading)
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
        // Ничего делать не нужно
    }
}
