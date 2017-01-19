package com.vlad1m1r.lemniscate.sample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.vlad1m1r.lemniscate.BaseCurveProgressView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private BaseCurveProgressView mInfinityProgressView;

    private SeekBar mSeekBarStrokeWidth;

    private SeekBar mSeekBarStrokeLength;

    private SeekBar mSeekBarStrokeLengthMax;

    private SeekBar mSeekBarStrokeLengthMin;

    private CheckBox mCheckBoxChangeableLength;

    private CheckBox mCheckBoxHasHole;

    private SeekBar mSeekBarSizeMultiplier;

    private SeekBar mSeekBarAnimationDuration;

    private SeekBar mSeekBarPrecision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfinityProgressView = (BaseCurveProgressView) findViewById(R.id.infinityProgressView);
        mSeekBarStrokeWidth = (SeekBar) findViewById(R.id.seekBarStrokeWidth);
        mSeekBarStrokeLength = (SeekBar) findViewById(R.id.seekBarStrokeLength);
        mSeekBarStrokeLengthMax = (SeekBar) findViewById(R.id.seekBarStrokeLengthMax);
        mSeekBarStrokeLengthMin = (SeekBar) findViewById(R.id.seekBarStrokeLengthMin);
        mCheckBoxChangeableLength = (CheckBox) findViewById(R.id.checkBoxChangeableLength);
        mCheckBoxHasHole = (CheckBox) findViewById(R.id.checkBoxHasHole);
        mSeekBarSizeMultiplier = (SeekBar) findViewById(R.id.seekBarSizeMultiplier);
        mSeekBarAnimationDuration = (SeekBar) findViewById(R.id.seekBarAnimationDuration);
        mSeekBarPrecision = (SeekBar) findViewById(R.id.seekBarPrecision);

        mSeekBarStrokeWidth.setMax(50);
        mSeekBarStrokeWidth.setProgress((int)mInfinityProgressView.getStrokeWidth());
        mSeekBarStrokeWidth.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLength.setMax(99);
        mSeekBarStrokeLength.setProgress(Math.round(100 * mInfinityProgressView.getLineLength())-1);
        mSeekBarStrokeLength.setOnSeekBarChangeListener(this);

        mSeekBarStrokeLengthMax.setMax(99);
        mSeekBarStrokeLengthMax.setProgress(Math.round(100 * mInfinityProgressView.getLineMaxLength())-1);
        mSeekBarStrokeLengthMax.setOnSeekBarChangeListener(this);

        mSeekBarSizeMultiplier.setOnSeekBarChangeListener(this);


        mSeekBarStrokeLengthMin.setMax(99);
        mSeekBarStrokeLengthMin.setProgress(Math.round(100 * mInfinityProgressView.getLineMinLength())-1);
        mSeekBarStrokeLengthMin.setOnSeekBarChangeListener(this);

        mSeekBarAnimationDuration.setMax(200);
        mSeekBarAnimationDuration.setProgress(((int)mInfinityProgressView.getDuration())/10-1);
        mSeekBarAnimationDuration.setOnSeekBarChangeListener(this);


        mCheckBoxChangeableLength.setOnCheckedChangeListener(this);
        mCheckBoxHasHole.setOnCheckedChangeListener(this);

        mCheckBoxChangeableLength.setChecked(mInfinityProgressView.isLineLengthChangeable());
        mCheckBoxHasHole.setChecked(mInfinityProgressView.isHasHole());

        mSeekBarPrecision.setMax(990);
        mSeekBarPrecision.setProgress(mInfinityProgressView.getPrecision());
        mSeekBarPrecision.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekBarStrokeWidth:
                mInfinityProgressView.setStrokeWidth(dpToPx(i/3.f));
                break;
            case R.id.seekBarStrokeLength:
                mInfinityProgressView.setLineLength((i+1)/100.f);
                break;
            case R.id.seekBarStrokeLengthMax:
                if(i < mSeekBarStrokeLengthMin.getProgress()) {
                    mSeekBarStrokeLengthMax.setProgress(mSeekBarStrokeLengthMin.getProgress());
                }
                else mInfinityProgressView.setLineMaxLength((i+1)/100.f);
                break;
            case R.id.seekBarStrokeLengthMin:
                if(i > mSeekBarStrokeLengthMax.getProgress()) {
                    mSeekBarStrokeLengthMin.setProgress(mSeekBarStrokeLengthMax.getProgress());
                }
                else mInfinityProgressView.setLineMinLength((i+1)/100.f);
                break;
            case R.id.seekBarSizeMultiplier:
                mInfinityProgressView.setSizeMultiplier((i+5)/10.0f);
                break;
            case R.id.seekBarAnimationDuration:
                mInfinityProgressView.setDuration((i+1) * 10);
                break;
            case R.id.seekBarPrecision:
                mInfinityProgressView.setPrecision(i+10);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.checkBoxChangeableLength:
                mInfinityProgressView.setIsLineLengthChangeable(b);
                mSeekBarStrokeLengthMin.setEnabled(b);
                mSeekBarStrokeLengthMax.setEnabled(b);
                mSeekBarStrokeLength.setEnabled(!b);
                break;
            case R.id.checkBoxHasHole:
                mInfinityProgressView.setHasHole(b);
                break;
        }
    }

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }


}
