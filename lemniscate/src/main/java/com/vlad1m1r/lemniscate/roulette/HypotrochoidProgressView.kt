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

class HypotrochoidProgressView : BaseRouletteProgressView {

    internal var radiusDiff = 0f
        get() = radiusFixed - radiusMoving
        private set

    internal var sizeFactor = 0f
        get() = 2 * (radiusDiff + distanceFromCenter)
        private set

    // radiusFixed = 5, radiusMoving=3, distanceFromCenter=5, numberOfCycles = 3 to get pentagram

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getGraphX(t: Float): Float =
            size / sizeFactor * (radiusDiff * cos(t) - distanceFromCenter * cos(radiusDiff / radiusMoving * t))

    override fun getGraphY(t: Float): Float =
            size / sizeFactor * (radiusDiff * sin(t) + distanceFromCenter * sin(radiusDiff / radiusMoving * t))

}
