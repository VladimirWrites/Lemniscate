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

package com.vlad1m1r.lemniscate.roulette.scribble

import android.content.Context
import android.util.AttributeSet

import com.vlad1m1r.lemniscate.roulette.BaseRouletteProgressView

class ScribbleProgressView : BaseRouletteProgressView {

    private var radiusSum: Float = 0.toFloat()
    private var sizeFactor: Float = 0.toFloat()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getGraphY(t: Double): Float =
         (viewSize.size / sizeFactor * (radiusSum * Math.sin(t) - rouletteCurveSettings.distanceFromCenter * Math.cos(radiusSum / rouletteCurveSettings.radiusMoving * t))).toFloat()


    override fun getGraphX(t: Double): Float =
         (viewSize.size / sizeFactor * (radiusSum * Math.cos(t) - rouletteCurveSettings.distanceFromCenter * Math.cos(radiusSum / rouletteCurveSettings.radiusMoving * t))).toFloat()


    override fun setHasHole(hasHole: Boolean) {
        super.setHasHole(false)
    }

    override fun recalculateConstants() {
        radiusSum = rouletteCurveSettings.radiusFixed + rouletteCurveSettings.radiusMoving
        sizeFactor = 2 * (radiusSum + rouletteCurveSettings.distanceFromCenter)
    }
}
