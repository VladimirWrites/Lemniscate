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
import androidx.core.os.ParcelCompat
import com.vlad1m1r.lemniscate.R
import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import com.vlad1m1r.lemniscate.roulette.settings.RouletteCurveSettings
import kotlin.math.PI

abstract class BaseRouletteProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseCurveProgressView(context, attrs, defStyleAttr) {

    protected var rouletteCurveSettings: RouletteCurveSettings = RouletteCurveSettings()

    var radiusFixed: Float
        get() = rouletteCurveSettings.radiusFixed
        set(radiusFixed) {
            rouletteCurveSettings.radiusFixed = radiusFixed
        }

    var radiusMoving: Float
        get() = rouletteCurveSettings.radiusMoving
        set(radiusMoving) {
            rouletteCurveSettings.radiusMoving = radiusMoving
        }

    var distanceFromCenter: Float
        get() = rouletteCurveSettings.distanceFromCenter
        set(distanceFromCenter) {
            rouletteCurveSettings.distanceFromCenter = distanceFromCenter
        }

    var numberOfCycles: Float
        get() = rouletteCurveSettings.numberOfCycles
        set(numberOfCycles) {
            rouletteCurveSettings.numberOfCycles = numberOfCycles
        }

    init {
        val rouletteCurveAttributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.RouletteCurveProgressView,
                defStyleAttr, 0)

        try {
            radiusFixed = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_radiusFixed, rouletteCurveSettings.radiusFixed)
            radiusMoving = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_radiusMoving, rouletteCurveSettings.radiusMoving)
            distanceFromCenter = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_distanceFromCenter, rouletteCurveSettings.distanceFromCenter)
            numberOfCycles = rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_numberOfCycles, rouletteCurveSettings.numberOfCycles)
        } finally {
            rouletteCurveAttributes.recycle()
        }
    }

    // The hole cut-out breaks this curve, so it stays disabled.
    override val supportsHole: Boolean
        get() = false


    override fun getT(i: Int, precision: Int): Float {
        return i * rouletteCurveSettings.numberOfCycles * 2 * PI.toFloat() / precision
    }

    override fun onSaveInstanceState(): Parcelable {
        val ss = RouletteCurveSavedState(super.onSaveInstanceState())
        ss.rouletteCurveSettings = rouletteCurveSettings
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is RouletteCurveSavedState) {
            super.onRestoreInstanceState(state.superState!!)
            state.rouletteCurveSettings?.let {
                this.rouletteCurveSettings = it
            }
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    protected open class RouletteCurveSavedState : BaseCurveSavedState {

        internal var rouletteCurveSettings: RouletteCurveSettings? = null

        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel) : super(source) {
            this.rouletteCurveSettings = ParcelCompat.readParcelable(source, RouletteCurveSettings::class.java.classLoader, RouletteCurveSettings::class.java)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(rouletteCurveSettings, flags)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<RouletteCurveSavedState> {
                override fun createFromParcel(source: Parcel): RouletteCurveSavedState {
                    return RouletteCurveSavedState(source)
                }

                override fun newArray(size: Int): Array<RouletteCurveSavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
