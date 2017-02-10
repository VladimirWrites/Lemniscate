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

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.BernoullisProgressView;
import com.vlad1m1r.lemniscate.GeronosProgressView;
import com.vlad1m1r.lemniscate.roulette.BaseRouletteProgressView;

public class FragmentSettings extends Fragment implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private BaseCurveProgressView mBaseCurveProgressView;

    private SeekBar mSeekBarStrokeWidth;
    private SeekBar mSeekBarStrokeLength;
    private SeekBar mSeekBarStrokeLengthMax;
    private SeekBar mSeekBarStrokeLengthMin;
    private CheckBox mCheckBoxChangeableLength;
    private CheckBox mCheckBoxHasHole;
    private SeekBar mSeekBarSizeMultiplier;
    private SeekBar mSeekBarAnimationDuration;
    private SeekBar mSeekBarPrecision;

    private SeekBar mSeekBarA, mSeekBarB, mSeekBarD;
    private SeekBar mSeekBarNumberOfCycles;

    private TextView mTextViewStrokeWidth;
    private TextView mTextViewLineLength;
    private TextView mTextViewLineLengthMax;
    private TextView mTextViewLineLengthMin;
    private TextView mTextViewSizeMultiplier;
    private TextView mTextViewAnimationDuration;
    private TextView mTextViewPrecision;

    protected int mPrecision = 200;
    protected float mStrokeWidth = 10;
    protected float mSizeMultiplier = 1;
    protected float mLineLength = 0.6f;
    protected float mLineMinLength = 0.4f, mLineMaxLength = 0.8f;
    protected boolean mIsLineLengthChangeable = true;
    protected int mColor;

    protected long mDuration = 1000;
    protected boolean mHasHole = false;

    protected float a = 4f;
    protected float b = 1f;
    protected float d = 3f;

    protected int numberOfCycles = 1;

    public static FragmentSettings getInstance(BaseCurveProgressView baseCurveProgressView) {
        FragmentSettings fragmentSettings = new FragmentSettings();
        fragmentSettings.setBaseCurveProgressView(baseCurveProgressView);
        return fragmentSettings;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        mSeekBarStrokeWidth = (SeekBar) root.findViewById(R.id.seekBarStrokeWidth);
        mSeekBarStrokeLength = (SeekBar) root.findViewById(R.id.seekBarLineLength);
        mSeekBarStrokeLengthMax = (SeekBar) root.findViewById(R.id.seekBarMaxLineLength);
        mSeekBarStrokeLengthMin = (SeekBar) root.findViewById(R.id.seekBarMinLineLength);
        mCheckBoxChangeableLength = (CheckBox) root.findViewById(R.id.checkBoxChangeableLength);
        mCheckBoxHasHole = (CheckBox) root.findViewById(R.id.checkBoxHasHole);
        mSeekBarSizeMultiplier = (SeekBar) root.findViewById(R.id.seekBarSizeMultiplier);
        mSeekBarAnimationDuration = (SeekBar) root.findViewById(R.id.seekBarAnimationDuration);
        mSeekBarPrecision = (SeekBar) root.findViewById(R.id.seekBarPrecision);

        mSeekBarA = (SeekBar) root.findViewById(R.id.seekBarA);
        mSeekBarB = (SeekBar) root.findViewById(R.id.seekBarB);
        mSeekBarD = (SeekBar) root.findViewById(R.id.seekBarD);
        mSeekBarNumberOfCycles = (SeekBar) root.findViewById(R.id.seekBarNumberOfCycles);

        mTextViewStrokeWidth = (TextView) root.findViewById(R.id.textStrokeWidth);
        mTextViewLineLength = (TextView) root.findViewById(R.id.textLineLength);
        mTextViewLineLengthMax = (TextView) root.findViewById(R.id.textMaxLineLength);
        mTextViewLineLengthMin = (TextView) root.findViewById(R.id.textMinLineLength);
        mTextViewSizeMultiplier = (TextView) root.findViewById(R.id.textSizeMultiplier);
        mTextViewAnimationDuration = (TextView) root.findViewById(R.id.textAnimationDuration);
        mTextViewPrecision = (TextView) root.findViewById(R.id.textPrecision);

        setupViews();

        return root;
    }

    private void setupViews() {
        mSeekBarStrokeWidth.setMax(50);
        mSeekBarStrokeWidth.setProgress((int) mStrokeWidth);
        mSeekBarStrokeWidth.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLength.setMax(99);
        mSeekBarStrokeLength.setProgress(Math.round(100 * mLineLength) - 1);
        mSeekBarStrokeLength.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLengthMax.setMax(99);
        mSeekBarStrokeLengthMax.setProgress(Math.round(100 * mLineMaxLength) - 1);
        mSeekBarStrokeLengthMax.setOnSeekBarChangeListener(this);

        mSeekBarSizeMultiplier.setMax(15);
        mSeekBarSizeMultiplier.setProgress(5);
        mSeekBarSizeMultiplier.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLengthMin.setMax(99);
        mSeekBarStrokeLengthMin.setProgress(Math.round(100 * mLineMinLength) - 1);
        mSeekBarStrokeLengthMin.setOnSeekBarChangeListener(this);

        mSeekBarAnimationDuration.setMax(200);
        mSeekBarAnimationDuration.setProgress(((int) mDuration) / 10 - 1);
        mSeekBarAnimationDuration.setOnSeekBarChangeListener(this);


        mCheckBoxChangeableLength.setOnCheckedChangeListener(this);
        mCheckBoxHasHole.setOnCheckedChangeListener(this);

        mCheckBoxChangeableLength.setChecked(mIsLineLengthChangeable);
        mCheckBoxHasHole.setChecked(mHasHole);

        mSeekBarPrecision.setMax(990);
        mSeekBarPrecision.setProgress(mPrecision);
        mSeekBarPrecision.setOnSeekBarChangeListener(this);

        mSeekBarA.setMax(10);
        mSeekBarA.setProgress((int)a-1);
        mSeekBarA.setOnSeekBarChangeListener(this);

        mSeekBarB.setMax(10);
        mSeekBarB.setProgress((int)b-1);
        mSeekBarB.setOnSeekBarChangeListener(this);

        mSeekBarD.setMax(10);
        mSeekBarD.setProgress((int)d-1);
        mSeekBarD.setOnSeekBarChangeListener(this);

        mSeekBarNumberOfCycles.setMax(5);
        mSeekBarNumberOfCycles.setProgress(numberOfCycles-1);
        mSeekBarNumberOfCycles.setOnSeekBarChangeListener(this);


        mColor = ContextCompat.getColor(getContext(), R.color.color_primary);
    }

    public void setBaseCurveProgressView(BaseCurveProgressView baseCurveProgressView) {
        mBaseCurveProgressView = baseCurveProgressView;

        //Checkbox
        if (mBaseCurveProgressView instanceof BernoullisProgressView ||
                mBaseCurveProgressView instanceof GeronosProgressView) {
            mCheckBoxHasHole.setEnabled(true);
        } else {
            mCheckBoxHasHole.setEnabled(false);
        }

        //Roulette params
        if(mBaseCurveProgressView instanceof BaseRouletteProgressView) {
            mSeekBarA.setEnabled(true);
            mSeekBarB.setEnabled(true);
            mSeekBarD.setEnabled(true);
            mSeekBarNumberOfCycles.setEnabled(true);
        } else {
            mSeekBarA.setEnabled(false);
            mSeekBarB.setEnabled(false);
            mSeekBarD.setEnabled(false);
            mSeekBarNumberOfCycles.setEnabled(false);
        }



        invalidateView(mBaseCurveProgressView);
        updateValues();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBarStrokeWidth:
                mStrokeWidth = dpToPx(i / 3.f);
                break;
            case R.id.seekBarLineLength:
                mLineLength = (i + 1) / 100.f;
                break;
            case R.id.seekBarMaxLineLength:
                if (i < mSeekBarStrokeLengthMin.getProgress()) {
                    mSeekBarStrokeLengthMax.setProgress(mSeekBarStrokeLengthMin.getProgress());
                } else mLineMaxLength = (i + 1) / 100.f;
                break;
            case R.id.seekBarMinLineLength:
                if (i > mSeekBarStrokeLengthMax.getProgress()) {
                    mSeekBarStrokeLengthMin.setProgress(mSeekBarStrokeLengthMax.getProgress());
                } else mLineMinLength = (i + 1) / 100.f;
                break;
            case R.id.seekBarSizeMultiplier:
                mSizeMultiplier = (i + 5) / 10.0f;
                break;
            case R.id.seekBarAnimationDuration:
                mDuration = (i + 1) * 10;
                break;
            case R.id.seekBarPrecision:
                mPrecision = i + 10;
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

        invalidateView(mBaseCurveProgressView);

        updateValues();
    }

    private void updateValues() {
        mTextViewStrokeWidth.setText(String.valueOf(mStrokeWidth));
        mTextViewLineLength.setText(String.format(getResources().getString(R.string.format_percentage), (int)(mLineLength * 100)));
        mTextViewLineLengthMax.setText(String.format(getResources().getString(R.string.format_percentage), (int)(mLineMaxLength * 100)));
        mTextViewLineLengthMin.setText(String.format(getResources().getString(R.string.format_percentage), (int)(mLineMinLength * 100)));
        mTextViewSizeMultiplier.setText(String.valueOf(mSizeMultiplier));
        mTextViewAnimationDuration.setText(String.format(getResources().getString(R.string.format_ms), mDuration));
        mTextViewPrecision.setText(String.format(getResources().getString(R.string.format_points), mPrecision));
    }

    private void invalidateView(BaseCurveProgressView baseCurveProgressView) {
        if (baseCurveProgressView != null) {
            baseCurveProgressView.setPrecision(mPrecision);
            baseCurveProgressView.setStrokeWidth(mStrokeWidth);
            baseCurveProgressView.setSizeMultiplier(mSizeMultiplier);
            baseCurveProgressView.setLineLength(mLineLength);
            baseCurveProgressView.setLineMaxLength(mLineMaxLength);
            baseCurveProgressView.setLineMinLength(mLineMinLength);
            baseCurveProgressView.setIsLineLengthChangeable(mIsLineLengthChangeable);
            baseCurveProgressView.setDuration((int) mDuration);
            baseCurveProgressView.setHasHole(mHasHole);
            baseCurveProgressView.setColor(mColor);

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
            case R.id.checkBoxChangeableLength:
                mIsLineLengthChangeable = isChecked;
                mSeekBarStrokeLengthMin.setEnabled(isChecked);
                mSeekBarStrokeLengthMax.setEnabled(isChecked);
                mSeekBarStrokeLength.setEnabled(!isChecked);
                break;
            case R.id.checkBoxHasHole:
                mHasHole = isChecked;
                break;
        }

        invalidateView(mBaseCurveProgressView);
    }

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public void applySettings(BaseCurveProgressView baseCurveProgressView) {
        invalidateView(baseCurveProgressView);
    }
}
