package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by vladimirjovanovic on 1/19/17.
 */

public class HypotrochoidProgressView extends BaseRouletteProgressView {

    // mRadiusFixed = 5, mRadiusMoving=3, mDistanceFromCenter=5, numberOfCycles = 3 to get pentagram

    public HypotrochoidProgressView(Context context) {
        super(context);
    }

    public HypotrochoidProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HypotrochoidProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getGraphY(int i) {
        //y = (mRadiusFixed - mRadiusMoving) sin(t) - mRadiusMoving sin(((mRadiusFixed-mRadiusMoving)/mRadiusMoving)*t)
        double t = getT(i);
        return mLemniscateParamY/((mRadiusFixed + mDistanceFromCenter - mRadiusMoving))*((mRadiusFixed - mRadiusMoving)*Math.sin(t) + mDistanceFromCenter *Math.sin(((mRadiusFixed - mRadiusMoving)/ mRadiusMoving)*t));
    }

    @Override
    public double getGraphX(int i) {
        //x = (mRadiusFixed - mRadiusMoving) cos(t) + mRadiusMoving cos(((mRadiusFixed-mRadiusMoving)/mRadiusMoving)*t),
        double t = getT(i);
        return mLemniscateParamY/((mRadiusFixed + mDistanceFromCenter - mRadiusMoving))*((mRadiusFixed - mRadiusMoving)*Math.cos(t) - mDistanceFromCenter *Math.cos(((mRadiusFixed - mRadiusMoving)/ mRadiusMoving)*t));
    }

    @Override
    public void setHasHole(boolean hasHole) {
        super.setHasHole(false);
    }
}
