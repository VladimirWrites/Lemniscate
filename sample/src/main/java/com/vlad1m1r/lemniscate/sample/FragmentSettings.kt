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

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar

import com.vlad1m1r.lemniscate.BernoullisBowProgressView
import com.vlad1m1r.lemniscate.BernoullisProgressView
import com.vlad1m1r.lemniscate.BernoullisSharpProgressView
import com.vlad1m1r.lemniscate.GeronosProgressView
import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import com.vlad1m1r.lemniscate.roulette.BaseRouletteProgressView
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlin.math.round

class FragmentSettings : Fragment(), SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private lateinit var curveData: CurveData
    private var baseCurveProgressView: BaseCurveProgressView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        curveData = if(savedInstanceState != null && savedInstanceState.containsKey("curve_data")) {
            savedInstanceState.getParcelable("curve_data")
        } else {
            CurveData(color = ContextCompat.getColor(context!!, R.color.picker_color_1))
        }

        setupViews()
    }

    private fun setupViews() {
        seekBarStrokeWidth.max = 50
        seekBarStrokeWidth.progress = curveData.strokeWidth.toInt()
        seekBarStrokeWidth.setOnSeekBarChangeListener(this)

        seekBarMaxLineLength!!.max = 99
        seekBarMaxLineLength!!.progress = round(100 * curveData.lineMaxLength).toInt() - 1
        seekBarMaxLineLength!!.setOnSeekBarChangeListener(this)

        seekBarSizeMultiplier!!.max = 15
        seekBarSizeMultiplier!!.progress = 5
        seekBarSizeMultiplier!!.setOnSeekBarChangeListener(this)

        seekBarMinLineLength!!.max = 99
        seekBarMinLineLength!!.progress = round(100 * curveData.lineMinLength).toInt() - 1
        seekBarMinLineLength!!.setOnSeekBarChangeListener(this)

        seekBarAnimationDuration!!.max = 199
        seekBarAnimationDuration!!.progress = curveData.duration / 10 - 1
        seekBarAnimationDuration!!.setOnSeekBarChangeListener(this)

        checkBoxHasHole!!.setOnCheckedChangeListener(this)

        checkBoxHasHole!!.isChecked = curveData.hasHole

        seekBarPrecision!!.max = 990
        seekBarPrecision!!.progress = curveData.precision
        seekBarPrecision!!.setOnSeekBarChangeListener(this)

        seekBarA!!.max = 10
        seekBarA!!.progress = (curveData.radiusFixed - 1).toInt()
        seekBarA!!.setOnSeekBarChangeListener(this)

        seekBarB!!.max = 10
        seekBarB!!.progress = (curveData.radiusMoving - 1).toInt()
        seekBarB!!.setOnSeekBarChangeListener(this)

        seekBarD!!.max = 10
        seekBarD!!.progress = (curveData.distanceFromCenter - 1).toInt()
        seekBarD!!.setOnSeekBarChangeListener(this)

        seekBarNumberOfCycles!!.max = 5
        seekBarNumberOfCycles!!.progress = curveData.numberOfCycles - 1
        seekBarNumberOfCycles!!.setOnSeekBarChangeListener(this)

        viewColor1!!.setOnClickListener(this)
        viewColor2!!.setOnClickListener(this)
        viewColor3!!.setOnClickListener(this)
        viewColor4!!.setOnClickListener(this)
        viewColor5!!.setOnClickListener(this)
        viewColor6!!.setOnClickListener(this)
    }

    fun setBaseCurveProgressView(baseCurveProgressView: BaseCurveProgressView) {
        this.baseCurveProgressView = baseCurveProgressView

        //Checkbox
        checkBoxHasHole!!.isEnabled = this.baseCurveProgressView is BernoullisProgressView ||
                this.baseCurveProgressView is GeronosProgressView ||
                this.baseCurveProgressView is BernoullisBowProgressView ||
                this.baseCurveProgressView is BernoullisSharpProgressView

        //Roulette params
        if (this.baseCurveProgressView is BaseRouletteProgressView) {
            seekBarA!!.isEnabled = true
            seekBarB!!.isEnabled = true
            seekBarD!!.isEnabled = true
            seekBarNumberOfCycles!!.isEnabled = true
        } else {
            seekBarA!!.isEnabled = false
            seekBarB!!.isEnabled = false
            seekBarD!!.isEnabled = false
            seekBarNumberOfCycles!!.isEnabled = false
        }

        invalidateView(this.baseCurveProgressView)
        updateValues()
    }

    override fun onProgressChanged(seekBar: SeekBar, i: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.seekBarStrokeWidth -> curveData.strokeWidth = resources.dpToPx(i / 3.0f)
            R.id.seekBarMaxLineLength -> if (i < seekBarMinLineLength!!.progress) {
                seekBarMaxLineLength!!.progress = seekBarMinLineLength!!.progress
            } else
                curveData.lineMaxLength = (i + 1) / 100.0f
            R.id.seekBarMinLineLength -> if (i > seekBarMaxLineLength!!.progress) {
                seekBarMinLineLength!!.progress = seekBarMaxLineLength!!.progress
            } else
                curveData.lineMinLength = (i + 1) / 100.0f
            R.id.seekBarSizeMultiplier -> curveData.sizeMultiplier = (i + 5) / 10.0f
            R.id.seekBarAnimationDuration -> curveData.duration = (i + 1) * 10
            R.id.seekBarPrecision -> curveData.precision = i + 10
            R.id.seekBarA -> curveData.radiusFixed = (i + 1).toFloat()
            R.id.seekBarB -> curveData.radiusMoving = (i + 1).toFloat()
            R.id.seekBarD -> curveData.distanceFromCenter = (i + 1).toFloat()
            R.id.seekBarNumberOfCycles -> curveData.numberOfCycles = i + 1
        }
        invalidateView(baseCurveProgressView)
        updateValues()
    }

    private fun updateValues() {
        textStrokeWidth!!.text = curveData.strokeWidth.toString()
        textMaxLineLength!!.text = String.format(resources.getString(R.string.format_percentage), (curveData.lineMaxLength * 100).toInt())
        textMinLineLength!!.text = String.format(resources.getString(R.string.format_percentage), (curveData.lineMinLength * 100).toInt())
        textSizeMultiplier!!.text = curveData.sizeMultiplier.toString()
        textAnimationDuration!!.text = String.format(resources.getString(R.string.format_ms), curveData.duration)
        textPrecision!!.text = String.format(resources.getString(R.string.format_points), curveData.precision)
    }

    private fun invalidateView(baseCurveProgressView: BaseCurveProgressView?) {
        if (baseCurveProgressView != null) {
            baseCurveProgressView.precision = curveData.precision
            baseCurveProgressView.strokeWidth = curveData.strokeWidth
            baseCurveProgressView.lineMaxLength = curveData.lineMaxLength
            baseCurveProgressView.lineMinLength = curveData.lineMinLength
            baseCurveProgressView.duration = curveData.duration
            baseCurveProgressView.hasHole = curveData.hasHole
            baseCurveProgressView.color = curveData.color
            baseCurveProgressView.sizeMultiplier = curveData.sizeMultiplier

            if (baseCurveProgressView is BaseRouletteProgressView) {
                baseCurveProgressView.radiusFixed = curveData.radiusFixed
                baseCurveProgressView.radiusMoving = curveData.radiusMoving
                baseCurveProgressView.distanceFromCenter = curveData.distanceFromCenter

                baseCurveProgressView.numberOfCycles = curveData.numberOfCycles.toFloat()
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.checkBoxHasHole -> curveData.hasHole = isChecked
        }
        invalidateView(baseCurveProgressView)
    }

    fun applySettings(baseCurveProgressView: BaseCurveProgressView) {
        invalidateView(baseCurveProgressView)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.viewColor1 -> curveData.color = ContextCompat.getColor(context!!, R.color.picker_color_1)
            R.id.viewColor2 -> curveData.color = ContextCompat.getColor(context!!, R.color.picker_color_2)
            R.id.viewColor3 -> curveData.color = ContextCompat.getColor(context!!, R.color.picker_color_3)
            R.id.viewColor4 -> curveData.color = ContextCompat.getColor(context!!, R.color.picker_color_4)
            R.id.viewColor5 -> curveData.color = ContextCompat.getColor(context!!, R.color.picker_color_5)
            R.id.viewColor6 -> curveData.color = ContextCompat.getColor(context!!, R.color.picker_color_6)
        }
        invalidateView(baseCurveProgressView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("curve_data", curveData)
    }
}

fun Resources.dpToPx(dp: Float): Float {
    return dp * this.displayMetrics.density
}
