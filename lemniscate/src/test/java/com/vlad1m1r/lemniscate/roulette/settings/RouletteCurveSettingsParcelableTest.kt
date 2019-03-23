package com.vlad1m1r.lemniscate.roulette.settings

import android.os.Parcel
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.lemniscate.testutils.isEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RouletteCurveSettingsParcelableTest {

    private lateinit var rouletteCurveSettings: RouletteCurveSettings

    @Before
    fun setUp() {
        rouletteCurveSettings = RouletteCurveSettings()
        rouletteCurveSettings.distanceFromCenter = 0.24f
        rouletteCurveSettings.numberOfCycles = 0.83f
        rouletteCurveSettings.radiusFixed = 1.41f
        rouletteCurveSettings.radiusMoving = 3.25f
    }

    @Test
    fun parcelable() {
        val parcel = Parcel.obtain()
        rouletteCurveSettings.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val copy = RouletteCurveSettings(parcel)
        parcel.recycle()

        assertThat(rouletteCurveSettings.isEqualTo(copy)).isTrue()
    }
}
