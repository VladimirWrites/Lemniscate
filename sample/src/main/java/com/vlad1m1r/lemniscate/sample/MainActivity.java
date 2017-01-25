package com.vlad1m1r.lemniscate.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView;

import static com.vlad1m1r.lemniscate.sample.R.id.viewPager;

public class MainActivity extends AppCompatActivity implements FragmentCurve.OnViewCreated{

    private static final int NUM_PAGES = 6;

    private FragmentSettings mFragmentSettings;
    private ViewPager mPager;

    private CurvesPagerAdapter mPagerAdapter;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFragmentSettings = (FragmentSettings) getSupportFragmentManager().findFragmentById(R.id.fragment_settings);
        mPager = (ViewPager) findViewById(viewPager);
        mPagerAdapter = new CurvesPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onViewShown(int position, BaseCurveProgressView baseCurveProgressView) {
        if(mPager != null && mPager.getCurrentItem() == position) mFragmentSettings.setBaseCurveProgressView(baseCurveProgressView);
    }

    @Override
    public void onViewPrepared(int position, BaseCurveProgressView baseCurveProgressView) {
        if(mPager != null && mPager.getCurrentItem() == position) mFragmentSettings.setBaseCurveProgressView(baseCurveProgressView);
        else mFragmentSettings.applySettings(baseCurveProgressView);
    }

    private class CurvesPagerAdapter extends FragmentStatePagerAdapter {
        public CurvesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentCurve.getInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_github:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vlad1m1r990/Lemniscate"));
                startActivity(browserIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
