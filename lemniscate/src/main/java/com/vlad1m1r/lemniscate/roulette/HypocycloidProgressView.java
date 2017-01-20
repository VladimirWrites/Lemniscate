package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by vladimirjovanovic on 1/19/17.
 */

public class HypocycloidProgressView extends BaseRouletteProgressView {

    // a = 2.5f, numberOfCycles = 2 to get pentagram

    public HypocycloidProgressView(Context context) {
        super(context);
    }

    public HypocycloidProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HypocycloidProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(int i) {
        //y = (a - b) sin(t) - b sin(((a-b)/b)*t)
        double t = i*numberOfCycles*2*Math.PI/mPrecision;
        return mLemniscateParamY/a*((a-b)*Math.sin(t) + b*Math.sin(((a-b)/b)*t));
    }

    @Override
    public double getGraphX(int i) {
        //x = (a - b) cos(t) + b cos(((a-b)/b)*t),
        double t = i*numberOfCycles*2*Math.PI/mPrecision;
        return mLemniscateParamY/a*((a-b)*Math.cos(t) - b*Math.cos(((a-b)/b)*t));
    }

    @Override
    public void setHasHole(boolean hasHole) {
        super.setHasHole(false);
    }
}
