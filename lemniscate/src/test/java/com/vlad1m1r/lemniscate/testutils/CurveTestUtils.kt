package com.vlad1m1r.lemniscate.testutils

import com.google.common.truth.Truth
import org.mockito.kotlin.*
import com.vlad1m1r.lemniscate.base.BaseCurveProgressView
import com.vlad1m1r.lemniscate.roulette.BaseRouletteProgressView

fun BaseCurveProgressView.isPeriodic(period: Float) {
    for (i in 1..10) {
        val random = Math.random().toFloat()
        Truth.assertThat(getGraphX(random)).isWithin(TestConstants.DELTA).of(getGraphX(random + period))
        Truth.assertThat(getGraphY(random)).isWithin(TestConstants.DELTA).of(getGraphY(random + period))
    }
}

fun BaseRouletteProgressView.setupDefaultMock() {
    doCallRealMethod().whenever(this).getGraphX(any())
    doCallRealMethod().whenever(this).getGraphY(any())

    whenever(this.size).thenReturn(100f)
    whenever(this.radiusFixed).thenReturn(3f)
    whenever(this.radiusMoving).thenReturn(1f)
    whenever(this.distanceFromCenter).thenReturn(1f)
}

fun BaseCurveProgressView.setupDefaultMock() {
    doCallRealMethod().whenever(this).getGraphX(any())
    doCallRealMethod().whenever(this).getGraphY(any())
    whenever(this.size).thenReturn(100f)
}
