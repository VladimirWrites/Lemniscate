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

package com.vlad1m1r.lemniscate.utils;

import android.graphics.Path;
import android.util.Pair;


public class CurveUtils {

    public static Path addPointsToPath(Pair<Float, Float> start, Pair<Float, Float> end, Path path) {

        if(start != null && end != null) {
            path.moveTo(start.first, start.second);
            path.quadTo(start.first, start.second, end.first, end.second);
        }
        else if (start != null) {
            path.moveTo(start.first, start.second);
            path.lineTo(start.first, start.second);
        } else if(end != null) {
            path.moveTo(end.first, end.second);
        }
        return path;
    }

    public static Pair<Float, Float> checkPointForHole(Pair<Float, Float> point, float holeSize, float viewHeight, float viewWidth) {
        if(point != null &&
                Math.abs(point.first - viewWidth / 2) < holeSize &&
                Math.abs(point.second - viewHeight / 2) < holeSize) {
            return null;
        }
        return point;
    }
}
