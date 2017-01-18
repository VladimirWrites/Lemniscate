package com.vlad1m1r.lemniscate;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by vladimirjovanovic on 1/18/17.
 */

public class BernoullisProgressView extends BaseLemniscateProgressView {

    public BernoullisProgressView(Context context) {
        super(context);
    }

    public BernoullisProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BernoullisProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public double getGraphY(int i){
        double t = i*2*Math.PI/mPrecision;
        return (mLemniscateParamY * Math.sin(t) * Math.cos(t)) / (1 + Math.pow(Math.sin(t), 2));
    }

    public double getGraphX(int i){
        // function is repeating every 2π and is defined from [0, 2π] so this is putting i∈[0, mPrecision) points between these two values
        double t = i*2*Math.PI/mPrecision;
        // trigonometric function for value of x for t∈[0, 2π)
        return (mLemniscateParamX * Math.cos(t)) / (1 + Math.pow(Math.sin(t), 2));
    }
}
