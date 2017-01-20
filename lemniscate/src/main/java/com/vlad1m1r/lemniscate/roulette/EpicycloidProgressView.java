package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;

/**
 * Created by vladimirjovanovic on 1/19/17.
 */

public class EpicycloidProgressView extends BaseCurveProgressView {

    private float a = 4f;
    private static final int b = 1;

    private int numberOfCycles = 1;

    public EpicycloidProgressView(Context context) {
        super(context);
    }

    public EpicycloidProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EpicycloidProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(int i) {
        //y = (a + b) sin(t) - b sin(((a+b)/b + 1)t)
        double t = i*numberOfCycles*2*Math.PI/mPrecision;
        return mLemniscateParamY/(2*(a+b)) * ((a+b)*Math.sin(t) - b*Math.sin(((a+b)/b + 1)*t));
    }

    @Override
    public double getGraphX(int i) {
        //x = (a + b) cos(t) - b cos((a/b + 1)t),
        double t = i*numberOfCycles*2*Math.PI/mPrecision;
        return mLemniscateParamX/(2*(a+b)) * ((a+b)*Math.cos(t) - b*Math.cos(((a+b)/b + 1)*t));
    }
}
