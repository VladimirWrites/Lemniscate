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

package com.vlad1m1r.lemniscate.roulette

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import com.vlad1m1r.lemniscate.roulette.settings.RouletteCurveSettings
import com.vlad1m1r.lemniscate.sample.lemniscate.R
import kotlin.math.PI

abstract class BaseRouletteProgressView : BaseCurveProgressView {

    protected var rouletteCurveSettings: RouletteCurveSettings = RouletteCurveSettings()

    var radiusFixed: Float
        get() = rouletteCurveSettings.radiusFixed
        set(radiusFixed) {
            rouletteCurveSettings.radiusFixed = radiusFixed
            recalculateConstants()
        }

    var radiusMoving: Float
        get() = rouletteCurveSettings.radiusMoving
        set(radiusMoving) {
            rouletteCurveSettings.radiusMoving = radiusMoving
            recalculateConstants()
        }

    var distanceFromCenter: Float
        get() = rouletteCurveSettings.distanceFromCenter
        set(distanceFromCenter) {
            rouletteCurveSettings.distanceFromCenter = distanceFromCenter
            recalculateConstants()
        }

    var numberOfCycles: Float
        get() = rouletteCurveSettings.numberOfCycles
        set(numberOfCycles) {
            rouletteCurveSettings.numberOfCycles = numberOfCycles
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val rouletteCurveAttributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.RouletteCurveProgressView,
                0, 0)

        try {
            radiusFixed = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_radiusFixed, rouletteCurveSettings.radiusFixed)
            radiusMoving = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_radiusMoving, rouletteCurveSettings.radiusMoving)
            distanceFromCenter = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_distanceFromCenter, rouletteCurveSettings.distanceFromCenter)
            numberOfCycles = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_numberOfCycles, rouletteCurveSettings.numberOfCycles)
        } finally {
            rouletteCurveAttributes.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    internal open fun recalculateConstants() {}

    override var hasHole: Boolean = false
        set(hasHole) {
            super.hasHole = false
        }


    override fun getT(i: Int, precision: Int): Float {
        return i * rouletteCurveSettings.numberOfCycles * 2 * PI.toFloat() / precision
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = RouletteCurveSavedState(superState)
        ss.rouletteCurveSettings = rouletteCurveSettings
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is RouletteCurveSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)

        this.rouletteCurveSettings = state.rouletteCurveSettings
    }

    protected class RouletteCurveSavedState : View.BaseSavedState {
        internal lateinit var rouletteCurveSettings: RouletteCurveSettings

        constructor(superState: Parcelable) : super(superState)

        constructor(`in`: Parcel) : super(`in`) {
            this.rouletteCurveSettings = `in`.readParcelable(RouletteCurveSettings::class.java.classLoader)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(this.rouletteCurveSettings, flags)
        }


        val CREATOR: Parcelable.Creator<RouletteCurveSavedState> = object : Parcelable.Creator<RouletteCurveSavedState> {
            override fun createFromParcel(`in`: Parcel): RouletteCurveSavedState {
                return RouletteCurveSavedState(`in`)
            }

            override fun newArray(size: Int): Array<RouletteCurveSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}
