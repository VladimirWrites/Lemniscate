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
import com.vlad1m1r.lemniscate.base.models.Points
import com.vlad1m1r.lemniscate.base.models.ViewSize
import com.vlad1m1r.lemniscate.base.settings.AnimationSettings
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import com.vlad1m1r.lemniscate.sample.lemniscate.R
import kotlin.math.min
import kotlin.math.round

abstract class BaseCurveProgressView : View, IBaseCurveView {

    protected var presenter: IBaseCurvePresenter = BaseCurvePresenter(
            this,
            CurveSettings(),
            ViewSize(), AnimationSettings(),
            DrawState(Path()),
            Points())

    private var valueAnimator: ValueAnimator? = null
    private val interpolator = LinearInterpolator()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val curveAttributes = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BaseCurveProgressView,
                0, 0)

        val colorAccentAttributes = context.obtainStyledAttributes(attrs, intArrayOf(R.attr.colorAccent))

        try {
            val colorAccent = colorAccentAttributes.getColor(0, 0)

            presenter.curveSettings.lineLength.lineMaxLength = curveAttributes.getFloat(R.styleable.BaseCurveProgressView_maxLineLength, 0.8f)
            presenter.curveSettings.lineLength.lineMinLength = curveAttributes.getFloat(R.styleable.BaseCurveProgressView_minLineLength, 0.4f)

            presenter.curveSettings.color = curveAttributes.getColor(R.styleable.BaseCurveProgressView_lineColor, colorAccent)
            presenter.curveSettings.hasHole = curveAttributes.getBoolean(R.styleable.BaseCurveProgressView_hasHole, false)
            presenter.curveSettings.strokeWidth = curveAttributes.getDimension(R.styleable.BaseCurveProgressView_strokeWidth, resources.getDimension(R.dimen.lemniscate_stroke_width))
            presenter.curveSettings.precision = curveAttributes.getInteger(R.styleable.BaseCurveProgressView_precision, 200)

            presenter.animationSettings.duration = curveAttributes.getInteger(R.styleable.BaseCurveProgressView_duration, 1000)
        } finally {
            curveAttributes.recycle()
            colorAccentAttributes.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        presenter.recreatePoints()
        canvas.drawPath(presenter.drawState.path, presenter.curveSettings.paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val defaultSize = resources.getDimension(R.dimen.lemniscate_preferred_height) * presenter.viewSize.sizeMultiplier

        val xPadding = paddingLeft + paddingRight
        val yPadding = paddingTop + paddingBottom

        val viewSize = getMaxViewSquareSize(
                measuredHeight,
                measuredWidth,
                xPadding,
                yPadding
        )

        presenter.viewSize.size = getViewDimension(
                View.MeasureSpec.getMode(widthMeasureSpec),
                viewSize.toFloat(),
                defaultSize
        )

        setMeasuredDimension(round(presenter.viewSize.size + xPadding).toInt(), round(presenter.viewSize.size + yPadding).toInt())
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
        valueAnimator = ValueAnimator.ofInt(presenter.curveSettings.precision - 1, 0)
        valueAnimator!!.duration = presenter.animationSettings.duration.toLong()
        valueAnimator!!.repeatCount = -1
        valueAnimator!!.repeatMode = ValueAnimator.RESTART
        valueAnimator!!.interpolator = interpolator
        valueAnimator!!.addUpdateListener { animation ->
            presenter.updateStartingPointOnCurve(animation.animatedValue as Int)
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
        ss.curveSettings = this.presenter.curveSettings
        ss.animationSettings = this.presenter.animationSettings
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is BaseCurveSavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        this.presenter.curveSettings = state.curveSettings
        this.presenter.animationSettings = state.animationSettings
    }

    var strokeWidth
        get() = presenter.curveSettings.strokeWidth
        set(strokeWidth) {
            presenter.curveSettings.strokeWidth = strokeWidth
        }

    var lineMaxLength
        get() = presenter.curveSettings.lineLength.lineMaxLength
        set(lineMaxLength) {
            presenter.curveSettings.lineLength.lineMaxLength = lineMaxLength
        }

    var lineMinLength
        get() = presenter.curveSettings.lineLength.lineMinLength
        set(lineMinLength) {
            presenter.curveSettings.lineLength.lineMaxLength = lineMinLength
        }

    var color
        get() = presenter.curveSettings.color
        set(color) {
            presenter.curveSettings.color = color
        }

    var duration
        get() = presenter.animationSettings.duration
        set(duration) {
            presenter.animationSettings.duration = duration
            if (valueAnimator != null) valueAnimator!!.duration = duration.toLong()
        }

    var precision
        get() = presenter.curveSettings.precision
        set(precision) {
            presenter.curveSettings.precision = precision
            animateLemniscate()
            invalidate()
        }

    var sizeMultiplier
        get() = presenter.viewSize.sizeMultiplier
        set(sizeMultiplier) {
            presenter.viewSize.sizeMultiplier = sizeMultiplier
            requestLayout()
            invalidate()
        }

    var size = presenter.viewSize.size
        get() = presenter.viewSize.size
        private set

    open var hasHole
        get() = presenter.curveSettings.hasHole
        set(hasHole) {
            presenter.curveSettings.hasHole = hasHole
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

    override fun invalidateView() {
        invalidate()
    }

    override fun requestViewLayout() {
        requestLayout()
    }
}