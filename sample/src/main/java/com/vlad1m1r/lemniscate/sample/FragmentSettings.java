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

import com.vlad1m1r.lemniscate.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.BernoullisProgressView;
import com.vlad1m1r.lemniscate.GeronosProgressView;

/**
 * Created by vladimirjovanovic on 1/19/17.
 */

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


    protected int mPrecision = 200;
    protected float mStrokeWidth = 10;
    protected float mSizeMultiplier = 1;
    protected float mLineLength = 0.6f;
    protected float mLineMinLength = 0.4f, mLineMaxLength = 0.8f;
    protected boolean mIsLineLengthChangeable = true;
    protected int mColor;

    protected long mDuration = 2000;
    protected boolean mHasHole = false;

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

        setupViews();

        return root;
    }

    private void setupViews() {
        mSeekBarStrokeWidth.setMax(50);
        mSeekBarStrokeWidth.setProgress((int)mStrokeWidth);
        mSeekBarStrokeWidth.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLength.setMax(99);
        mSeekBarStrokeLength.setProgress(Math.round(100 * mLineLength)-1);
        mSeekBarStrokeLength.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLengthMax.setMax(99);
        mSeekBarStrokeLengthMax.setProgress(Math.round(100 * mLineMaxLength)-1);
        mSeekBarStrokeLengthMax.setOnSeekBarChangeListener(this);

        mSeekBarSizeMultiplier.setMax(15);
        mSeekBarSizeMultiplier.setProgress(5);
        mSeekBarSizeMultiplier.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLengthMin.setMax(99);
        mSeekBarStrokeLengthMin.setProgress(Math.round(100 * mLineMinLength)-1);
        mSeekBarStrokeLengthMin.setOnSeekBarChangeListener(this);

        mSeekBarAnimationDuration.setMax(200);
        mSeekBarAnimationDuration.setProgress(((int)mDuration)/10-1);
        mSeekBarAnimationDuration.setOnSeekBarChangeListener(this);


        mCheckBoxChangeableLength.setOnCheckedChangeListener(this);
        mCheckBoxHasHole.setOnCheckedChangeListener(this);

        mCheckBoxChangeableLength.setChecked(mIsLineLengthChangeable);
        mCheckBoxHasHole.setChecked(mHasHole);

        mSeekBarPrecision.setMax(990);
        mSeekBarPrecision.setProgress(mPrecision);
        mSeekBarPrecision.setOnSeekBarChangeListener(this);

        mColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
    }

    public void setBaseCurveProgressView(BaseCurveProgressView baseCurveProgressView) {
        mBaseCurveProgressView = baseCurveProgressView;
        if(mBaseCurveProgressView instanceof BernoullisProgressView ||
                mBaseCurveProgressView instanceof GeronosProgressView) {
            mCheckBoxHasHole.setEnabled(true);
        } else {
            mCheckBoxHasHole.setEnabled(false);
        }
        invalidateView();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBarStrokeWidth:
                mStrokeWidth = dpToPx(i/3.f);
                break;
            case R.id.seekBarLineLength:
                mLineLength = (i+1)/100.f;
                break;
            case R.id.seekBarMaxLineLength:
                if(i < mSeekBarStrokeLengthMin.getProgress()) {
                    mSeekBarStrokeLengthMax.setProgress(mSeekBarStrokeLengthMin.getProgress());
                }
                else mLineMaxLength = (i+1)/100.f;
                break;
            case R.id.seekBarMinLineLength:
                if(i > mSeekBarStrokeLengthMax.getProgress()) {
                    mSeekBarStrokeLengthMin.setProgress(mSeekBarStrokeLengthMax.getProgress());
                }
                else mLineMinLength = (i+1)/100.f;
                break;
            case R.id.seekBarSizeMultiplier:
                mSizeMultiplier = (i+5)/10.0f;
                break;
            case R.id.seekBarAnimationDuration:
                mDuration = (i+1) * 10;
                break;
            case R.id.seekBarPrecision:
                mPrecision = i+10;
                break;
        }

        invalidateView();
    }

    private void invalidateView() {
        if(mBaseCurveProgressView != null) {
            mBaseCurveProgressView.setPrecision(mPrecision);
            mBaseCurveProgressView.setStrokeWidth(mStrokeWidth);
            mBaseCurveProgressView.setSizeMultiplier(mSizeMultiplier);
            mBaseCurveProgressView.setLineLength(mLineLength);
            mBaseCurveProgressView.setLineMaxLength(mLineMaxLength);
            mBaseCurveProgressView.setLineMinLength(mLineMinLength);
            mBaseCurveProgressView.setIsLineLengthChangeable(mIsLineLengthChangeable);
            mBaseCurveProgressView.setDuration((int) mDuration);
            mBaseCurveProgressView.setHasHole(mHasHole);
            mBaseCurveProgressView.setColor(mColor);
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

        invalidateView();
    }

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}
