package com.vlad1m1r.lemniscate.funny;

import android.content.Context;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;

/**
 * Created by vladimirjovanovic on 1/20/17.
 */

public class CannabisProgressView extends BaseCurveProgressView {

    public CannabisProgressView(Context context) {
        super(context);
    }

    public CannabisProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CannabisProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(double t) {
        return -mLemniscateParamY/3 * Math.sin(t) * (Math.sin(t) + 1) * (9/10f * Math.cos(8*t) + 1) * (1/10f * Math.cos(24*t) + 1) * (1/10f * Math.cos(200*t) + 9/10f) + mLemniscateParamY/2;
    }

    @Override
    public double getGraphX(double t) {
        return mLemniscateParamX/3 * (Math.sin(t) + 1) * Math.cos(t) * (9/10f * Math.cos(8*t) + 1) * (1/10f * Math.cos(24*t) + 1) * (1/10f * Math.cos(200*t) + 9/10f);
    }
}
