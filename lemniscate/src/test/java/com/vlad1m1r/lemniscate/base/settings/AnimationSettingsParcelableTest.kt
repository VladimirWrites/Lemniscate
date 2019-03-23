package com.vlad1m1r.lemniscate.base.settings

import android.os.Parcel
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.lemniscate.testutils.isEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AnimationSettingsParcelableTest {

    private lateinit var animationSettings: AnimationSettings

    @Before
    fun setUp() {
        animationSettings = AnimationSettings(123, 987)
    }

    @Test
    fun parcelable() {
        val parcel = Parcel.obtain()
        animationSettings.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val copy = AnimationSettings(parcel)
        parcel.recycle()

        assertThat(animationSettings.isEqualTo(copy)).isTrue()
    }
}
