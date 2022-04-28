package com.vlad1m1r.lemniscate

import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.mock
import com.vlad1m1r.lemniscate.testutils.TestConstants
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import kotlin.math.PI

class GeronosProgressViewTest {

    private val view = mock<GeronosProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphX(0.1f)).isWithin(TestConstants.DELTA).of(4.991671f)
        assertThat(view.getGraphX(0.5f)).isWithin(TestConstants.DELTA).of(23.971277f)
        assertThat(view.getGraphX(1.0f)).isWithin(TestConstants.DELTA).of(42.073547f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphX(2.0f)).isWithin(TestConstants.DELTA).of(45.46487f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(TestConstants.DELTA).of(4.9667335f)
        assertThat(view.getGraphY(0.5f)).isWithin(TestConstants.DELTA).of(21.036774f)
        assertThat(view.getGraphY(1.0f)).isWithin(TestConstants.DELTA).of(22.732433f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(TestConstants.DELTA).of(-18.920063f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}
