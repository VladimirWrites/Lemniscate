package com.vlad1m1r.lemniscate.base.models

import android.os.Parcel
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.lemniscate.testutils.isEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LineLengthParcelableTest {

    private lateinit var lineLength: LineLength

    @Before
    fun setUp() {
        lineLength = LineLength().apply {
            lineMinLength = 0.24f
            lineMaxLength = 0.83f
        }
    }

    @Test
    fun parcelable() {
        val parcel = Parcel.obtain()
        lineLength.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val copy = LineLength(parcel)
        parcel.recycle()

        assertThat(lineLength.isEqualTo(copy)).isTrue()
    }
}
