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
package com.vlad1m1r.lemniscate.base.models

class Point(x: Float, y: Float, strokeWidth: Float, viewSize: Float) {
    val x: Float
    val y: Float

    init {
        this.x = translateToPositiveCoordinates(x, strokeWidth, viewSize)
        this.y = translateToPositiveCoordinates(y, strokeWidth, viewSize)
    }

    private fun compensateForStrokeWidth(coordinate: Float, strokeWidth: Float, viewSize: Float): Float {
        val ratio = viewSize / (viewSize + 2 * strokeWidth)
        return coordinate * ratio + strokeWidth * ratio
    }

    private fun translateToPositiveCoordinates(coordinate: Float, strokeWidth: Float, viewSize: Float): Float {
        return compensateForStrokeWidth(coordinate + viewSize / 2, strokeWidth, viewSize)
    }
}
