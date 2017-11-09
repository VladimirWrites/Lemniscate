package com.vlad1m1r.lemniscate.base.models

import com.vlad1m1r.lemniscate.TestConstants
import org.junit.Assert.assertEquals
import org.junit.Test

class PointTest {
    @Test
    fun testIfPointsAreTranslated() {
        val point = Point(0f, 30f, 30f, 270f)
        assertEquals(135.0f, point.x, TestConstants.DELTA)
        assertEquals(159.545455f, point.y, TestConstants.DELTA)
    }
}