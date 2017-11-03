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
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.roulette.models.RouletteCurveSettings;
import com.vlad1m1r.lemniscate.sample.lemniscate.R;

public abstract class BaseRouletteProgressView extends BaseCurveProgressView<RouletteCurveSettings> {

    public BaseRouletteProgressView(Context context) {
        super(context);
    }

    public BaseRouletteProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray rouletteCurveAttributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RouletteCurveProgressView,
                0, 0);

        try {
            setRadiusFixed(rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_radiusFixed, curveSettings.getRadiusFixed()));
            setRadiusMoving(rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_radiusMoving, curveSettings.getRadiusMoving()));
            setDistanceFromCenter(rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_distanceFromCenter, curveSettings.getDistanceFromCenter()));
            setNumberOfCycles(rouletteCurveAttributes.getFloat(R.styleable.RouletteCurveProgressView_numberOfCycles, curveSettings.getNumberOfCycles()));
        } finally {
            rouletteCurveAttributes.recycle();
        }
    }

    public BaseRouletteProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getRadiusFixed() {
        return curveSettings.getRadiusFixed();
    }

    public void setRadiusFixed(float radiusFixed) {
        curveSettings.setRadiusFixed(radiusFixed);
        recalculateConstants();
    }

    public float getRadiusMoving() {
        return curveSettings.getRadiusMoving();
    }

    public void setRadiusMoving(float radiusMoving) {
        curveSettings.setRadiusMoving(radiusMoving);
        recalculateConstants();
    }

    public float getDistanceFromCenter() {
        return curveSettings.getDistanceFromCenter();
    }

    public void setDistanceFromCenter(float distanceFromCenter) {
        curveSettings.setDistanceFromCenter(distanceFromCenter);
        recalculateConstants();
    }

    public float getNumberOfCycles() {
        return curveSettings.getNumberOfCycles();
    }

    public void setNumberOfCycles(float numberOfCycles) {
        curveSettings.setNumberOfCycles(numberOfCycles);
    }

    protected void recalculateConstants() {}

    @Override
    protected RouletteCurveSettings getCurveSettings() {
        return new RouletteCurveSettings();
    }

    @Override
    public void setHasHole(boolean hasHole) {
        super.setHasHole(false);
    }

    @Override
    public double getT(int i) {
        return i * curveSettings.getNumberOfCycles() * 2 * Math.PI / curveSettings.getPrecision();
    }
}
