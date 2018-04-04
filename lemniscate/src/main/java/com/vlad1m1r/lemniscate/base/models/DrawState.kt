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

import android.graphics.Path
import com.vlad1m1r.lemniscate.base.settings.CurveSettings

import com.vlad1m1r.lemniscate.utils.CurveUtils

private const val STEP_SIZE = 0.001f
class DrawState(val path:Path) {

    internal var isExpanding = true
    var currentLineLength = 0.0f
        internal set

    internal fun addPairOfPointsToPath(start: Point?, end: Point?) {
        if (start != null && end != null) {
            path.moveTo(start.x, start.y)
            path.quadTo(start.x, start.y, end.x, end.y)
        } else if (start != null) {
            path.moveTo(start.x, start.y)
            path.lineTo(start.x, start.y)
        } else if (end != null) {
            path.moveTo(end.x, end.y)
        } 
    }

    internal fun isInRightDirectionToBeInHole(start: Point?, end: Point?)
            = start != null && end != null && start.x > end.x

    fun addPointsToPath(listOfPoints: List<Point>, curveSettings: CurveSettings, viewSize: ViewSize) {
        resetPath()

        val holeSize = curveSettings.strokeWidth

        //adds points to path and creates hole if curveSettings.hasHole()
        for (i in listOfPoints.indices) {
            var start: Point? = listOfPoints[i]
            var end: Point? = null

            if (listOfPoints.size > i + 1)
                end = listOfPoints[i + 1]

            if (curveSettings.hasHole) {
                if (isInRightDirectionToBeInHole(start, end)) {
                    start = CurveUtils.checkPointForHole(start, holeSize, viewSize.size)
                    end = CurveUtils.checkPointForHole(end, holeSize, viewSize.size)
                }
            }

            addPairOfPointsToPath(start, end)
        }
    }

    internal fun resetPath() {
        path.reset()
    }

    internal fun keepLineLengthInsideLimits(lineLength: LineLength){
        if (currentLineLength < lineLength.lineMinLength) {
            currentLineLength = lineLength.lineMinLength
        }
        if (currentLineLength > lineLength.lineMaxLength) {
            currentLineLength = lineLength.lineMaxLength
        }
    }

    internal fun calculateNewCurrentLineLength(lineLength: LineLength) {
        if (currentLineLength < lineLength.lineMaxLength && isExpanding) {
            currentLineLength += STEP_SIZE
        } else if (currentLineLength > lineLength.lineMinLength && !isExpanding) {
            currentLineLength -= STEP_SIZE
        } else if (currentLineLength == lineLength.lineMaxLength) {
            isExpanding = false
        } else if (currentLineLength == lineLength.lineMinLength) {
            isExpanding = true
        } else {
            throw IllegalArgumentException("currentLineLength is not inside limits")
        }
    }

    fun recalculateLineLength(lineLength: LineLength) {
        if (lineLength.lineMinLength < lineLength.lineMaxLength) {
            keepLineLengthInsideLimits(lineLength)
            calculateNewCurrentLineLength(lineLength)
        } else {
            currentLineLength = lineLength.lineMaxLength
        }
    }
}
