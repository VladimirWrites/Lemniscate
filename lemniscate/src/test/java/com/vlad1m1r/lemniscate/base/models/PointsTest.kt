package com.vlad1m1r.lemniscate.base.models

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PointsTest {

    val points = Points()
    val point  = Point(0f, 0f, 10f, 10f)

    @Test
    fun isEmpty() {
        assertThat(points.isEmpty).isTrue()
        points.addPoint(point)
        assertThat(points.isEmpty).isFalse()
    }

    @Test
    fun addPoint() {
        points.addPoint(point)
        assertThat(points.getPoints()).containsExactly(point)
    }

    @Test
    fun clear() {
        points.addPoint(point)
        points.clear()
        assertThat(points.isEmpty).isTrue()
    }

    @Test
    fun getPointsReturnsCopy() {
        val list = points.getPoints()
        list.add(point)
        assertThat(points.isEmpty).isTrue()
    }
}