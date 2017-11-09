package com.vlad1m1r.lemniscate.base.models

import android.graphics.Paint
import com.vlad1m1r.lemniscate.TestConstants
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurveSettingsTest {

    private lateinit var curveSettings: CurveSettings

    @Mock
    lateinit var paint: Paint

    @Before
    fun setUp() {
        val lineLength = LineLength()
        curveSettings = CurveSettings(paint, lineLength)
    }

    @Test
    fun setStrokeWidth() {
        curveSettings.strokeWidth = 10f
        assertEquals(10.0f, curveSettings.strokeWidth, TestConstants.DELTA)
        verify<Paint>(paint).strokeWidth = 10f
    }

    @Test(expected = IllegalArgumentException::class)
    fun setStrokeWidthException() {
        curveSettings.strokeWidth = -1f
    }

    @Test
    fun setColor() {
        curveSettings.color = 123
        assertEquals(123, curveSettings.color.toLong())
        verify<Paint>(paint).color = 123
    }
}