package com.vlad1m1r.lemniscate.base.models;

import com.vlad1m1r.lemniscate.TestConstants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {
    @Test
    public void testIfPointsAreTranslated() {
        Point point = new Point(0,30,30,270);
        assertEquals(135, point.x(), TestConstants.DELTA);
        assertEquals(159.5454559326172, point.y(), TestConstants.DELTA);
    }
}