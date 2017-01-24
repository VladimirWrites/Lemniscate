package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
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
     * Curve will be drawn on interval  [0, 2*mNumberOfCycles*π] before repeating
     */
    protected float mNumberOfCycles = 1;


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
            setNumberOfCycles(a.getFloat(R.styleable.RouletteCurveProgressView_numberOfCycles, mNumberOfCycles));
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
        return mNumberOfCycles;
    }

    public void setNumberOfCycles(float numberOfCycles) {
        this.mNumberOfCycles = numberOfCycles;
    }

    @Override
    public double getT(int i) {
        return i* mNumberOfCycles *2*Math.PI/mPrecision;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        RouletteCurveSavedState ss = new RouletteCurveSavedState(superState);

        ss.radiusFixed = this.mRadiusFixed;
        ss.radiusMoving = this.mRadiusMoving;
        ss.distanceFromCenter = this.mDistanceFromCenter;
        ss.numberOfCycles = this.mNumberOfCycles;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof RouletteCurveSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        RouletteCurveSavedState ss = (RouletteCurveSavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        setRadiusFixed(ss.radiusFixed);
        setRadiusMoving(ss.radiusMoving);
        setDistanceFromCenter(ss.distanceFromCenter);
        setNumberOfCycles(ss.numberOfCycles);
    }



    static class RouletteCurveSavedState extends BaseCurveProgressView.BaseCurveSavedState {

        float radiusFixed;
        float radiusMoving;
        float distanceFromCenter;
        float numberOfCycles;

        RouletteCurveSavedState(Parcelable superState) {
            super(superState);
        }

        private RouletteCurveSavedState(Parcel in) {
            super(in);
            this.radiusFixed = in.readFloat();
            this.radiusMoving = in.readFloat();
            this.distanceFromCenter = in.readFloat();
            this.numberOfCycles = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.radiusFixed);
            out.writeFloat(this.radiusMoving);
            out.writeFloat(this.distanceFromCenter);
            out.writeFloat(this.numberOfCycles);
        }

        public static final Parcelable.Creator<RouletteCurveSavedState> CREATOR =
                new Parcelable.Creator<RouletteCurveSavedState>() {
                    public RouletteCurveSavedState createFromParcel(Parcel in) {
                        return new RouletteCurveSavedState(in);
                    }
                    public RouletteCurveSavedState[] newArray(int size) {
                        return new RouletteCurveSavedState[size];
                    }
                };
    }
}
