/*
 * Copyright 2016 Vladimir Jovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vlad1m1r.lemniscate.base.settings

import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import com.vlad1m1r.lemniscate.base.models.LineLength

open class CurveSettings(val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.STROKE
    strokeCap = Paint.Cap.ROUND
}) : Parcelable {

    var lineLength: LineLength = LineLength()
    var precision = 200
    var strokeWidth: Float = 0f
        set(value) {
            if (value >= 0) {
                field = value
                this.paint.strokeWidth = value
            } else {
                throw IllegalArgumentException("\'strokeWidth\' must be positive!")
            }
        }

    var color: Int = 0
        set(value) {
            field = value
            paint.color = value
        }
    var hasHole = false

    constructor(parcel: Parcel) : this() {
        lineLength = parcel.readParcelable(LineLength::class.java.classLoader)
        precision = parcel.readInt()
        hasHole = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(lineLength, flags)
        parcel.writeInt(precision)
        parcel.writeByte(if (hasHole) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurveSettings> {
        override fun createFromParcel(parcel: Parcel): CurveSettings {
            return CurveSettings(parcel)
        }

        override fun newArray(size: Int): Array<CurveSettings?> {
            return arrayOfNulls(size)
        }
    }
}
