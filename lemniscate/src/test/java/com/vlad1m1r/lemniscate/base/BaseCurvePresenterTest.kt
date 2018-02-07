package com.vlad1m1r.lemniscate.base

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.*
import com.vlad1m1r.lemniscate.base.models.*
import com.vlad1m1r.lemniscate.base.settings.AnimationSettings
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import org.junit.Test

class BaseCurvePresenterTest {

    private val view = mock<IBaseCurveView>()
    private val curveSettings = mock<CurveSettings>()
    private val viewSize = mock<ViewSize>()
    private val animationSettings = mock<AnimationSettings>()
    private val drawState = mock<DrawState>()
    private val points = mock<Points>()

    val presenter = BaseCurvePresenter(view, curveSettings, viewSize, animationSettings, drawState, points)

    @Test
    fun updateStartingPointOnCurve() {
        whenever(curveSettings.lineLength).thenReturn(LineLength())
        presenter.updateStartingPointOnCurve(1)
        verify(animationSettings).startingPointOnCurve = 1
        verify(drawState).recalculateLineLength(curveSettings.lineLength)
        verify(view).invalidateProgressView()
    }

    @Test
    fun recreatePoints() {
        val presenterSpy = spy(presenter)
        presenterSpy.recreatePoints()
        verify(points).clear()
        verify(presenterSpy).createNewPoints()
        verify(presenterSpy).addPointsToPath()
    }

    @Test
    fun getLineLengthToDraw() {
        whenever(curveSettings.precision).thenReturn(99)
        whenever(drawState.currentLineLength).thenReturn(10.32f)
        assertThat(presenter.lineLengthToDraw).isEqualTo(1022)
    }

    @Test
    fun createNewPoints() {
    }

    @Test
    fun getStartingPointWhenPointsIsEmpty() {
        whenever(animationSettings.startingPointOnCurve).thenReturn(10)
        whenever(points.isEmpty).thenReturn(true)
        assertThat(presenter.getStartingPoint()).isEqualTo(10)
    }

    @Test
    fun getStartingPointWhenPointsIsNotEmpty() {
        whenever(points.isEmpty).thenReturn(false)
        assertThat(presenter.getStartingPoint()).isEqualTo(0)
    }

    @Test
    fun addPointsToPath() {
        whenever(points.getPoints()).thenReturn(ArrayList())
        presenter.addPointsToPath()
        verify(drawState).addPointsToPath(points.getPoints(), curveSettings, viewSize)
    }

    @Test
    fun addPointsToCurveWhenNotAllAddedStartO() {
        val presenterSpy = spy(presenter)
        val point = Point(1f,2f, 3f, 200f)
        doReturn(point).whenever(presenterSpy).getPoint(any())
        whenever(curveSettings.precision).thenReturn(20)
        val remainingPointsValue = 100
        val remainingPoints = presenterSpy.addPointsToCurve(0, remainingPointsValue)
        verify(points, times(curveSettings.precision)).addPoint(point)
        assertThat(remainingPoints).isEqualTo(remainingPointsValue - curveSettings.precision)
    }

    @Test
    fun addPointsToCurveWhenNotAllAddedStartNotO() {
        val presenterSpy = spy(presenter)
        val point = Point(1f,2f, 3f, 200f)
        doReturn(point).whenever(presenterSpy).getPoint(any())
        whenever(curveSettings.precision).thenReturn(20)
        val startValue = 10
        val remainingPointsValue = 100

        val remainingPoints = presenterSpy.addPointsToCurve(startValue, remainingPointsValue)

        verify(points, times(curveSettings.precision - startValue)).addPoint(point)
        assertThat(remainingPoints).isEqualTo(remainingPointsValue - curveSettings.precision + startValue)
    }

    @Test
    fun addPointsToCurveWhenAllAdded() {
        val presenterSpy = spy(presenter)
        val point = Point(1f,2f, 3f, 200f)
        doReturn(point).whenever(presenterSpy).getPoint(any())
        whenever(curveSettings.precision).thenReturn(200)
        val startValue = 10
        val remainingPointsValue = 100

        val remainingPoints = presenterSpy.addPointsToCurve(startValue, remainingPointsValue)

        verify(points, times(remainingPointsValue)).addPoint(point)
        assertThat(remainingPoints).isEqualTo(0)
    }

    @Test
    fun addPointsToCurve() {
    }

    @Test
    fun getPoint() {
        val presenterSpy = spy(presenter)
        whenever(presenterSpy.getT(1)).thenReturn(10.9f)
        presenterSpy.getPoint(1)
        verify(view).getGraphX(10.9f)
        verify(view).getGraphY(10.9f)
    }

    @Test
    fun getT() {
        whenever(curveSettings.precision).thenReturn(10)
        presenter.getT(1)
        verify(view).getT(1, curveSettings.precision)
    }
}