package com.vlad1m1r.lemniscate.funny

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import com.vlad1m1r.lemniscate.testutils.TestConstants
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.math.PI

class CannabisProgressViewTest {

    private val view = mock<CannabisProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(TestConstants.DELTA).of(34.833332f)
        assertThat(view.getGraphX(0.1f)).isWithin(TestConstants.DELTA).of(25.860207f)
        assertThat(view.getGraphX(0.5f)).isWithin(TestConstants.DELTA).of(9.527859f)
        assertThat(view.getGraphX(1.0f)).isWithin(TestConstants.DELTA).of(14.251956f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(TestConstants.DELTA).of(-34.83333f)
        assertThat(view.getGraphX(2.0f)).isWithin(TestConstants.DELTA).of(-1.4506966f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(34.833332f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(TestConstants.DELTA).of(25.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(TestConstants.DELTA).of(22.405325f)
        assertThat(view.getGraphY(0.5f)).isWithin(TestConstants.DELTA).of(19.794907f)
        assertThat(view.getGraphY(1.0f)).isWithin(TestConstants.DELTA).of(2.803894f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(TestConstants.DELTA).of(25.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(TestConstants.DELTA).of(21.83017f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(25.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }


}

@RunWith(RobolectricTestRunner::class)
class CannabisProgressViewHasHoleTest {

    val context = RuntimeEnvironment.application.applicationContext
    private val view = CannabisProgressView(context)

    @Test
    fun hasHoleDisabled() {
        view.hasHole = true
        assertThat(view.hasHole).isFalse()
    }
}