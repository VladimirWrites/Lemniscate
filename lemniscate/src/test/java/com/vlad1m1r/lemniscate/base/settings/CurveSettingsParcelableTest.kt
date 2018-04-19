package com.vlad1m1r.lemniscate.base.settings

import android.os.Parcel
import com.vlad1m1r.lemniscate.base.models.LineLength
import com.vlad1m1r.lemniscate.testutils.isEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CurveSettingsParcelableTest {

    private lateinit var curveSettings: CurveSettings

    @Before
    fun setUp() {
        curveSettings = CurveSettings().apply {
            color = 123
            hasHole = true
            lineLength = LineLength().apply {
                lineMaxLength = 0.85f
                lineMinLength = 0.26f
            }
            strokeWidth = 23.2f
            precision = 123
        }
    }

    @Test
    fun parcelable() {
        val parcel = Parcel.obtain()
        curveSettings.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val copy = CurveSettings(parcel)
        parcel.recycle()

        curveSettings.isEqualTo(copy)
    }
}
