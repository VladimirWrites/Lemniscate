package com.vlad1m1r.lemniscate.roulette

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.*
import com.vlad1m1r.lemniscate.sample.lemniscate.R
import com.vlad1m1r.lemniscate.testutils.TestLayoutInflater
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class BaseRouletteProgressViewAttributesTest {

    val context = RuntimeEnvironment.application.applicationContext
    val typedArray = mock<TypedArray>()
    val atributeSet = mock<AttributeSet>()

    val contextSpy = spy(context)

    @Before
    fun setUp() {

        val layoutInflater = TestLayoutInflater(contextSpy)
        whenever(contextSpy.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .thenReturn(layoutInflater)

        whenever(typedArray.getFloat(eq(R.styleable.RouletteCurveProgressView_radiusFixed), any())).thenReturn(34f)
        whenever(typedArray.getFloat(eq(R.styleable.RouletteCurveProgressView_radiusMoving), any())).thenReturn(23f)
        whenever(typedArray.getFloat(eq(R.styleable.RouletteCurveProgressView_numberOfCycles), any())).thenReturn(43f)
        whenever(typedArray.getFloat(eq(R.styleable.RouletteCurveProgressView_distanceFromCenter), any())).thenReturn(31f)

        doReturn(typedArray).whenever(contextSpy).obtainStyledAttributes(atributeSet, R.styleable.RouletteCurveProgressView, 0, 0)
    }

    @Test
    fun constructorWithAttributeSet() {
        val epitrochoidProgressView = EpitrochoidProgressView(contextSpy, atributeSet)
        assertThat(epitrochoidProgressView.radiusFixed).isEqualTo(34f)
        assertThat(epitrochoidProgressView.radiusMoving).isEqualTo(23f)
        assertThat(epitrochoidProgressView.numberOfCycles).isEqualTo(43f)
        assertThat(epitrochoidProgressView.distanceFromCenter).isEqualTo(31f)
    }
}