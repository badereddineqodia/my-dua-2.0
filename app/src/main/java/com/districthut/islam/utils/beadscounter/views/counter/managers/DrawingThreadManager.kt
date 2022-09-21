package com.districthut.islam.utils.beadscounter.views.counter.managers

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.districthut.islam.utils.beadscounter.views.counter.dpToPx
import com.mirfatif.noorulhuda.R
import kotlin.math.min


private const val PADDING_HORIZONTAL = 16

class DrawingThreadManager(
    private val context: Context,
) {

    @ColorInt
    var threadColor: Int = Color.GREEN
    var threadThickness: Double = 2.0
    var mainXCenter = 0
    var mainYCenter = 0
    var radius = 0

    private var mainBeadBitmap =
        context.getBitmapFromVectorDrawable(R.drawable.ic_tasbeeh_top)
            ?: BitmapFactory.decodeResource(
                context.resources,
                R.drawable.tasbeeh_top
            )
    private val beadPaddingBottom = 0
    private val threadPaint: Paint by lazy {
        Paint().apply {
            color = threadColor
            strokeWidth = context.dpToPx(threadThickness)
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    fun setMeasurements(width: Int, height: Int) {
        assertMainBeadBitmap()
        val remainingHeight =
            height - mainBeadBitmap.height + beadPaddingBottom - context.dpToPx(BEAD_RADIUS_MAX)
        radius = min(remainingHeight, getMaxCounterViewWidth(width)).div(2)
        mainXCenter = width.div(2)
        val figureHeight =
            radius * 2 + mainBeadBitmap.height - beadPaddingBottom + context.dpToPx(BEAD_RADIUS_MAX)
        val verticalSpace = height - figureHeight
        mainYCenter =
            radius + mainBeadBitmap.height - beadPaddingBottom - context.dpToPx(BEAD_RADIUS_MAX) * 2 + verticalSpace.div(
                2
            )
    }

    private fun getMaxCounterViewWidth(width: Int) =
        width - 2 * context.dpToPx(BEAD_RADIUS_MAX + PADDING_HORIZONTAL)

    private fun assertMainBeadBitmap() {
        if (mainBeadBitmap == null || mainBeadBitmap.isRecycled)
            mainBeadBitmap =
                context.getBitmapFromVectorDrawable(R.drawable.ic_tasbeeh_top)
                    ?: BitmapFactory.decodeResource(context.resources, R.drawable.tasbeeh_top)
    }

    fun draw(canvas: Canvas) {
        drawThread(canvas)
        drawMainBead(canvas)
    }

    private fun drawThread(canvas: Canvas) {
        with(canvas) {
            drawCircle(mainXCenter.toFloat(), mainYCenter.toFloat(), radius.toFloat(), threadPaint)
        }
    }

    private fun drawMainBead(canvas: Canvas) {
        assertMainBeadBitmap()
        val rect = Rect(
            mainXCenter - mainBeadBitmap.width.div(2),
            mainYCenter - radius + beadPaddingBottom - mainBeadBitmap.height,
            mainXCenter + mainBeadBitmap.width.div(2),
            mainYCenter - radius + beadPaddingBottom
        )
        with(canvas) {
            drawBitmap(mainBeadBitmap, null, rect, null)
        }
    }
}

fun Context.getBitmapFromVectorDrawable(
    @DrawableRes drawableId: Int,
    width: Int? = null,
    height: Int? = null
): Bitmap? {
    return ContextCompat.getDrawable(this, drawableId)?.run {
        val w = width ?: intrinsicWidth
        val h = height ?: intrinsicHeight
        val bitmap = Bitmap.createBitmap(
            w, h, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        bitmap
    }
}