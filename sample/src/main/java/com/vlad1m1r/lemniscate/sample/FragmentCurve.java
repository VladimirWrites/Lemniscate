package com.vlad1m1r.lemniscate.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vlad1m1r.lemniscate.BernoullisProgressView;
import com.vlad1m1r.lemniscate.GeronosProgressView;
import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.funny.CannabisProgressView;
import com.vlad1m1r.lemniscate.funny.HeartProgressView;
import com.vlad1m1r.lemniscate.roulette.EpicycloidProgressView;
import com.vlad1m1r.lemniscate.roulette.EpitrochoidProgressView;
import com.vlad1m1r.lemniscate.roulette.HypocycloidProgressView;
import com.vlad1m1r.lemniscate.roulette.HypotrochoidProgressView;

/**
 * Created by vladimirjovanovic on 1/19/17.
 */

public class FragmentCurve  extends Fragment {



    public interface OnViewCreated {
        void onViewShown(int position, BaseCurveProgressView baseCurveProgressView);
        void onViewPrepared(int position, BaseCurveProgressView baseCurveProgressView);
    }

    private OnViewCreated listener;

    private BaseCurveProgressView mBaseCurveProgressView;

    private TextView mCurveName;
    private LinearLayout mLayoutViewholder;

    private int mPosition;

    public static FragmentCurve getInstance(int position) {
        FragmentCurve fragmentCurve = new FragmentCurve();
        fragmentCurve.setPosition(position);
        fragmentCurve.setRetainInstance(true);
        return fragmentCurve;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mBaseCurveProgressView == null) {
            mBaseCurveProgressView = getViewForPosition(mPosition);
            mBaseCurveProgressView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_curve, container, false);

        mCurveName = (TextView) root.findViewById(R.id.textCurveName);
        mLayoutViewholder = (LinearLayout) root.findViewById(R.id.layoutViewHolder);

        if(mBaseCurveProgressView.getParent() != null) {
            ((ViewGroup) mBaseCurveProgressView.getParent()).removeView(mBaseCurveProgressView);
        }
        mLayoutViewholder.addView(mBaseCurveProgressView);

        mCurveName.setText(mBaseCurveProgressView.getClass().getSimpleName());

        return root;
    }

    private BaseCurveProgressView getViewForPosition(int position) {
        switch (position) {
            case 0: return new BernoullisProgressView(getContext());
            case 1: return new GeronosProgressView(getContext());

            case 2: return new EpicycloidProgressView(getContext());
            case 3: return new EpitrochoidProgressView(getContext());
            case 4: return new HypocycloidProgressView(getContext());
            case 5: return new HypotrochoidProgressView(getContext());

            case 6: return new CannabisProgressView(getContext());
            case 7: return new HeartProgressView(getContext());
            default: return new BernoullisProgressView(getContext());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnViewCreated) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listener != null) listener.onViewPrepared(mPosition, mBaseCurveProgressView);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(listener != null) listener.onViewShown(mPosition, mBaseCurveProgressView);
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }
}
