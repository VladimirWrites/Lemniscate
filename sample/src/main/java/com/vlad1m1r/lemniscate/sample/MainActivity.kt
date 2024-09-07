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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import me.relex.circleindicator.CircleIndicator

private const val NUM_PAGES = 11

class MainActivity : AppCompatActivity(), FragmentCurve.OnViewCreated {

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

        val rootView = findViewById<View>(R.id.root_view)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, systemInsets.top, 0, systemInsets.bottom)
            insets
        }
    }

    override fun onViewShown(position: Int, baseCurveProgressView: BaseCurveProgressView?) {
        if (pager.currentItem == position) {
            fragmentSettings.setBaseCurveProgressView(baseCurveProgressView!!)
        }
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

    private inner class CurvesPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm,  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return FragmentCurve.getInstance(position)
        }

        override fun getCount(): Int {
            return NUM_PAGES
        }
    }
}
