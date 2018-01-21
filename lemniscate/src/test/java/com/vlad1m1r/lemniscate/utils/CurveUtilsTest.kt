package com.vlad1m1r.lemniscate.utils

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.lemniscate.base.models.Point
import org.junit.Test

class CurveUtilsTest {

    @Test
    fun checkPointForHole() {
        val point = Point(5f, 0f, 100f, 10f)

        assertThat(CurveUtils.checkPointForHole(point, 0.2f, 10f)).isSameAs(point)
        assertThat(CurveUtils.checkPointForHole(point, 5.0f, 10f)).isNull()
        assertThat(CurveUtils.checkPointForHole(null, 0.2f, 10f)).isNull()
    }
}