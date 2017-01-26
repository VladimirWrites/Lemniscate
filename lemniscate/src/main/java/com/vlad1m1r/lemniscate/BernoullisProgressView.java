package com.vlad1m1r.lemniscate;

import android.content.Context;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;

/**
 * Created by vladimirjovanovic on 1/18/17.
 */

public class BernoullisProgressView extends BaseCurveProgressView {

    public BernoullisProgressView(Context context) {
        super(context);
    }

    public BernoullisProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BernoullisProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public double getGraphY(double t){
        return (mLemniscateParamY * Math.sin(t) * Math.cos(t)) / (1 + Math.pow(Math.sin(t), 2));
    }

    public double getGraphX(double t){
        // trigonometric function for value of x for t∈[0, 2π)
        return (mLemniscateParamX * Math.cos(t)) / (1 + Math.pow(Math.sin(t), 2));
    }
}
