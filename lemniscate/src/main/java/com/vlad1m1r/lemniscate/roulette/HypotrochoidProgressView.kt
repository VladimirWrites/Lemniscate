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
import android.util.AttributeSet
import kotlin.math.cos
import kotlin.math.sin

class HypotrochoidProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseRouletteProgressView(context, attrs, defStyleAttr) {

    internal val radiusDiff: Float
        get() = radiusFixed - radiusMoving

    internal val sizeFactor: Float
        get() = 2 * (radiusDiff + distanceFromCenter)

    // radiusFixed = 5, radiusMoving=3, distanceFromCenter=5, numberOfCycles = 3 to get pentagram

    override fun getGraphX(t: Float): Float =
            size / sizeFactor * (radiusDiff * cos(t) - distanceFromCenter * cos(radiusDiff / radiusMoving * t))

    override fun getGraphY(t: Float): Float =
            size / sizeFactor * (radiusDiff * sin(t) + distanceFromCenter * sin(radiusDiff / radiusMoving * t))

}
