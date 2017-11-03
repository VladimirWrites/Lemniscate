/*
 * Copyright 2016 Vladimir Jovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import com.vlad1m1r.lemniscate.BernoullisBowProgressView;
import com.vlad1m1r.lemniscate.BernoullisProgressView;
import com.vlad1m1r.lemniscate.BernoullisSharpProgressView;
import com.vlad1m1r.lemniscate.GeronosProgressView;
import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.funny.CannabisProgressView;
import com.vlad1m1r.lemniscate.funny.HeartProgressView;
import com.vlad1m1r.lemniscate.other.XProgressView;
import com.vlad1m1r.lemniscate.roulette.EpitrochoidProgressView;
import com.vlad1m1r.lemniscate.roulette.HypotrochoidProgressView;
import com.vlad1m1r.lemniscate.scribble.RoundScribbleProgressView;
import com.vlad1m1r.lemniscate.scribble.ScribbleProgressView;

public class FragmentCurve  extends Fragment {

    private static final String KEY_POSITION = "position";

    public interface OnViewCreated {
        void onViewShown(int position, BaseCurveProgressView baseCurveProgressView);
        void onViewPrepared(int position, BaseCurveProgressView baseCurveProgressView);
    }

    private OnViewCreated listener;

    private BaseCurveProgressView baseCurveProgressView;

    private TextView curveName;
    private LinearLayout layoutViewHolder;

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

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_POSITION))
            mPosition = savedInstanceState.getInt(KEY_POSITION);

        if(baseCurveProgressView == null) {
            baseCurveProgressView = getViewForPosition(mPosition);
            baseCurveProgressView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_curve, container, false);

        curveName = root.findViewById(R.id.textCurveName);
        layoutViewHolder = root.findViewById(R.id.layoutViewHolder);

        if(baseCurveProgressView.getParent() != null) {
            ((ViewGroup) baseCurveProgressView.getParent()).removeView(baseCurveProgressView);
        }
        layoutViewHolder.addView(baseCurveProgressView);

        curveName.setText(baseCurveProgressView.getClass().getSimpleName());

        return root;
    }

    private BaseCurveProgressView getViewForPosition(int position) {
        switch (position) {
            case 0: return new BernoullisProgressView(getContext());
            case 1: return new GeronosProgressView(getContext());
            case 2: return new BernoullisBowProgressView(getContext());
            case 3: return new BernoullisSharpProgressView(getContext());

            case 4: return new EpitrochoidProgressView(getContext());
            case 5: return new HypotrochoidProgressView(getContext());

            case 6: return new XProgressView(getContext());

            case 7: return new RoundScribbleProgressView(getContext());
            case 8: return new ScribbleProgressView(getContext());

            case 9: return new CannabisProgressView(getContext());
            case 10: return new HeartProgressView(getContext());
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
        if(listener != null) listener.onViewPrepared(mPosition, baseCurveProgressView);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(listener != null) listener.onViewShown(mPosition, baseCurveProgressView);
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_POSITION, mPosition);
        super.onSaveInstanceState(outState);
    }
}
