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

package com.vlad1m1r.lemniscate.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import me.relex.circleindicator.CircleIndicator



class MainActivity : AppCompatActivity(), FragmentCurve.OnViewCreated {
    private val NUM_PAGES = 11

    private lateinit var fragmentSettings: FragmentSettings
    private lateinit var pager: ViewPager

    private lateinit var pagerAdapter: CurvesPagerAdapter
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        fragmentSettings = supportFragmentManager.findFragmentById(R.id.fragment_settings) as FragmentSettings
        pager = findViewById(R.id.viewPager)
        pagerAdapter = CurvesPagerAdapter(supportFragmentManager)
        val indicator = findViewById<CircleIndicator>(R.id.indicator)
        pager.adapter = pagerAdapter
        indicator.setViewPager(pager)

    }

    override fun onViewShown(position: Int, baseCurveProgressView: BaseCurveProgressView?) {
        if (pager.currentItem == position) fragmentSettings.setBaseCurveProgressView(baseCurveProgressView!!)
    }

    override fun onViewPrepared(position: Int, baseCurveProgressView: BaseCurveProgressView?) {
        if (pager.currentItem == position)
            fragmentSettings.setBaseCurveProgressView(baseCurveProgressView!!)
        else
            fragmentSettings.applySettings(baseCurveProgressView!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_github -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_github)))
                startActivity(browserIntent)
                return true
            }
            R.id.action_presentation -> {
                startActivity(Intent(this, PresentationActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class CurvesPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return FragmentCurve.getInstance(position)
        }

        override fun getCount(): Int {
            return NUM_PAGES
        }
    }
}
