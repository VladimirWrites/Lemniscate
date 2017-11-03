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

package com.vlad1m1r.lemniscate.sample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vlad1m1r.lemniscate.BernoullisBowProgressView;
import com.vlad1m1r.lemniscate.BernoullisProgressView;
import com.vlad1m1r.lemniscate.BernoullisSharpProgressView;
import com.vlad1m1r.lemniscate.GeronosProgressView;
import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.roulette.BaseRouletteProgressView;

public class FragmentSettings extends Fragment implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    protected int precision = 200;
    protected float strokeWidth = 10;
    protected float sizeMultiplier = 1;
    protected float lineMinLength = 0.4f, lineMaxLength = 0.8f;
    protected int color;
    protected long duration = 1000;
    protected boolean hasHole = false;
    protected float a = 4f;
    protected float b = 1f;
    protected float d = 3f;
    protected int numberOfCycles = 1;
    private BaseCurveProgressView baseCurveProgressView;
    private SeekBar seekBarStrokeWidth;
    private SeekBar seekBarStrokeLengthMax;
    private SeekBar seekBarStrokeLengthMin;
    private CheckBox checkBoxHasHole;
    private SeekBar seekBarSizeMultiplier;
    private SeekBar seekBarAnimationDuration;
    private SeekBar seekBarPrecision;
    private SeekBar seekBarA, seekBarB, seekBarD;
    private SeekBar seekBarNumberOfCycles;
    private TextView textViewStrokeWidth;
    private TextView textViewLineLengthMax;
    private TextView textViewLineLengthMin;
    private TextView textViewSizeMultiplier;
    private TextView textViewAnimationDuration;
    private TextView textViewPrecision;
    private View viewColor1, viewColor2, viewColor3, viewColor4, viewColor5, viewColor6;

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        seekBarStrokeWidth = root.findViewById(R.id.seekBarStrokeWidth);
        seekBarStrokeLengthMax = root.findViewById(R.id.seekBarMaxLineLength);
        seekBarStrokeLengthMin = root.findViewById(R.id.seekBarMinLineLength);
        checkBoxHasHole = root.findViewById(R.id.checkBoxHasHole);
        seekBarSizeMultiplier = root.findViewById(R.id.seekBarSizeMultiplier);
        seekBarAnimationDuration = root.findViewById(R.id.seekBarAnimationDuration);
        seekBarPrecision = root.findViewById(R.id.seekBarPrecision);

        viewColor1 = root.findViewById(R.id.viewColor1);
        viewColor2 = root.findViewById(R.id.viewColor2);
        viewColor3 = root.findViewById(R.id.viewColor3);
        viewColor4 = root.findViewById(R.id.viewColor4);
        viewColor5 = root.findViewById(R.id.viewColor5);
        viewColor6 = root.findViewById(R.id.viewColor6);

        seekBarA = root.findViewById(R.id.seekBarA);
        seekBarB = root.findViewById(R.id.seekBarB);
        seekBarD = root.findViewById(R.id.seekBarD);
        seekBarNumberOfCycles = root.findViewById(R.id.seekBarNumberOfCycles);

        textViewStrokeWidth = root.findViewById(R.id.textStrokeWidth);
        textViewLineLengthMax = root.findViewById(R.id.textMaxLineLength);
        textViewLineLengthMin = root.findViewById(R.id.textMinLineLength);
        textViewSizeMultiplier = root.findViewById(R.id.textSizeMultiplier);
        textViewAnimationDuration = root.findViewById(R.id.textAnimationDuration);
        textViewPrecision = root.findViewById(R.id.textPrecision);

        setupViews();

        return root;
    }

    private void setupViews() {
        seekBarStrokeWidth.setMax(50);
        seekBarStrokeWidth.setProgress((int) strokeWidth);
        seekBarStrokeWidth.setOnSeekBarChangeListener(this);

        seekBarStrokeLengthMax.setMax(99);
        seekBarStrokeLengthMax.setProgress(Math.round(100 * lineMaxLength) - 1);
        seekBarStrokeLengthMax.setOnSeekBarChangeListener(this);

        seekBarSizeMultiplier.setMax(15);
        seekBarSizeMultiplier.setProgress(5);
        seekBarSizeMultiplier.setOnSeekBarChangeListener(this);

        seekBarStrokeLengthMin.setMax(99);
        seekBarStrokeLengthMin.setProgress(Math.round(100 * lineMinLength) - 1);
        seekBarStrokeLengthMin.setOnSeekBarChangeListener(this);

        seekBarAnimationDuration.setMax(199);
        seekBarAnimationDuration.setProgress(((int) duration) / 10 - 1);
        seekBarAnimationDuration.setOnSeekBarChangeListener(this);

        checkBoxHasHole.setOnCheckedChangeListener(this);

        checkBoxHasHole.setChecked(hasHole);

        seekBarPrecision.setMax(990);
        seekBarPrecision.setProgress(precision);
        seekBarPrecision.setOnSeekBarChangeListener(this);

        seekBarA.setMax(10);
        seekBarA.setProgress((int)a-1);
        seekBarA.setOnSeekBarChangeListener(this);

        seekBarB.setMax(10);
        seekBarB.setProgress((int)b-1);
        seekBarB.setOnSeekBarChangeListener(this);

        seekBarD.setMax(10);
        seekBarD.setProgress((int)d-1);
        seekBarD.setOnSeekBarChangeListener(this);

        seekBarNumberOfCycles.setMax(5);
        seekBarNumberOfCycles.setProgress(numberOfCycles-1);
        seekBarNumberOfCycles.setOnSeekBarChangeListener(this);

        color = ContextCompat.getColor(getContext(), R.color.picker_color_1);

        viewColor1.setOnClickListener(this);
        viewColor2.setOnClickListener(this);
        viewColor3.setOnClickListener(this);
        viewColor4.setOnClickListener(this);
        viewColor5.setOnClickListener(this);
        viewColor6.setOnClickListener(this);
    }

    public void setBaseCurveProgressView(BaseCurveProgressView baseCurveProgressView) {
        this.baseCurveProgressView = baseCurveProgressView;

        //Checkbox
        if (this.baseCurveProgressView instanceof BernoullisProgressView ||
                this.baseCurveProgressView instanceof GeronosProgressView ||
                this.baseCurveProgressView instanceof BernoullisBowProgressView ||
                this.baseCurveProgressView instanceof BernoullisSharpProgressView) {
            checkBoxHasHole.setEnabled(true);
        } else {
            checkBoxHasHole.setEnabled(false);
        }

        //Roulette params
        if(this.baseCurveProgressView instanceof BaseRouletteProgressView) {
            seekBarA.setEnabled(true);
            seekBarB.setEnabled(true);
            seekBarD.setEnabled(true);
            seekBarNumberOfCycles.setEnabled(true);
        } else {
            seekBarA.setEnabled(false);
            seekBarB.setEnabled(false);
            seekBarD.setEnabled(false);
            seekBarNumberOfCycles.setEnabled(false);
        }

        invalidateView(this.baseCurveProgressView);
        updateValues();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBarStrokeWidth:
                strokeWidth = dpToPx(i/3.f);
                break;
            case R.id.seekBarMaxLineLength:
                if (i < seekBarStrokeLengthMin.getProgress()) {
                    seekBarStrokeLengthMax.setProgress(seekBarStrokeLengthMin.getProgress());
                } else lineMaxLength = (i + 1) / 100.f;
                break;
            case R.id.seekBarMinLineLength:
                if (i > seekBarStrokeLengthMax.getProgress()) {
                    seekBarStrokeLengthMin.setProgress(seekBarStrokeLengthMax.getProgress());
                } else lineMinLength = (i + 1) / 100.f;
                break;
            case R.id.seekBarSizeMultiplier:
                sizeMultiplier = (i + 5) / 10.0f;
                break;
            case R.id.seekBarAnimationDuration:
                duration = (i + 1) * 10;
                break;
            case R.id.seekBarPrecision:
                precision = i + 10;
                break;
            case R.id.seekBarA:
                a = i + 1;
                break;
            case R.id.seekBarB:
                b = i + 1;
                break;
            case R.id.seekBarD:
                d = i + 1;
                break;
            case R.id.seekBarNumberOfCycles:
                numberOfCycles = i + 1;
                break;
        }
        invalidateView(baseCurveProgressView);
        updateValues();
    }

    private void updateValues() {
        textViewStrokeWidth.setText(String.valueOf(strokeWidth));
        textViewLineLengthMax.setText(String.format(getResources().getString(R.string.format_percentage), (int)(lineMaxLength * 100)));
        textViewLineLengthMin.setText(String.format(getResources().getString(R.string.format_percentage), (int)(lineMinLength * 100)));
        textViewSizeMultiplier.setText(String.valueOf(sizeMultiplier));
        textViewAnimationDuration.setText(String.format(getResources().getString(R.string.format_ms), duration));
        textViewPrecision.setText(String.format(getResources().getString(R.string.format_points), precision));
    }

    private void invalidateView(BaseCurveProgressView baseCurveProgressView) {
        if (baseCurveProgressView != null) {
            baseCurveProgressView.setPrecision(precision);
            baseCurveProgressView.setStrokeWidth(strokeWidth);
            baseCurveProgressView.setLineMaxLength(lineMaxLength);
            baseCurveProgressView.setLineMinLength(lineMinLength);
            baseCurveProgressView.setDuration((int) duration);
            baseCurveProgressView.setHasHole(hasHole);
            baseCurveProgressView.setColor(color);
            baseCurveProgressView.setSizeMultiplier(sizeMultiplier);

            if(baseCurveProgressView instanceof BaseRouletteProgressView) {
                BaseRouletteProgressView baseRouletteProgressView = (BaseRouletteProgressView) baseCurveProgressView;
                baseRouletteProgressView.setRadiusFixed(a);
                baseRouletteProgressView.setRadiusMoving(b);
                baseRouletteProgressView.setDistanceFromCenter(d);

                baseRouletteProgressView.setNumberOfCycles(numberOfCycles);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkBoxHasHole:
                hasHole = isChecked;
                break;
        }
        invalidateView(baseCurveProgressView);
    }

    public void applySettings(BaseCurveProgressView baseCurveProgressView) {
        invalidateView(baseCurveProgressView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewColor1:
                color = ContextCompat.getColor(getContext(), R.color.picker_color_1);
                break;
            case R.id.viewColor2:
                color = ContextCompat.getColor(getContext(), R.color.picker_color_2);
                break;
            case R.id.viewColor3:
                color = ContextCompat.getColor(getContext(), R.color.picker_color_3);
                break;
            case R.id.viewColor4:
                color = ContextCompat.getColor(getContext(), R.color.picker_color_4);
                break;
            case R.id.viewColor5:
                color = ContextCompat.getColor(getContext(), R.color.picker_color_5);
                break;
            case R.id.viewColor6:
                color = ContextCompat.getColor(getContext(), R.color.picker_color_6);
                break;
        }
        invalidateView(baseCurveProgressView);
    }
}
