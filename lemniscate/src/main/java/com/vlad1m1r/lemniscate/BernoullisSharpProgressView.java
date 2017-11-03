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

public class BernoullisSharpProgressView extends BaseCurveProgressView {

    public BernoullisSharpProgressView(Context context) {
        super(context);
    }

    public BernoullisSharpProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BernoullisSharpProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public float getGraphY(double t){
        return (float) ((viewSize.getSize() * Math.sin(t) * Math.cos(t)) / (1 + Math.pow(Math.cos(t), 2)));
    }

    @Override
    public float getGraphX(double t){
        return (float) ((viewSize.getSize() * Math.cos(t)) / (1 + Math.pow(Math.cos(t), 2)));
    }
}
