package com.vlad1m1r.lemniscate

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import com.vlad1m1r.lemniscate.testutils.TestConstants
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import kotlin.math.PI


class BernoullisSharpProgressViewTest {

    private val view = mock<BernoullisSharpProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(TestConstants.DELTA).of(50.0f)
        assertThat(view.getGraphX(0.1f)).isWithin(TestConstants.DELTA).of(49.99937f)
        assertThat(view.getGraphX(0.5f)).isWithin(TestConstants.DELTA).of(49.576702f)
        assertThat(view.getGraphX(1.0f)).isWithin(TestConstants.DELTA).of(41.821438f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(TestConstants.DELTA).of(-50.0f)
        assertThat(view.getGraphX(2.0f)).isWithin(TestConstants.DELTA).of(-35.471752f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(50.0f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(TestConstants.DELTA).of(4.991608f)
        assertThat(view.getGraphY(0.5f)).isWithin(TestConstants.DELTA).of(23.768335f)
        assertThat(view.getGraphY(1.0f)).isWithin(TestConstants.DELTA).of(35.191525f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(TestConstants.DELTA).of(-32.25437f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}