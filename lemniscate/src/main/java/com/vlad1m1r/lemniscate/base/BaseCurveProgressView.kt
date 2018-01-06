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

abstract class BaseCurveProgressView : View, IBaseCurveProgressView{

    protected var curveSettings: CurveSettings = CurveSettings()
    protected var viewSize = ViewSize()
    private var animationSettings = AnimationSettings()
    private var drawState = DrawState()
    private var points = Points()

    private var valueAnimator: ValueAnimator? = null
    private val interpolator = LinearInterpolator()

    private val lineLengthToDraw: Int
        get() = Math.round(curveSettings.precision * drawState.currentLineLength)

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
     * This method should return values of y for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for y.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy y∈[-viewSize.getHeight()/2, viewSize.getHeight()/2].
     */
    abstract fun getGraphY(t: Double): Float

    /**
     * This method should return values of x for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for x.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy x∈[-viewSize.getWidth()/2, viewSize.getWidth()/2].
     */
    abstract fun getGraphX(t: Double): Float

    /**
     * @param i ∈ [0, mPrecision)
     * @return function is putting i∈[0, curveSettings.getPrecision()) points between [0, 2π]
     */
    protected open fun getT(i: Int): Double {
        return i.toDouble() * 2.0 * Math.PI / curveSettings.precision
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
        var remainingPoints = remainingPoints
        for (i in start until curveSettings.precision) {

            points.addPoint(getPoint(i))

            if (--remainingPoints == 0) {
                return remainingPoints
            }
        }
        return remainingPoints
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

        setMeasuredDimension(Math.round(this.viewSize.size + xPadding), Math.round(this.viewSize.size + yPadding))
    }

    private fun getMaxViewSquareSize(height: Int, width: Int, xPadding: Int, yPadding: Int): Int {
        return Math.min(height - yPadding, width - xPadding)
    }

    private fun getViewDimension(mode: Int, viewSize: Float, defaultSize: Float): Float {
        return if (mode == View.MeasureSpec.EXACTLY) {
            viewSize
        } else if (mode == View.MeasureSpec.AT_MOST) {
            Math.min(defaultSize, viewSize)
        } else {
            defaultSize
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

    override fun getStrokeWidth() = curveSettings.strokeWidth

    override fun setStrokeWidth(strokeWidth: Float) {
        curveSettings.strokeWidth = strokeWidth
    }

    override fun getLineMaxLength() =  curveSettings.lineLength.lineMaxLength

    override fun setLineMaxLength(lineMaxLength: Float) {
        curveSettings.lineLength.lineMaxLength = lineMaxLength
    }

    override fun getLineMinLength() =  curveSettings.lineLength.lineMinLength

    override fun setLineMinLength(lineMinLength: Float) {
        curveSettings.lineLength.lineMinLength = lineMinLength
    }

    override fun getColor() =  curveSettings.color

    override fun setColor(color: Int) {
        curveSettings.color = color
    }

    override fun getDuration() = animationSettings.duration

    override fun setDuration(duration: Int) {
        animationSettings.duration = duration
        if (valueAnimator != null) valueAnimator!!.duration = duration.toLong()
    }

    override fun getPrecision() = curveSettings.precision

    override fun setPrecision(precision: Int) {
        curveSettings.precision = precision
        animateLemniscate()
        invalidate()
    }

    override fun getSizeMultiplier() = viewSize.sizeMultiplier

    override fun setSizeMultiplier(multiplier: Float) {
        viewSize.sizeMultiplier = multiplier
        requestLayout()
        invalidate()
    }

    override fun hasHole() = curveSettings.hasHole

    override fun setHasHole(hasHole: Boolean) {
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