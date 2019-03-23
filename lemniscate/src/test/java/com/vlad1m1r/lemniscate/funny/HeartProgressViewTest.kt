package com.vlad1m1r.lemniscate.funny

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.vlad1m1r.lemniscate.testutils.TestConstants
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.math.PI

class HeartProgressViewTest{

    private val view = mock<HeartProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphX(0.1f)).isWithin(TestConstants.DELTA).of(0.046824045f)
        assertThat(view.getGraphX(0.5f)).isWithin(TestConstants.DELTA).of(5.1856666f)
        assertThat(view.getGraphX(1.0f)).isWithin(TestConstants.DELTA).of(28.038736f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
        assertThat(view.getGraphX(2.0f)).isWithin(TestConstants.DELTA).of(35.38009f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(0.0f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(TestConstants.DELTA).of(-14.705882f)
        assertThat(view.getGraphY(0.1f)).isWithin(TestConstants.DELTA).of(-15.302903f)
        assertThat(view.getGraphY(0.5f)).isWithin(TestConstants.DELTA).of(-26.416864f)
        assertThat(view.getGraphY(1.0f)).isWithin(TestConstants.DELTA).of(-34.52439f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(TestConstants.DELTA).of(50.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(TestConstants.DELTA).of(11.51921f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(TestConstants.DELTA).of(-14.705882f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}

@RunWith(RobolectricTestRunner::class)
class HeartProgressViewHasHoleTest {

    val context = RuntimeEnvironment.application.applicationContext
    private val view = HeartProgressView(context)

    @Test
    fun hasHoleDisabled() {
        view.hasHole = true
        assertThat(view.hasHole).isFalse()
    }
}
