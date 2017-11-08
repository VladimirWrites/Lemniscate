package com.vlad1m1r.lemniscate.base.models

import com.vlad1m1r.lemniscate.TestConstants
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LineLengthTest {

    private lateinit var lineLength: LineLength

    @Before
    fun setUp() {
        lineLength = LineLength()
    }

    @Test
    fun getLineMaxLength() {
        lineLength.lineMaxLength = 0.9f
        assertEquals(0.9f, lineLength.lineMaxLength, TestConstants.DELTA)

    }

    @Test(expected = IllegalArgumentException::class)
    fun getLineMaxLengthException() {
        lineLength.lineMaxLength = 1.1f
    }

    @Test
    fun setLineMinLength() {
        lineLength.lineMinLength = 0.1f
        assertEquals(0.1f, lineLength.lineMinLength, TestConstants.DELTA)
    }

    @Test(expected = IllegalArgumentException::class)
    fun setLineMinLengthException() {
        lineLength.lineMaxLength = -1.1f
    }
}