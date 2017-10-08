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

package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.util.AttributeSet;

public class EpitrochoidProgressView extends BaseRouletteProgressView {

    public EpitrochoidProgressView(Context context) {
        super(context);
    }

    public EpitrochoidProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EpitrochoidProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private double radiusSum;
    private double sizeFactor;

    @Override
    public float getGraphY(double t) {
        //y = (mRadiusFixed + mRadiusMoving) sin(t) - mDistanceFromCenter sin(((mRadiusFixed+mRadiusMoving)/mRadiusMoving)*t)
        return (float) (viewSize.getSize() / sizeFactor
                        * (radiusSum * Math.sin(t) - curveSettings.getDistanceFromCenter() * Math.sin((radiusSum / curveSettings.getRadiusMoving()) * t)));
    }

    @Override
    public float getGraphX(double t) {
        //x = (mRadiusFixed + mRadiusMoving) cos(t) + mRadiusMoving cos(((mRadiusFixed+mRadiusMoving)/mRadiusMoving)*t),
        return (float) (viewSize.getSize() / sizeFactor
                        * (radiusSum * Math.cos(t) - curveSettings.getDistanceFromCenter() * Math.cos((radiusSum / curveSettings.getRadiusMoving()) * t)));
    }

    @Override
    protected void recalculateConstants() {
        radiusSum = curveSettings.getRadiusFixed() + curveSettings.getRadiusMoving();
        sizeFactor = 2 * (radiusSum + curveSettings.getDistanceFromCenter());
    }
}
