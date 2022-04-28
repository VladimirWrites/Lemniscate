package com.vlad1m1r.lemniscate.roulette

import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.*
import com.vlad1m1r.lemniscate.testutils.TestConstants.DELTA
import com.vlad1m1r.lemniscate.testutils.isPeriodic
import com.vlad1m1r.lemniscate.testutils.setupDefaultMock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.math.PI

class EpitrochoidProgressViewTest {

    private val view = mock<EpitrochoidProgressView>()

    @Before
    fun setUp() {
        view.setupDefaultMock()
        doCallRealMethod().whenever(view).radiusSum
        doCallRealMethod().whenever(view).sizeFactor
    }

    @Test
    fun getGraphX() {
        assertThat(view.getGraphX(0.0f)).isWithin(DELTA).of(30.0f)
        assertThat(view.getGraphX(0.1f)).isWithin(DELTA).of(30.589556f)
        assertThat(view.getGraphX(0.5f)).isWithin(DELTA).of(39.26477f)
        assertThat(view.getGraphX(1.0f)).isWithin(DELTA).of(28.14853f)
        assertThat(view.getGraphX(PI.toFloat())).isWithin(DELTA).of(-50.0f)
        assertThat(view.getGraphX(2.0f)).isWithin(DELTA).of(-15.190873f)
        assertThat(view.getGraphX(2 * PI.toFloat())).isWithin(DELTA).of(30.0f)
    }

    @Test
    fun getGraphY() {
        assertThat(view.getGraphY(0.0f)).isWithin(DELTA).of(0.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(DELTA).of(0.09915324f)
        assertThat(view.getGraphY(0.5f)).isWithin(DELTA).of(10.084047f)
        assertThat(view.getGraphY(1.0f)).isWithin(DELTA).of(41.226864f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(DELTA).of(9.797174E-15f)
        assertThat(view.getGraphY(2.0f)).isWithin(DELTA).of(26.478315f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(DELTA).of(0.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class EpitrochoidProgressViewHasHoleTest {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val view = EpitrochoidProgressView(context)

    @Test
    fun hasHoleDisabled() {
        view.hasHole = true
        assertThat(view.hasHole).isFalse()
    }
}
