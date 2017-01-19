package com.vlad1m1r.lemniscate.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vlad1m1r.lemniscate.BaseCurveProgressView;

/**
 * Created by vladimirjovanovic on 1/19/17.
 */

public class FragmentCurve  extends Fragment {

    private BaseCurveProgressView mBaseCurveProgressView;

    public static FragmentCurve getInstance(BaseCurveProgressView baseCurveProgressView) {
        FragmentCurve fragmentCurve = new FragmentCurve();
        fragmentCurve.setBaseCurveProgressView(baseCurveProgressView);
        return fragmentCurve;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_curve, container, false);
        if(mBaseCurveProgressView != null) {
            if (mBaseCurveProgressView.getParent() != null) {
                ((ViewGroup) mBaseCurveProgressView.getParent()).removeView(mBaseCurveProgressView);
            }
            root.addView(mBaseCurveProgressView);
        }
        return root;
    }

    public BaseCurveProgressView getBaseCurveProgressView() {
        return mBaseCurveProgressView;
    }

    public void setBaseCurveProgressView(BaseCurveProgressView baseCurveProgressView) {
        mBaseCurveProgressView = baseCurveProgressView;
    }
}
