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
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
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
import com.vlad1m1r.lemniscate.sample.databinding.FragmentSettingsBinding
import kotlin.math.round

class FragmentSettings : Fragment(), SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private lateinit var curveData: CurveData
    private var baseCurveProgressView: BaseCurveProgressView? = null

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        curveData = if(savedInstanceState != null && savedInstanceState.containsKey("curve_data")) {
            savedInstanceState.getParcelable("curve_data")!!
        } else {
            CurveData(color = ContextCompat.getColor(requireContext(), R.color.picker_color_1))
        }

        setupViews()
    }

    private fun setupViews() {

        binding.seekBarStrokeWidth.max = 50
        binding.seekBarStrokeWidth.progress = curveData.strokeWidth.toInt()
        binding.seekBarStrokeWidth.setOnSeekBarChangeListener(this)

        binding.seekBarMaxLineLength.max = 99
        binding.seekBarMaxLineLength.progress = round(100 * curveData.lineMaxLength).toInt() - 1
        binding.seekBarMaxLineLength.setOnSeekBarChangeListener(this)

        binding.seekBarSizeMultiplier.max = 15
        binding.seekBarSizeMultiplier.progress = 5
        binding.seekBarSizeMultiplier.setOnSeekBarChangeListener(this)

        binding.seekBarMinLineLength.max = 99
        binding. seekBarMinLineLength.progress = round(100 * curveData.lineMinLength).toInt() - 1
        binding.seekBarMinLineLength.setOnSeekBarChangeListener(this)

        binding.seekBarAnimationDuration.max = 199
        binding.seekBarAnimationDuration.progress = curveData.duration / 10 - 1
        binding.seekBarAnimationDuration.setOnSeekBarChangeListener(this)

        binding.checkBoxHasHole.setOnCheckedChangeListener(this)

        binding.checkBoxHasHole.isChecked = curveData.hasHole

        binding.seekBarPrecision.max = 990
        binding.seekBarPrecision.progress = curveData.precision
        binding.seekBarPrecision.setOnSeekBarChangeListener(this)

        binding.seekBarA.max = 10
        binding.seekBarA.progress = (curveData.radiusFixed - 1).toInt()
        binding.seekBarA.setOnSeekBarChangeListener(this)

        binding.seekBarB.max = 10
        binding.seekBarB.progress = (curveData.radiusMoving - 1).toInt()
        binding.seekBarB.setOnSeekBarChangeListener(this)

        binding.seekBarD.max = 10
        binding.seekBarD.progress = (curveData.distanceFromCenter - 1).toInt()
        binding.seekBarD.setOnSeekBarChangeListener(this)

        binding.seekBarNumberOfCycles.max = 5
        binding.seekBarNumberOfCycles.progress = curveData.numberOfCycles - 1
        binding.seekBarNumberOfCycles.setOnSeekBarChangeListener(this)

        binding.viewColor1.setOnClickListener(this)
        binding.viewColor2.setOnClickListener(this)
        binding.viewColor3.setOnClickListener(this)
        binding.viewColor4.setOnClickListener(this)
        binding.viewColor5.setOnClickListener(this)
        binding.viewColor6.setOnClickListener(this)
    }

    fun setBaseCurveProgressView(baseCurveProgressView: BaseCurveProgressView) {
        this.baseCurveProgressView = baseCurveProgressView

        //Checkbox
        binding.checkBoxHasHole.isEnabled = this.baseCurveProgressView is BernoullisProgressView ||
                this.baseCurveProgressView is GeronosProgressView ||
                this.baseCurveProgressView is BernoullisBowProgressView ||
                this.baseCurveProgressView is BernoullisSharpProgressView

        //Roulette params
        if (this.baseCurveProgressView is BaseRouletteProgressView) {
            binding.seekBarA.isEnabled = true
            binding.seekBarB.isEnabled = true
            binding.seekBarD.isEnabled = true
            binding.seekBarNumberOfCycles!!.isEnabled = true
        } else {
            binding.seekBarA.isEnabled = false
            binding.seekBarB.isEnabled = false
            binding.seekBarD.isEnabled = false
            binding.seekBarNumberOfCycles.isEnabled = false
        }

        invalidateView(this.baseCurveProgressView)
        updateValues()
    }

    override fun onProgressChanged(seekBar: SeekBar, i: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.seekBarStrokeWidth -> curveData.strokeWidth = resources.dpToPx(i / 3.0f)
            R.id.seekBarMaxLineLength -> if (i <  binding.seekBarMinLineLength.progress) {
                binding.seekBarMaxLineLength.progress =  binding.seekBarMinLineLength.progress
            } else
                curveData.lineMaxLength = (i + 1) / 100.0f
            R.id.seekBarMinLineLength -> if (i >  binding.seekBarMaxLineLength.progress) {
                binding.seekBarMinLineLength.progress =  binding.seekBarMaxLineLength.progress
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
        binding.textStrokeWidth.text = curveData.strokeWidth.toString()
        binding.textMaxLineLength.text = String.format(resources.getString(R.string.format_percentage), (curveData.lineMaxLength * 100).toInt())
        binding.textMinLineLength.text = String.format(resources.getString(R.string.format_percentage), (curveData.lineMinLength * 100).toInt())
        binding.textSizeMultiplier.text = curveData.sizeMultiplier.toString()
        binding.textAnimationDuration.text = String.format(resources.getString(R.string.format_ms), curveData.duration)
        binding.textPrecision.text = String.format(resources.getString(R.string.format_points), curveData.precision)
    }

    private fun invalidateView(baseCurveProgressView: BaseCurveProgressView?) {
        baseCurveProgressView?.apply {
            precision = curveData.precision
            strokeWidth = curveData.strokeWidth
            lineMaxLength = curveData.lineMaxLength
            lineMinLength = curveData.lineMinLength
            duration = curveData.duration
            hasHole = curveData.hasHole
            color = curveData.color
            sizeMultiplier = curveData.sizeMultiplier

            if (this is BaseRouletteProgressView) {
                radiusFixed = curveData.radiusFixed
                radiusMoving = curveData.radiusMoving
                distanceFromCenter = curveData.distanceFromCenter

                numberOfCycles = curveData.numberOfCycles.toFloat()
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
            R.id.viewColor1 -> curveData.color = ContextCompat.getColor(requireContext(), R.color.picker_color_1)
            R.id.viewColor2 -> curveData.color = ContextCompat.getColor(requireContext(), R.color.picker_color_2)
            R.id.viewColor3 -> curveData.color = ContextCompat.getColor(requireContext(), R.color.picker_color_3)
            R.id.viewColor4 -> curveData.color = ContextCompat.getColor(requireContext(), R.color.picker_color_4)
            R.id.viewColor5 -> curveData.color = ContextCompat.getColor(requireContext(), R.color.picker_color_5)
            R.id.viewColor6 -> curveData.color = ContextCompat.getColor(requireContext(), R.color.picker_color_6)
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
