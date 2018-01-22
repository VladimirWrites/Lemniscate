/*
 * Copyright 2016 Vladimir Jovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vlad1m1r.lemniscate.base

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.vlad1m1r.lemniscate.base.models.DrawState
import com.vlad1m1r.lemniscate.base.models.Point
import com.vlad1m1r.lemniscate.base.models.Points
import com.vlad1m1r.lemniscate.base.models.ViewSize
import com.vlad1m1r.lemniscate.base.settings.AnimationSettings
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import com.vlad1m1r.lemniscate.sample.lemniscate.R
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.round

abstract class BaseCurveProgressView : View {

    protected var curveSettings: CurveSettings = CurveSettings()
    private var viewSize = ViewSize()
    private var animationSettings = AnimationSettings()
    private var drawState = DrawState(Path())
    private var points = Points()

    private var valueAnimator: ValueAnimator? = null
    private val interpolator = LinearInterpolator()

    private val lineLengthToDraw: Int
        get() = round(curveSettings.precision * drawState.currentLineLength).toInt()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val curveAttributes = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BaseCurveProgressView,
                0, 0)

        val colorAccentAttributes = context.obtainStyledAttributes(attrs, intArrayOf(R.attr.colorAccent))

        try {
            val colorAccent = colorAccentAttributes.getColor(0, 0)

            curveSettings.lineLength.lineMaxLength = curveAttributes.getFloat(R.styleable.BaseCurveProgressView_maxLineLength, 0.8f)
            curveSettings.lineLength.lineMinLength = curveAttributes.getFloat(R.styleable.BaseCurveProgressView_minLineLength, 0.4f)

            curveSettings.color = curveAttributes.getColor(R.styleable.BaseCurveProgressView_lineColor, colorAccent)
            curveSettings.hasHole = curveAttributes.getBoolean(R.styleable.BaseCurveProgressView_hasHole, false)
            curveSettings.strokeWidth = curveAttributes.getDimension(R.styleable.BaseCurveProgressView_strokeWidth, resources.getDimension(R.dimen.lemniscate_stroke_width))
            curveSettings.precision = curveAttributes.getInteger(R.styleable.BaseCurveProgressView_precision, 200)

            animationSettings.duration = curveAttributes.getInteger(R.styleable.BaseCurveProgressView_duration, 1000)

            viewSize.sizeMultiplier = curveAttributes.getFloat(R.styleable.BaseCurveProgressView_sizeMultiplier, 1f)
        } finally {
            curveAttributes.recycle()
            colorAccentAttributes.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * This method should return values of x for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for x.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy x∈[-viewSize.getWidth()/2, viewSize.getWidth()/2].
     */
    abstract fun getGraphX(t: Float): Float

    /**
     * This method should return values of y for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for y.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy y∈[-viewSize.getHeight()/2, viewSize.getHeight()/2].
     */
    abstract fun getGraphY(t: Float): Float

    /**
     * @param i ∈ [0, mPrecision)
     * @return function is putting i∈[0, curveSettings.getPrecision()) points between [0, 2π]
     */
    protected open fun getT(i: Int): Float {
        return i * 2f * PI.toFloat() / curveSettings.precision
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        recreatePoints()
        drawState.addPointsToPath(points.getPoints(), curveSettings, viewSize)
        canvas.drawPath(drawState.path, curveSettings.paint)
    }

    private fun recreatePoints() {
        points.clear()
        createNewPoints()
    }

    private fun createNewPoints() {
        var lineLengthToDraw = lineLengthToDraw

        // creates points from mStart till mLineLength points is created, or till mPrecision is reached in first pass
        // if there is more points to be created goes to second pass
        while (lineLengthToDraw > 0) {
            lineLengthToDraw = addPointsToCurve(
                    if (points.isEmpty) animationSettings.startingPointOnCurve else 0,
                    lineLengthToDraw
            )
        }
    }

    private fun addPointsToCurve(start: Int, remainingPoints: Int): Int {
        var remainingPointsTemp = remainingPoints
        for (i in start until curveSettings.precision) {

            points.addPoint(getPoint(i))

            if (--remainingPointsTemp == 0) {
                return remainingPointsTemp
            }
        }
        return remainingPointsTemp
    }

    private fun getPoint(i: Int): Point {
        return Point(
                getGraphX(getT(i)),
                getGraphY(getT(i)),
                curveSettings.strokeWidth,
                viewSize.size
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val defaultSize = resources.getDimension(R.dimen.lemniscate_preferred_height) * viewSize.sizeMultiplier

        val xPadding = paddingLeft + paddingRight
        val yPadding = paddingTop + paddingBottom

        val viewSize = getMaxViewSquareSize(
                measuredHeight,
                measuredWidth,
                xPadding,
                yPadding
        )

        this.viewSize.size = getViewDimension(
                View.MeasureSpec.getMode(widthMeasureSpec),
                viewSize.toFloat(),
                defaultSize
        )

        setMeasuredDimension(round(this.viewSize.size + xPadding).toInt(), round(this.viewSize.size + yPadding).toInt())
    }

    private fun getMaxViewSquareSize(height: Int, width: Int, xPadding: Int, yPadding: Int): Int {
        return min(height - yPadding, width - xPadding)
    }

    private fun getViewDimension(mode: Int, viewSize: Float, defaultSize: Float): Float {
        return when {
            viewSize == 0.0f -> defaultSize
            mode == View.MeasureSpec.EXACTLY -> viewSize
            mode == View.MeasureSpec.AT_MOST -> Math.min(defaultSize, viewSize)
            else -> defaultSize
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animateLemniscate()
    }

    private fun animateLemniscate() {
        if (valueAnimator != null) valueAnimator!!.end()
        valueAnimator = ValueAnimator.ofInt(curveSettings.precision - 1, 0)
        valueAnimator!!.duration = animationSettings.duration.toLong()
        valueAnimator!!.repeatCount = -1
        valueAnimator!!.repeatMode = ValueAnimator.RESTART
        valueAnimator!!.interpolator = interpolator
        valueAnimator!!.addUpdateListener { animation ->
            animationSettings.startingPointOnCurve = animation.animatedValue as Int
            drawState.recalculateLineLength(curveSettings.lineLength)
            invalidate()
        }
        valueAnimator!!.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        valueAnimator!!.end()
    }

    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = BaseCurveSavedState(superState)
        ss.curveSettings = curveSettings
        ss.animationSettings = animationSettings
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is BaseCurveSavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        this.curveSettings = state.curveSettings
        this.animationSettings = state.animationSettings
    }

    var strokeWidth
        get() = curveSettings.strokeWidth
        set(strokeWidth) {
            curveSettings.strokeWidth = strokeWidth
        }

    var lineMaxLength
        get() = curveSettings.lineLength.lineMaxLength
        set(lineMaxLength) {
            curveSettings.lineLength.lineMaxLength = lineMaxLength
        }

    var lineMinLength
        get() = curveSettings.lineLength.lineMinLength
        set(lineMinLength) {
            curveSettings.lineLength.lineMinLength = lineMinLength
        }

    var color
        get() = curveSettings.color
        set(color) {
            curveSettings.color = color
        }

    var duration
        get() = animationSettings.duration
        set(duration) {
            animationSettings.duration = duration
            if (valueAnimator != null) valueAnimator!!.duration = duration.toLong()
        }

    var precision
        get() = curveSettings.precision
        set(precision) {
            curveSettings.precision = precision
            animateLemniscate()
            invalidate()
        }

    var sizeMultiplier
        get() = viewSize.sizeMultiplier
        set(sizeMultiplier) {
            viewSize.sizeMultiplier = sizeMultiplier
            requestLayout()
            invalidate()
        }

    var size = viewSize.size
        get() = viewSize.size
        private set

    open var hasHole
        get() = curveSettings.hasHole
        set(hasHole) {
            curveSettings.hasHole = hasHole
        }

    protected class BaseCurveSavedState : View.BaseSavedState {
        internal lateinit var curveSettings: CurveSettings
        internal lateinit var animationSettings: AnimationSettings

        constructor(superState: Parcelable) : super(superState)

        constructor(`in`: Parcel) : super(`in`) {
            this.curveSettings = `in`.readParcelable(CurveSettings::class.java.classLoader)
            this.animationSettings = `in`.readParcelable(AnimationSettings::class.java.classLoader)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(this.curveSettings, flags)
            out.writeParcelable(this.animationSettings, flags)
        }


        val CREATOR: Parcelable.Creator<BaseCurveSavedState> = object : Parcelable.Creator<BaseCurveSavedState> {
            override fun createFromParcel(`in`: Parcel): BaseCurveSavedState {
                return BaseCurveSavedState(`in`)
            }

            override fun newArray(size: Int): Array<BaseCurveSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}