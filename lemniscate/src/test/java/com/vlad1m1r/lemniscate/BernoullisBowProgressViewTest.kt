package com.vlad1m1r.lemniscate

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.vlad1m1r.lemniscate.testutils.TestConstants
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import kotlin.math.PI

class BernoullisBowProgressViewTest {

    private val view = mock<BernoullisBowProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(TestConstants.DELTA).of(37.5f)
        assertThat(view.getGraphX(0.1f)).isWithin(TestConstants.DELTA).of(37.873238f)
        assertThat(view.getGraphX(0.5f)).isWithin(TestConstants.DELTA).of(45.180264f)
        assertThat(view.getGraphX(1.0f)).isWithin(TestConstants.DELTA).of(39.53901f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(TestConstants.DELTA).of(-37.5f)
        assertThat(view.getGraphX(2.0f)).isWithin(TestConstants.DELTA).of(-31.049751f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(37.5f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(TestConstants.DELTA).of(3.7810147f)
        assertThat(view.getGraphY(0.5f)).isWithin(TestConstants.DELTA).of(21.660572f)
        assertThat(view.getGraphY(1.0f)).isWithin(TestConstants.DELTA).of(33.270927f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(TestConstants.DELTA).of(-28.233456f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}
