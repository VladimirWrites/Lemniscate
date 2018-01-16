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

package com.vlad1m1r.lemniscate.sample

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.vlad1m1r.lemniscate.BernoullisBowProgressView
import com.vlad1m1r.lemniscate.BernoullisProgressView
import com.vlad1m1r.lemniscate.BernoullisSharpProgressView
import com.vlad1m1r.lemniscate.GeronosProgressView
import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import com.vlad1m1r.lemniscate.funny.CannabisProgressView
import com.vlad1m1r.lemniscate.funny.HeartProgressView
import com.vlad1m1r.lemniscate.other.XProgressView
import com.vlad1m1r.lemniscate.roulette.EpitrochoidProgressView
import com.vlad1m1r.lemniscate.roulette.HypotrochoidProgressView
import com.vlad1m1r.lemniscate.roulette.scribble.RoundScribbleProgressView
import com.vlad1m1r.lemniscate.roulette.scribble.ScribbleProgressView

class FragmentCurve : Fragment() {

    private var listener: OnViewCreated? = null

    private var baseCurveProgressView: BaseCurveProgressView? = null

    private lateinit var curveName: TextView
    private lateinit var layoutViewHolder: LinearLayout

    private var position: Int = 0

    interface OnViewCreated {
        fun onViewShown(position: Int, baseCurveProgressView: BaseCurveProgressView?)
        fun onViewPrepared(position: Int, baseCurveProgressView: BaseCurveProgressView?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_POSITION))
            position = savedInstanceState.getInt(KEY_POSITION)

        if (baseCurveProgressView == null) {
            baseCurveProgressView = getViewForPosition(position)
            baseCurveProgressView!!.id = position
            baseCurveProgressView!!.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_curve, container, false) as ViewGroup

        curveName = root.findViewById(R.id.textCurveName)
        layoutViewHolder = root.findViewById(R.id.layoutViewHolder)

        if (baseCurveProgressView!!.parent != null) {
            (baseCurveProgressView!!.parent as ViewGroup).removeView(baseCurveProgressView)
        }
        layoutViewHolder.addView(baseCurveProgressView)

        curveName.text = baseCurveProgressView!!.javaClass.simpleName

        return root
    }

    private fun getViewForPosition(position: Int): BaseCurveProgressView {
        when (position) {
            0 -> return BernoullisProgressView(context!!)
            1 -> return GeronosProgressView(context!!)
            2 -> return BernoullisBowProgressView(context!!)
            3 -> return BernoullisSharpProgressView(context!!)

            4 -> return EpitrochoidProgressView(context!!)
            5 -> return HypotrochoidProgressView(context!!)

            6 -> return XProgressView(context!!)

            7 -> return RoundScribbleProgressView(context!!)
            8 -> return ScribbleProgressView(context!!)

            9 -> return CannabisProgressView(context!!)
            10 -> return HeartProgressView(context!!)
            else -> return BernoullisProgressView(context!!)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnViewCreated?
    }

    override fun onResume() {
        super.onResume()
        if (listener != null) listener!!.onViewPrepared(position, baseCurveProgressView)
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (listener != null) listener!!.onViewShown(position, baseCurveProgressView)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_POSITION, position)
        super.onSaveInstanceState(outState)
    }

    companion object {

        private val KEY_POSITION = "position"

        fun getInstance(position: Int): FragmentCurve {
            val fragmentCurve = FragmentCurve()
            fragmentCurve.position = position
            fragmentCurve.retainInstance = true
            return fragmentCurve
        }
    }
}
