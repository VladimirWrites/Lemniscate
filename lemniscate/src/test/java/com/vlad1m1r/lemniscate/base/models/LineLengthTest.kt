package com.vlad1m1r.lemniscate.base.models

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LineLengthTest {

    private val lineLength = LineLength()

    @Test
    fun getLineMaxLength() {
        lineLength.lineMaxLength = 0.9f
        assertThat(lineLength.lineMaxLength).isEqualTo(0.9f)
    }

    @Test(expected = IllegalArgumentException::class)
    fun setLineMaxLengthGreaterThan1ThrowsException() {
        lineLength.lineMaxLength = 1.1f
    }

    @Test(expected = IllegalArgumentException::class)
    fun setLineMaxLengthEqualTo0ThrowsException() {
        lineLength.lineMaxLength = 0.0f
    }

    @Test(expected = IllegalArgumentException::class)
    fun setLineMaxLengthLestThan0ThrowsException() {
        lineLength.lineMaxLength = -1.0f
    }

    @Test
    fun setLineMinLength() {
        lineLength.lineMinLength = 0.1f
        assertThat(lineLength.lineMinLength).isEqualTo(0.1f)
    }

    @Test(expected = IllegalArgumentException::class)
    fun setLineMinLengthLestThan0ThrowsException() {
        lineLength.lineMinLength = -0.1f
    }

    @Test(expected = IllegalArgumentException::class)
    fun setLineMinLengthGreaterThan1ThrowsException() {
        lineLength.lineMinLength = 1.1f
    }

    @Test(expected = IllegalArgumentException::class)
    fun setLineMinLengthEqualTo0ThrowsException() {
        lineLength.lineMinLength = 0.0f
    }
}