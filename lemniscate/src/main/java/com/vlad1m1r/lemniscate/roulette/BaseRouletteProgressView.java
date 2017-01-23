package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.sample.lemniscate.R;

/**
 * Created by vladimirjovanovic on 1/20/17.
 */

public abstract class BaseRouletteProgressView extends BaseCurveProgressView {


    /**
     * Radius of the non-moving circle
     */
    protected float mRadiusFixed = 3f;
    /**
     * Radius of the moving circle
     */
    protected float mRadiusMoving = 1f;
    /**
     * Distance from the center of the moving circle
     */
    protected float mDistanceFromCenter = 1f;

    /**
     * Curve will be drawn on interval  [0, 2*numberOfCycles*π] before repeating
     */
    protected float numberOfCycles = 1;


    public BaseRouletteProgressView(Context context) {
        super(context);
    }

    public BaseRouletteProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RouletteCurveProgressView,
                0, 0);

        try {
            setRadiusFixed(a.getFloat(R.styleable.RouletteCurveProgressView_radiusFixed, mRadiusFixed));
            setRadiusMoving(a.getFloat(R.styleable.RouletteCurveProgressView_radiusMoving, mRadiusMoving));
            setDistanceFromCenter(a.getFloat(R.styleable.RouletteCurveProgressView_distanceFromCenter, mDistanceFromCenter));
            setNumberOfCycles(a.getFloat(R.styleable.RouletteCurveProgressView_numberOfCycles, numberOfCycles));
        } finally {
            a.recycle();
        }
    }

    public BaseRouletteProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getRadiusFixed() {
        return mRadiusFixed;
    }

    public void setRadiusFixed(float radiusFixed) {
        this.mRadiusFixed = radiusFixed;
    }

    public float getRadiusMoving() {
        return mRadiusMoving;
    }

    public void setRadiusMoving(float radiusMoving) {
        this.mRadiusMoving = radiusMoving;
    }

    public float getDistanceFromCenter() {
        return mDistanceFromCenter;
    }

    public void setDistanceFromCenter(float distanceFromCenter) {
        this.mDistanceFromCenter = distanceFromCenter;
    }

    public float getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(float numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    @Override
    public double getT(int i) {
        return i*numberOfCycles*2*Math.PI/mPrecision;
    }
}
