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

open class CurveSettings (val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG),  var lineLength: LineLength = LineLength()) : Parcelable {

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
    }

    var precision = 200
    var strokeWidth: Float = 0.toFloat()
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

    protected constructor(`in`: Parcel) : this() {
        this.precision = `in`.readInt()
        this.strokeWidth = `in`.readFloat()
        this.color = `in`.readInt()
        this.lineLength = `in`.readParcelable(LineLength::class.java.classLoader)
        this.hasHole = `in`.readByte().toInt() != 0
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.precision)
        dest.writeFloat(this.strokeWidth)
        dest.writeInt(this.color)
        dest.writeParcelable(this.lineLength, flags)
        dest.writeByte(if (this.hasHole) 1.toByte() else 0.toByte())
    }

    companion object CREATOR : Parcelable.Creator<CurveSettings> {
        override fun createFromParcel(source: Parcel): CurveSettings {
            return CurveSettings(source)
        }

        override fun newArray(size: Int): Array<CurveSettings?> {
            return arrayOfNulls(size)
        }
    }
}
