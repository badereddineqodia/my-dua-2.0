package com.districthut.islam.utils.beadscounter.views.counter.managers

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import com.districthut.islam.utils.beadscounter.views.counter.dpToPx
import com.districthut.islam.utils.beadscounter.views.counter.models.Bead
import com.districthut.islam.utils.beadscounter.views.counter.models.Point
import com.mirfatif.noorulhuda.R
import kotlin.math.sin

const val BEAD_RADIUS_MAX = 16
private const val BEADS_MIN_DISTANCE = 4
private const val BEADS_DEFAULT_DISTANCE = 8
const val BEGINNING_SPACE_BEADS_COUNT = 3

class DrawingBeadsManager(
    private val context: Context,
) {

    private val beadsPaint: Paint by lazy {
        Paint().apply {
            color = beadsColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    private var beadBitmap =
        context.getBitmapFromVectorDrawable(R.drawable.ic_circle)
            ?: BitmapFactory.decodeResource(context.resources, R.drawable.circle_png)

    @ColorInt
    var beadsColor: Int = Color.GREEN
    private var beadAngle: Double = 0.0
    var beadsCount: Int = 0
        set(value) {
            val newValue = value + BEGINNING_SPACE_BEADS_COUNT
            field = newValue
        }

    val doneBeadsCount: Int
        get() = beadsList.filter { it.isDone }.size

    var beadsList = mutableListOf<Bead>()
    var pointList = mutableListOf<Point>()

    fun refreshBeadsList(keepDoneBeads: Boolean = false) {
        val previousBeads = mutableListOf<Bead>().apply {
            addAll(beadsList)
        }
        beadsList.clear()
        pointList.clear()
        beadAngle = 360.0 / beadsCount
        for (i in 0 until beadsCount) {
            val pointAngle: Double = 90 - i * beadAngle
            val point = Point(i, pointAngle, getXCenter(pointAngle), getYCenter(pointAngle))
            pointList.add(point)
            if (i >= BEGINNING_SPACE_BEADS_COUNT) {
                beadsList.add(
                    Bead(point.index, point.xCenter, point.yCenter)
                )
            }
        }
        if (keepDoneBeads) setInitialDoneBeads(previousBeads)
    }

    fun setInitialDoneBeads(oldBeadList: List<Bead>) {
        var index = 0
        oldBeadList.filter { it.isDone }.forEach { previousBead ->
            with(beadsList[index]) {
                pointIndex = previousBead.pointIndex
                isDone = true
                pointList[pointIndex].let {
                    xCenter = it.xCenter
                    yCenter = it.yCenter
                }
            }
            index++
        }
    }

    fun draw(canvas: Canvas) {
        beadsList.forEach { bead ->
            val radius: Float = getBeadCircleRadius()
            val rect = getBeadRect(
                bead.xCenter.toInt(),
                bead.yCenter.toInt(),
                radius.toInt()
            )
            (2 * radius).toInt().let { assertBeadBitmap(it, it) }
            canvas.drawBitmap(beadBitmap, null, rect, null)
        }
    }

    private fun getBeadCircleRadius(): Float {
        val maxGraphicSize = context.dpToPx(2 * BEAD_RADIUS_MAX + BEADS_DEFAULT_DISTANCE)
        val distanceBetweenTwoCirclesCenters =
            2 * beadCenterCoordinatesHelper.radius * sin((beadAngle.div(2.0)).toRadians())
        return if (maxGraphicSize <= distanceBetweenTwoCirclesCenters) {
            context.dpToPx(BEAD_RADIUS_MAX).toFloat()
        } else {
            (distanceBetweenTwoCirclesCenters - context.dpToPx(BEADS_MIN_DISTANCE)).div(2.0)
                .toFloat()
        }
    }

    private fun getBeadRect(x: Int, y: Int, r: Int) =
        Rect(x - r, y - r, x + r, y + r)

    private fun getXCenter(angle: Double): Float =
        beadCenterCoordinatesHelper.getXCenter(angle)

    private fun getYCenter(angle: Double): Float =
        beadCenterCoordinatesHelper.getYCenter(angle)

    private fun assertBeadBitmap(w: Int, h: Int) {
        if (beadBitmap == null || beadBitmap.isRecycled)
            beadBitmap =
                context.getBitmapFromVectorDrawable(R.drawable.ic_circle, w, h)
                    ?: BitmapFactory.decodeResource(context.resources, R.drawable.circle_png)
    }

}