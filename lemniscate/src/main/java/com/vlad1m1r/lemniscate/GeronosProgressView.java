package com.vlad1m1r.lemniscate;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by vladimirjovanovic on 1/18/17.
 */

public class GeronosProgressView extends BaseLemniscateProgressView {

    public GeronosProgressView(Context context) {
        super(context);
    }

    public GeronosProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GeronosProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(int i) {
        double t = i*2*Math.PI/mPrecision;
        return mLemniscateParamX * Math.sin(t) * Math.cos(t);
    }

    @Override
    public double getGraphX(int i) {
        double t = i*2*Math.PI/mPrecision;
        return mLemniscateParamX * Math.sin(t);
    }
}
