package com.vlad1m1r.lemniscate.base

import android.view.View
import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.*
import org.junit.Before
import org.junit.Test

class BaseCurveProgressViewTest {

    val baseCurve: BaseCurve = BaseCurve()

    val baseCurveProgressView = mock<BaseCurveProgressView>()

    @Before
    fun setUp() {
        doCallRealMethod().whenever(baseCurveProgressView).getMaxViewSquareSize(any(), any(), any(), any())
        doCallRealMethod().whenever(baseCurveProgressView).getViewDimension(any(), any(), any())
    }

    @Test
    fun getMaxViewSquareSize() {
        assertThat(baseCurveProgressView.getMaxViewSquareSize(100, 200, 30, 50)).isEqualTo(100-50)
        assertThat(baseCurveProgressView.getMaxViewSquareSize(220, 150, 30, 50)).isEqualTo(150-30)
    }

    @Test
    fun getViewDimension_whenViewSizeIsZero() {
        val defaultSize = 10f
        assertThat(baseCurveProgressView.getViewDimension(View.MeasureSpec.AT_MOST, 0f, defaultSize)).isEqualTo(defaultSize)
    }

    @Test
    fun getViewDimension_whenMeasureSpecIsExactly() {
        val viewSize = 10f
        assertThat(baseCurveProgressView.getViewDimension(View.MeasureSpec.EXACTLY, viewSize, 10f)).isEqualTo(viewSize)
    }

    @Test
    fun getViewDimension_whenMeasureSpecIsAtMost() {
        assertThat(baseCurveProgressView.getViewDimension(View.MeasureSpec.AT_MOST, 10f, 20f)).isEqualTo(10f)
        assertThat(baseCurveProgressView.getViewDimension(View.MeasureSpec.AT_MOST, 30f, 20f)).isEqualTo(20f)
    }

    @Test
    fun getViewDimension_whenMeasureSpecIsUnspecified() {
        val defaultSize = 10f
        assertThat(baseCurveProgressView.getViewDimension(View.MeasureSpec.UNSPECIFIED, 20f, defaultSize)).isEqualTo(defaultSize)
    }

    @Test
    fun getT() {
        assertThat(baseCurve.getT(0, 10)).isEqualTo(0.0f)
        assertThat(baseCurve.getT(1, 10)).isEqualTo(0.62831855f)
        assertThat(baseCurve.getT(10, 10)).isEqualTo(6.2831855f)
        assertThat(baseCurve.getT(99, 8)).isEqualTo(77.75442f)
        assertThat(baseCurve.getT(-1, 10)).isEqualTo(-0.62831855f)
        assertThat(baseCurve.getT(-10, 10)).isEqualTo(-6.2831855f)
    }

    inner class BaseCurve : IBaseCurveView {
        override fun getGraphX(t: Float): Float {
            return 0f
        }

        override fun getGraphY(t: Float): Float {
            return 0f
        }

        override fun invalidateProgressView() {}

        override fun requestProgressViewLayout() {}
    }
}
