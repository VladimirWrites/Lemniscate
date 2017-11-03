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
package com.vlad1m1r.lemniscate.base.models;

import android.graphics.Path;

import com.vlad1m1r.lemniscate.utils.CurveUtils;

import java.util.List;

public class DrawState {

    public static float STEP_SIZE = 0.001f;
    private final Path path = new Path();

    private boolean isExpanding = true;
    private float currentLineLength;

    public float getCurrentLineLength() {
        return currentLineLength;
    }

    public Path getPath() {
        return path;
    }


    private void addPairOfPointsToPath(Point start, Point end) {
        if (start != null && end != null) {
            path.moveTo(start.x(), start.y());
            path.quadTo(start.x(), start.y(), end.x(), end.y());
        } else if (start != null) {
            path.moveTo(start.x(), start.y());
            path.lineTo(start.x(), start.y());
        } else if (end != null) {
            path.moveTo(end.x(), end.y());
        }
    }

    public void addPointsToPath(List<Point> listOfPoints, CurveSettings curveSettings, ViewSize viewSize) {
        resetPath();

        float holeSize = curveSettings.getStrokeWidth(); //Math.max(mStrokeWidth, 10);

        //adds points to path and creates hole if mHasHole
        for (int i = 0; i < listOfPoints.size(); i++) {
            Point start = listOfPoints.get(i);
            Point end = null;


            if(listOfPoints.size() > i + 1)
                end = listOfPoints.get(i + 1);

            if(curveSettings.hasHole()) {
                if(start!= null && end != null && start.x() > end.x()) {
                    start = CurveUtils.checkPointForHole(start, holeSize, viewSize.getSize());
                    end = CurveUtils.checkPointForHole(end, holeSize, viewSize.getSize());
                }
            }

            addPairOfPointsToPath(start, end);
        }
    }

    private void resetPath() {
        path.reset();
    }

    public void recalculateLineLength(LineLength lineLength) {
        if (lineLength.getLineMinLength() < lineLength.getLineMaxLength()) {
            if (currentLineLength < lineLength.getLineMinLength()) {
                currentLineLength = lineLength.getLineMinLength();
            }
            if (currentLineLength > lineLength.getLineMaxLength()) {
                currentLineLength = lineLength.getLineMaxLength();
            }

            if (currentLineLength < lineLength.getLineMaxLength() && isExpanding) {
                currentLineLength += STEP_SIZE;
            } else if (currentLineLength > lineLength.getLineMinLength() && !isExpanding) {
                currentLineLength -= STEP_SIZE;
            } else if (currentLineLength >= lineLength.getLineMaxLength()) {
                isExpanding = false;
            } else if (currentLineLength <= lineLength.getLineMinLength()) {
                isExpanding = true;
            }
        } else {
            currentLineLength = lineLength.getLineMaxLength();
        }
    }
}
