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

interface IBaseCurveProgressView {
    fun getStrokeWidth(): Float
    fun setStrokeWidth(strokeWidth: Float)

    fun getLineMaxLength(): Float
    fun setLineMaxLength(lineMaxLength: Float)

    fun getLineMinLength(): Float
    fun setLineMinLength(lineMinLength: Float)

    fun getColor(): Int
    fun setColor(color: Int)

    fun getDuration(): Int
    fun setDuration(duration: Int)

    fun getPrecision(): Int
    fun setPrecision(precision: Int)

    fun getSizeMultiplier(): Float
    fun setSizeMultiplier(multiplier: Float)

    fun hasHole(): Boolean
    fun setHasHole(hasHole: Boolean)
}