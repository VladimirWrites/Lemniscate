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

public class Point {
    private float x;
    private float y;

    public Point(float x, float y, float strokeWidth, float viewSize) {
        this.x = translateToPositiveCoordinates(x, strokeWidth, viewSize);
        this.y = translateToPositiveCoordinates(y, strokeWidth, viewSize);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    private float compensateForStrokeWidth(float coordinate, float strokeWidth, float viewSize) {
        final float ratio = viewSize/(viewSize + 2 * strokeWidth);
        return coordinate * ratio + strokeWidth * ratio;
    }

    private float translateToPositiveCoordinates(float coordinate, float strokeWidth, float viewSize) {
        return compensateForStrokeWidth(coordinate + viewSize/2, strokeWidth, viewSize);
    }
}
