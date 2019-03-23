package com.vlad1m1r.lemniscate.roulette

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doCallRealMethod
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.lemniscate.testutils.TestConstants.DELTA
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.math.PI

class HypotrochoidProgressViewTest {
    private val view = mock<HypotrochoidProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
        doCallRealMethod().whenever(view).radiusDiff
        doCallRealMethod().whenever(view).sizeFactor
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(DELTA).of(16.666666f)
        assertThat(view.getGraphX(0.1f)).isWithin(DELTA).of(16.832361f)
        assertThat(view.getGraphX(0.5f)).isWithin(DELTA).of(20.247713f)
        assertThat(view.getGraphX(1.0f)).isWithin(DELTA).of(24.945856f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(DELTA).of(-50.0f)
        assertThat(view.getGraphX(2.0f)).isWithin(DELTA).of(-2.9775007f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(DELTA).of(16.666666f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(DELTA).of(0.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(DELTA).of(6.638936f)
        assertThat(view.getGraphY(0.5f)).isWithin(DELTA).of(30.005367f)
        assertThat(view.getGraphY(1.0f)).isWithin(DELTA).of(43.203987f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(DELTA).of(0.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(DELTA).of(17.696539f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(DELTA).of(0.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}

@RunWith(RobolectricTestRunner::class)
class HypotrochoidProgressViewHasHoleTest {

    val context = RuntimeEnvironment.application.applicationContext
    private val view = HypotrochoidProgressView(context)

    @Test
    fun hasHoleDisabled() {
        view.hasHole = true
        assertThat(view.hasHole).isFalse()
    }
}
