package com.districthut.islam.utils.beadscounter.views.counter.managers

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class BeadCenterCoordinatesHelper {
    var mainXCenter = 0
    var mainYCenter = 0
    var radius = 0

    fun getXCenter(angle: Double): Float {
        return when {
            angle >= -90 -> mainXCenter + (radius * cos(angle.toRadians()))
            angle > -180 -> mainXCenter - (radius * cos((180 + angle).toRadians()))
            else -> mainXCenter - (radius * cos((180 + angle).toRadians()))
        }.toFloat()
    }

    fun getYCenter(angle: Double): Float {
        return when {
            angle > 0 -> mainYCenter - (radius * sin(angle.toRadians()))
            angle >= -90 -> mainYCenter + (radius * sin(abs(angle.toRadians())))
            angle > -180 -> mainYCenter + (radius * sin((180 + angle).toRadians()))
            else -> mainYCenter - (radius * sin(abs(angle + 180).toRadians()))
        }.toFloat()
    }
}

fun Double.toRadians() = Math.toRadians(this)

val beadCenterCoordinatesHelper = BeadCenterCoordinatesHelper()
