package com.vlad1m1r.lemniscate.base.models

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PointTest {
    @Test
    fun testIfPointsAreTranslated() {
        val point = Point(0.0f, 30.0f, 30.0f, 270.0f)
        assertThat(point.x).isEqualTo(135.0f)
        assertThat(point.y).isEqualTo(159.545455f)
    }
}