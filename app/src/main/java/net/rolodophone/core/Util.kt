package net.rolodophone.core

import android.graphics.PointF
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun w(n: Int): Float = width/360f * n
fun w(n: Float): Float = width/360f * n
fun h(n: Int): Float = height/360f * n
fun h(n: Float): Float = height/360f * n

private val randomGen = Random()

fun gaussianRandomFloat(mean: Float, std: Float) = randomGen.nextGaussian().toFloat() * std + mean
fun gaussianRandomInt(mean: Float, std: Float) = (randomGen.nextGaussian() * std + mean).toInt()

fun randomFloat(min: Float, max: Float) = (randomGen.nextFloat()) * (max-min) + min

fun posFromDeg(centerX: Float, centerY: Float, radius: Float, angle: Float): PointF {
    return PointF(
        centerX + radius * cos(angle.toRadians()).toFloat(),
        centerY + radius * sin(angle.toRadians()).toFloat()
    )
}

fun Float.toRadians() = this * PI / 180f
fun Double.toDegrees() = (this * 180 / PI).toFloat()