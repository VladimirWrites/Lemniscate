package com.vlad1m1r.lemniscate.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.vlad1m1r.lemniscate.BaseCurveProgressView;
import com.vlad1m1r.lemniscate.BernoullisProgressView;
import com.vlad1m1r.lemniscate.EpicycloidProgressView;
import com.vlad1m1r.lemniscate.GeronosProgressView;
import com.vlad1m1r.lemniscate.HeartsProgressView;
import com.vlad1m1r.lemniscate.HypocycloidProgressView;
import com.vlad1m1r.lemniscate.HypotrochoidProgressView;

import static com.vlad1m1r.lemniscate.sample.R.id.viewPager;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 6;

    private FragmentSettings mFragmentSettings;
    private ViewPager mPager;

    private CurvesPagerAdapter mPagerAdapter;

    private BaseCurveProgressView curve1, curve2, curve3, curve4, curve5, curve6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curve1 = new BernoullisProgressView(this);
        curve2 = new GeronosProgressView(this);
        curve3 = new HeartsProgressView(this);
        curve4 = new EpicycloidProgressView(this);
        curve5 = new HypocycloidProgressView(this);
        curve6 = new HypotrochoidProgressView(this);

        mFragmentSettings = (FragmentSettings) getSupportFragmentManager().findFragmentById(R.id.fragment_settings);
        mFragmentSettings.setBaseCurveProgressView(curve1);
        mPager = (ViewPager) findViewById(viewPager);
        mPagerAdapter = new CurvesPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragmentSettings.setBaseCurveProgressView(getCurveForPosition(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class CurvesPagerAdapter extends FragmentStatePagerAdapter {
        public CurvesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseCurveProgressView baseCurveProgressView = getCurveForPosition(position);
            baseCurveProgressView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            return FragmentCurve.getInstance(baseCurveProgressView);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private BaseCurveProgressView getCurveForPosition(int position){
        switch (position) {
            case 0: return curve1;
            case 1: return curve2;
            case 2: return curve3;
            case 3: return curve4;
            case 4: return curve5;
            case 5: return curve6;
            default: return curve1;
        }
    }
}
