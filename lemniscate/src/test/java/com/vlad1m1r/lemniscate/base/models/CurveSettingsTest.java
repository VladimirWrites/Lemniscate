package com.vlad1m1r.lemniscate.base.models;

import android.graphics.Paint;

import com.vlad1m1r.lemniscate.TestConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CurveSettingsTest {

    private CurveSettings curveSettings;

    @Mock
    Paint paint;

    @Before
    public void setUp() {
        curveSettings = new CurveSettings(paint, null);
    }

    @Test
    public void setStrokeWidth() {
        curveSettings.setStrokeWidth(10);
        assertEquals(10, curveSettings.getStrokeWidth(), TestConstants.DELTA);
        verify(paint).setStrokeWidth(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setStrokeWidthException() throws Exception {
        curveSettings.setStrokeWidth(-1);
    }

    @Test
    public void setColor() {
        curveSettings.setColor(123);
        assertEquals(123, curveSettings.getColor());
        verify(paint).setColor(123);
    }
}