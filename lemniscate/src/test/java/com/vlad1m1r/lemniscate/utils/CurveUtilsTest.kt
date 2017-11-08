package com.vlad1m1r.lemniscate.utils

import com.vlad1m1r.lemniscate.base.models.Point
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test

class CurveUtilsTest {

    @Test
    fun checkPointForHole() {
        val viewSize = 100f
        val strokeWidth = 10f
        val point = Point(5f, 0f, strokeWidth, viewSize)

        assertSame(CurveUtils.checkPointForHole(point, 1f, viewSize), point)
        assertNull(CurveUtils.checkPointForHole(point, 5f, viewSize))
        assertNull(CurveUtils.checkPointForHole(null, 1f, viewSize))
    }

}