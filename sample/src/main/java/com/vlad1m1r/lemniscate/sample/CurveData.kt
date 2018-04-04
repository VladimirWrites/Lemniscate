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

package com.vlad1m1r.lemniscate.sample

import android.os.Parcel
import android.os.Parcelable

class CurveData(var precision: Int = 200,
                var strokeWidth: Float = 10.0f,
                var sizeMultiplier: Float = 1.0f,
                var lineMinLength: Float = 0.4f,
                var lineMaxLength: Float = 0.8f,
                var color: Int = 0,
                var duration: Int = 1000,
                var hasHole: Boolean = false,
                var radiusFixed: Float = 4.0f,
                var radiusMoving: Float = 1.0f,
                var distanceFromCenter: Float = 3.0f,
                var numberOfCycles: Int = 1) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(precision)
        parcel.writeFloat(strokeWidth)
        parcel.writeFloat(sizeMultiplier)
        parcel.writeFloat(lineMinLength)
        parcel.writeFloat(lineMaxLength)
        parcel.writeInt(color)
        parcel.writeInt(duration)
        parcel.writeByte(if (hasHole) 1 else 0)
        parcel.writeFloat(radiusFixed)
        parcel.writeFloat(radiusMoving)
        parcel.writeFloat(distanceFromCenter)
        parcel.writeInt(numberOfCycles)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurveData> {
        override fun createFromParcel(parcel: Parcel): CurveData {
            return CurveData(parcel)
        }

        override fun newArray(size: Int): Array<CurveData?> {
            return arrayOfNulls(size)
        }
    }
}
