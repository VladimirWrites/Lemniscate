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

package com.vlad1m1r.lemniscate.scribble;

import android.content.Context;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.roulette.BaseRouletteProgressView;

public class ScribbleProgressView extends BaseRouletteProgressView {

    // mRadiusFixed = 5, mRadiusMoving=3, mDistanceFromCenter=5, mNumberOfCycles = 3 to get pentagram

    public ScribbleProgressView(Context context) {
        super(context);
    }

    public ScribbleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScribbleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(double t) {
        return mLemniscateParamY/((mRadiusFixed + mDistanceFromCenter + mRadiusMoving)) * ((mRadiusFixed + mRadiusMoving)*Math.sin(t) - mDistanceFromCenter *Math.cos(((mRadiusFixed + mRadiusMoving)/ mRadiusMoving)*t));
    }

    @Override
    public double getGraphX(double t) {
        return mLemniscateParamY/((mRadiusFixed + mDistanceFromCenter + mRadiusMoving))*((mRadiusFixed + mRadiusMoving)*Math.cos(t) - mDistanceFromCenter *Math.cos(((mRadiusFixed + mRadiusMoving)/ mRadiusMoving)*t));
    }

    @Override
    public void setHasHole(boolean hasHole) {
        super.setHasHole(false);
    }
}
