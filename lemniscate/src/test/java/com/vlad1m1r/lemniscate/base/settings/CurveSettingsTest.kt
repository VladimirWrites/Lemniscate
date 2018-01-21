package com.vlad1m1r.lemniscate.base.settings

import android.graphics.Paint
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import com.vlad1m1r.lemniscate.base.models.LineLength
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurveSettingsTest {

    private lateinit var curveSettings: CurveSettings

    val paint = mock<Paint>()

    @Before
    fun setUp() {
        val lineLength = LineLength()
        curveSettings = CurveSettings(paint, lineLength)
    }

    @Test
    fun setStrokeWidth() {
        curveSettings.strokeWidth = 10.0f
        assertThat(curveSettings.strokeWidth).isEqualTo(10.0f)
        verify<Paint>(paint).strokeWidth = 10f
    }

    @Test(expected = IllegalArgumentException::class)
    fun setStrokeWidthException() {
        curveSettings.strokeWidth = -1.0f
    }

    @Test
    fun setColor() {
        curveSettings.color = 123
        assertEquals(123, curveSettings.color.toLong())
        verify<Paint>(paint).color = 123
    }
}