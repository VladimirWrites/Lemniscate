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

package com.vlad1m1r.lemniscate.utils

import com.vlad1m1r.lemniscate.base.models.Point

object CurveUtils {

    /**
     * @param point is being checked if it's inside hole
     * @param holeSize size of hole
     * @param viewSize size of view
     * @return if point is in hole returns null, otherwise returns point
     */
    fun checkPointForHole(point: Point?, holeSize: Float, viewSize: Float): Point? {
        return if (point != null &&
                Math.abs(point.x - viewSize / 2) < holeSize &&
                Math.abs(point.y - viewSize / 2) < holeSize) {
            null
        } else point
    }
}
