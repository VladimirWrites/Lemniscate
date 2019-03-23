package com.vlad1m1r.lemniscate

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.vlad1m1r.lemniscate.testutils.TestConstants
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import kotlin.math.PI

class BernoullisProgressViewTest {

    private val view = mock<BernoullisProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(TestConstants.DELTA).of(50.0f)
        assertThat(view.getGraphX(0.1f)).isWithin(TestConstants.DELTA).of(49.259254f)
        assertThat(view.getGraphX(0.5f)).isWithin(TestConstants.DELTA).of(35.67847f)
        assertThat(view.getGraphX(1.0f)).isWithin(TestConstants.DELTA).of(15.816132f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(TestConstants.DELTA).of(-50.0f)
        assertThat(view.getGraphX(2.0f)).isWithin(TestConstants.DELTA).of(-11.389914f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(50.0f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(TestConstants.DELTA).of(4.91772f)
        assertThat(view.getGraphY(0.5f)).isWithin(TestConstants.DELTA).of(17.10517f)
        assertThat(view.getGraphY(1.0f)).isWithin(TestConstants.DELTA).of(13.308815f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(TestConstants.DELTA).of(-10.356819f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}
