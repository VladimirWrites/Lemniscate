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

package com.vlad1m1r.lemniscate.funny

import android.content.Context
import android.util.AttributeSet

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView

class HeartProgressView : BaseCurveProgressView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getGraphY(t: Double): Float =
            (-viewSize.size / 34 * (13 * Math.cos(t)
                    - 5 * Math.cos(2 * t)
                    - 2 * Math.cos(3 * t)
                    - Math.cos(4 * t))).toFloat()

    override fun getGraphX(t: Double): Float =
            ((viewSize.size / 34).toDouble()
                    * 16.0 * Math.pow(Math.sin(t), 3.0)).toFloat()

}
