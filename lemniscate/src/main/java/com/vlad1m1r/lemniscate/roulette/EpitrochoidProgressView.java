package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by vladimirjovanovic on 1/19/17.
 */

public class EpitrochoidProgressView extends BaseRouletteProgressView {

    // a = 5, b=3, d=5, numberOfCycles = 3 to get pentagram

    public EpitrochoidProgressView(Context context) {
        super(context);
    }

    public EpitrochoidProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EpitrochoidProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(int i) {
        //y = (a + b) sin(t) - d sin(((a+b)/b)*t)
        double t = i*numberOfCycles*2*Math.PI/mPrecision;
        return mLemniscateParamY/(2*(a+d+b))*((a+b)*Math.sin(t) + d*Math.sin(((a+b)/b)*t));
    }

    @Override
    public double getGraphX(int i) {
        //x = (a + b) cos(t) + b cos(((a+b)/b)*t),
        double t = i*numberOfCycles*2*Math.PI/mPrecision;
        return mLemniscateParamY/(2*(a+d+b))*((a+b)*Math.cos(t) - d*Math.cos(((a+b)/b)*t));
    }

    @Override
    public void setHasHole(boolean hasHole) {
        super.setHasHole(false);
    }
}
