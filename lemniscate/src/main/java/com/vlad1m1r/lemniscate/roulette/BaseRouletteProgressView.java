package com.vlad1m1r.lemniscate.roulette;

import android.content.Context;
import android.util.AttributeSet;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;

/**
 * Created by vladimirjovanovic on 1/20/17.
 */

public abstract class BaseRouletteProgressView extends BaseCurveProgressView {

    protected float a = 5f;
    protected float b = 3f;
    protected float d = 5f;

    protected int numberOfCycles = 3;


    public BaseRouletteProgressView(Context context) {
        super(context);
    }

    public BaseRouletteProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRouletteProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public int getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(int numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }
}
