package com.vlad1m1r.lemniscate.base.models

import android.graphics.Path
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import org.junit.Test
import org.mockito.Mockito.inOrder

class DrawStateTest {

    val path = mock<Path>()
    val drawState = DrawState(path)

    @Test
    fun addPairOfPoints() {
        val start = Point(0f,1f, 2f, 100f)
        val end = Point(2f, 3f, 2f, 100f)
        drawState.addPairOfPointsToPath(start, end)
        verify(path).moveTo(start.x, start.y)
        verify(path).quadTo(start.x, start.y, end.x, end.y)
        verifyNoMoreInteractions(path)
    }

    @Test
    fun addStartPoints() {
        val start = Point(0f,1f, 2f, 100f)
        drawState.addPairOfPointsToPath(start, null)
        verify(path).moveTo(start.x, start.y)
        verify(path).lineTo(start.x, start.y)
        verifyNoMoreInteractions(path)
    }

    @Test
    fun addEndPoints() {
        val end = Point(2f, 3f, 2f, 100f)
        drawState.addPairOfPointsToPath(null, end)
        verify(path).moveTo(end.x, end.y)
        verifyNoMoreInteractions(path)
    }

    @Test
    fun addPointsToPathWhenListEmpty() {
        val curveSettings = mock<CurveSettings>()
        val viewSize = mock<ViewSize>()
        drawState.addPointsToPath(emptyList(), curveSettings, viewSize)
        verify(path).reset()
        verifyNoMoreInteractions(path)
    }

    @Test
    fun addPointsToPathWhenListHasPointsOutOfHole() {
        val list = listOf(
                Point(5f, 0f, 1f, 100f),
                Point(3f, 0f, 1f, 100f)
        )
        val curveSettings = mock<CurveSettings>()
        val viewSize = mock<ViewSize>()
        whenever(curveSettings.hasHole).thenReturn(false)
        whenever(curveSettings.strokeWidth).thenReturn(1f)

        val drawStateSpy = spy(drawState)
        drawStateSpy.addPointsToPath(list, curveSettings, viewSize)
        verify(drawStateSpy).addPairOfPointsToPath(list[0], list[1])
        verify(drawStateSpy).addPairOfPointsToPath(list[1], null)
    }

    @Test
    fun addPointsToPathWhenListHasPointsInHole() {
        val list = listOf(
                Point(5f, 0f, 100f, 10f),
                Point(3f, 0f, 100f, 10f)
        )
        val curveSettings = mock<CurveSettings>()
        val viewSize = mock<ViewSize>()
        whenever(curveSettings.hasHole).thenReturn(true)
        whenever(curveSettings.strokeWidth).thenReturn(100f)

        val drawStateSpy = spy(drawState)
        drawStateSpy.addPointsToPath(list, curveSettings, viewSize)
        verify(drawStateSpy).addPairOfPointsToPath(null, null)
    }

    @Test
    fun recalculateLineLength() {
        val drawStateSpy = spy(drawState)
        val inOrder = inOrder(drawStateSpy)
        val lineLength = LineLength()
        drawStateSpy.recalculateLineLength(lineLength)
        inOrder.verify(drawStateSpy).keepLineLengthInsideLimits(lineLength)
        inOrder.verify(drawStateSpy).calculateNewCurrentLineLength(lineLength)
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun recalculateLineLengthWhenMinGreaterThanMax() {
        val lineLength = LineLength()
        lineLength.lineMinLength = 0.8f
        lineLength.lineMaxLength = 0.6f
        drawState.recalculateLineLength(lineLength)
        drawState.currentLineLength = lineLength.lineMaxLength
    }

    @Test
    fun keepLineLengthInsideLimitsWhenCurrentLessThanMin() {
        val lineLength = LineLength()
        lineLength.lineMinLength = 0.5f
        drawState.currentLineLength = 0.4f
        drawState.keepLineLengthInsideLimits(lineLength)
        assertThat(drawState.currentLineLength).isEqualTo(lineLength.lineMinLength)
    }

    @Test
    fun keepLineLengthInsideLimitsWhenCurrentGreaterThanMax() {
        val lineLength = LineLength()
        lineLength.lineMaxLength = 0.5f
        drawState.currentLineLength = 0.7f
        drawState.keepLineLengthInsideLimits(lineLength)
        assertThat(drawState.currentLineLength).isEqualTo(lineLength.lineMaxLength)
    }

    @Test
    fun calculateNewCurrentLineLengthWhenLessThanMaxAndExpanding() {
        val lineLength = LineLength()
        lineLength.lineMaxLength = 0.7f
        drawState.isExpanding = true
        drawState.currentLineLength = 0.5f
        drawState.calculateNewCurrentLineLength(lineLength)
        assertThat(drawState.currentLineLength).isEqualTo(0.501f)
    }

    @Test
    fun calculateNewCurrentLineLengthWhenGreaterThanMinAndNotExpanding() {
        val lineLength = LineLength()
        lineLength.lineMinLength = 0.2f
        drawState.isExpanding = false
        drawState.currentLineLength = 0.5f
        drawState.calculateNewCurrentLineLength(lineLength)
        assertThat(drawState.currentLineLength).isEqualTo(0.499f)
    }

    @Test
    fun calculateNewCurrentLineLengthWhenEqualMaxAndExpanding() {
        val lineLength = LineLength()
        drawState.currentLineLength = lineLength.lineMaxLength
        drawState.isExpanding = true
        drawState.calculateNewCurrentLineLength(lineLength)
        assertThat(drawState.currentLineLength).isEqualTo(lineLength.lineMaxLength)
        assertThat(drawState.isExpanding).isFalse()
    }

    @Test
    fun calculateNewCurrentLineLengthWhenEqualMinAndNotExpanding() {
        val lineLength = LineLength()
        drawState.currentLineLength = lineLength.lineMinLength
        drawState.isExpanding = false
        drawState.calculateNewCurrentLineLength(lineLength)
        assertThat(drawState.currentLineLength).isEqualTo(lineLength.lineMinLength)
        assertThat(drawState.isExpanding).isTrue()
    }

    @Test(expected = IllegalArgumentException::class)
    fun calculateNewCurrentLineLengthIsNotInsideLimits() {
        val lineLength = LineLength()
        drawState.currentLineLength = lineLength.lineMaxLength + 1
        drawState.calculateNewCurrentLineLength(lineLength)
    }

    @Test
    fun resetPath() {
        drawState.resetPath()
        verify(path).reset()
    }

}
