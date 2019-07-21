package com.vlad1m1r.lemniscate.base

import android.graphics.Color
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.lemniscate.BernoullisProgressView
import com.vlad1m1r.lemniscate.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class BaseProgressViewAttributesTest {

    val context = RuntimeEnvironment.application.applicationContext

    val atributeSet =  Robolectric.buildAttributeSet()
            .addAttribute(R.attr.maxLineLength, "0.81")
            .addAttribute(R.attr.minLineLength, "0.23")
            .addAttribute(R.attr.lineColor, "#000000")
            .addAttribute(R.attr.hasHole, "true")
            .addAttribute(R.attr.strokeWidth, "33px")
            .addAttribute(R.attr.precision, "111")
            .addAttribute(R.attr.duration, "999")
            .build()

    @Test
    fun constructorWithAttributeSet() {
        val bernoullisProgressView = BernoullisProgressView(context, atributeSet)
        assertThat(bernoullisProgressView.lineMaxLength).isEqualTo(0.81f)
        assertThat(bernoullisProgressView.lineMinLength).isEqualTo(0.23f)

        assertThat(bernoullisProgressView.color).isEqualTo(Color.BLACK)
        assertThat(bernoullisProgressView.hasHole).isTrue()
        assertThat(bernoullisProgressView.strokeWidth).isEqualTo(33f)
        assertThat(bernoullisProgressView.precision).isEqualTo(111)

        assertThat(bernoullisProgressView.duration).isEqualTo(999)
    }
}
