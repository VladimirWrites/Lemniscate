package com.vlad1m1r.lemniscate.roulette.scribble

import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
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
import org.robolectric.annotation.Config
import kotlin.math.PI

class RoundScribbleProgressViewTest {

    private val view = mock<RoundScribbleProgressView>()

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
        assertThat(view.getGraphY(0.0f)).isWithin(DELTA).of(40.0f)
        assertThat(view.getGraphY(0.1f)).isWithin(DELTA).of(35.905983f)
        assertThat(view.getGraphY(0.5f)).isWithin(DELTA).of(26.010328f)
        assertThat(view.getGraphY(1.0f)).isWithin(DELTA).of(29.180117f)
        assertThat(view.getGraphY(PI.toFloat())).isWithin(DELTA).of(-40.0f)
        assertThat(view.getGraphY(2.0f)).isWithin(DELTA).of(-26.539455f)
        assertThat(view.getGraphY(2 * PI.toFloat())).isWithin(DELTA).of(40.0f)
    }

    @Test
    fun isPeriodic() {
        view.isPeriodic(2 * PI.toFloat())
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class RoundScribbleProgressViewHasHoleTest {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val view = RoundScribbleProgressView(context)

    @Test
    fun hasHoleDisabled() {
        view.hasHole = true
        assertThat(view.hasHole).isFalse()
    }
}
