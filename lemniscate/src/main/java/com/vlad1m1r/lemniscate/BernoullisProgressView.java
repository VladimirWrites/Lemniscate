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

package com.vlad1m1r.lemniscate;

import android.content.Context;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;



public class BernoullisProgressView extends BaseCurveProgressView {

    public BernoullisProgressView(Context context) {
        super(context);
    }

    public BernoullisProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BernoullisProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public double getGraphY(double t){
        return (mLemniscateParamY * Math.sin(t) * Math.cos(t)) / (1 + Math.pow(Math.sin(t), 2));
    }

    public double getGraphX(double t){
        // trigonometric function for value of x for t∈[0, 2π)
        return (mLemniscateParamX * Math.cos(t)) / (1 + Math.pow(Math.sin(t), 2));
    }
}
