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

package com.vlad1m1r.lemniscate

import android.content.Context
import android.util.AttributeSet

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class BernoullisSharpProgressView : BaseCurveProgressView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getGraphX(t: Float): Float =
            size * cos(t) / (1 + cos(t).pow(2))

    override fun getGraphY(t: Float): Float =
            size * sin(t) * cos(t) / (1 + cos(t).pow(2))
}
