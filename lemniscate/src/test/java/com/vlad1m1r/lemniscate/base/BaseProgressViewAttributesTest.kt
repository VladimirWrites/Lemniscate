package com.vlad1m1r.lemniscate.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.*
import com.vlad1m1r.lemniscate.BernoullisProgressView
import com.vlad1m1r.lemniscate.sample.lemniscate.R
import com.vlad1m1r.lemniscate.testutils.TestLayoutInflater
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class BaseProgressViewAttributesTest {

    val context = RuntimeEnvironment.application.applicationContext
    val typedArray = mock<TypedArray>()
    val atributeSet = mock<AttributeSet>()

    val contextSpy = spy(context)

    @Before
    fun setUp() {

        val layoutInflater = TestLayoutInflater(contextSpy)
        whenever(contextSpy.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .thenReturn(layoutInflater)

        whenever(typedArray.getFloat(eq(R.styleable.BaseCurveProgressView_maxLineLength), any())).thenReturn(0.81f)
        whenever(typedArray.getFloat(eq(R.styleable.BaseCurveProgressView_minLineLength), any())).thenReturn(0.23f)

        whenever(typedArray.getColor(eq(R.styleable.BaseCurveProgressView_lineColor), any())).thenReturn(123)
        whenever(typedArray.getBoolean(eq(R.styleable.BaseCurveProgressView_hasHole), any())).thenReturn(true)
        whenever(typedArray.getDimension(eq(R.styleable.BaseCurveProgressView_strokeWidth), any())).thenReturn(32.2f)
        whenever(typedArray.getInteger(eq(R.styleable.BaseCurveProgressView_precision), any())).thenReturn(111)

        whenever(typedArray.getInteger(eq(R.styleable.BaseCurveProgressView_duration), any())).thenReturn(999)

        doReturn(typedArray).whenever(contextSpy).obtainStyledAttributes(atributeSet, R.styleable.BaseCurveProgressView, 0, 0)
    }

    @Test
    fun constructorWithAttributeSet() {
        val bernoullisProgressView = BernoullisProgressView(contextSpy, atributeSet)
        assertThat(bernoullisProgressView.lineMaxLength).isEqualTo(0.81f)
        assertThat(bernoullisProgressView.lineMinLength).isEqualTo(0.23f)

        assertThat(bernoullisProgressView.color).isEqualTo(123)
        assertThat(bernoullisProgressView.hasHole).isTrue()
        assertThat(bernoullisProgressView.strokeWidth).isEqualTo(32.2f)
        assertThat(bernoullisProgressView.precision).isEqualTo(111)

        assertThat(bernoullisProgressView.duration).isEqualTo(999)
    }
}