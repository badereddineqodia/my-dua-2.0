package com.districthut.islam.utils.beadscounter.views.counter.managers

import android.content.Context
import android.os.Handler
import com.districthut.islam.utils.beadscounter.views.counter.models.Bead
import com.districthut.islam.utils.beadscounter.views.counter.models.Movement

private const val ANIMATION_DURATION = 200
private const val ANIMATION_UPDATE_INTERVAL = 10

class AnimationManager(
    context: Context,
    private val beadsManager: DrawingBeadsManager,
    private val listener: AnimationListener,
) {

    private val handler = Handler(context.mainLooper)

    fun onMoveNextBead() {
        val bead = getNextMovingBead()
        bead ?: return
        val movement = Movement(
            beadsManager.pointList[bead.pointIndex],
            beadsManager.pointList[getNextAvailablePointIndex()],
            bead
        )
        playBeadAnimation(movement)
    }

    private fun playBeadAnimation(movement: Movement) {
        val angles = mutableListOf<Double>()
        with(movement) {
            bead.isDone = true
            bead.pointIndex = beadsManager.pointList.indexOf(toPoint)
            val angleDifference = toPoint.angle - fromPoint.angle
            for (i in 0..ANIMATION_DURATION step ANIMATION_UPDATE_INTERVAL) {
                angles.add(fromPoint.angle + (i * angleDifference / ANIMATION_DURATION))
            }
            animateByAngle(this, angles)
        }

    }

    private fun animateByAngle(movement: Movement, angles: MutableList<Double>) {
        angles.firstOrNull()?.let {
            handler.postDelayed(
                {
                    movement.bead.xCenter = beadCenterCoordinatesHelper.getXCenter(it)
                    movement.bead.yCenter = beadCenterCoordinatesHelper.getYCenter(it)
                    angles.remove(it)
                    animateByAngle(movement, angles)
                    listener.invalidateView()
                }, ANIMATION_UPDATE_INTERVAL.toLong()
            )
        }
    }

    interface AnimationListener {
        fun invalidateView()
    }

    fun clear() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun getNextMovingBead(): Bead? =
        beadsManager.beadsList.find { it.isDone.not() }

    private fun getNextAvailablePointIndex(): Int =
        beadsManager.beadsList.findLast { it.isDone }?.pointIndex?.plus(1) ?: 1
}