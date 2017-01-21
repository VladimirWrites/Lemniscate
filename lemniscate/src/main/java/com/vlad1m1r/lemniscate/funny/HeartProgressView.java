package com.vlad1m1r.lemniscate.funny;

import android.content.Context;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;

/**
 * Created by vladimirjovanovic on 1/18/17.
 */

public class HeartProgressView extends BaseCurveProgressView {

    public HeartProgressView(Context context) {
        super(context);
    }

    public HeartProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeartProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(int i) {
        double t = getT(i);
        return -mLemniscateParamY/17 * (13 * Math.cos(t) - 5 * Math.cos(2*t) - 2 * Math.cos(3*t) - Math.cos(4*t));
    }

    @Override
    public double getGraphX(int i) {
        double t = getT(i);
        return mLemniscateParamX/17 * 16 *  Math.pow(Math.sin(t), 3);
    }
}
