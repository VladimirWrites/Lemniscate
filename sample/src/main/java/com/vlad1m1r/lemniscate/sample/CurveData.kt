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
                var strokeWidth: Float = 10f,
                var sizeMultiplier: Float = 1f,
                var lineMinLength: Float = 0.4f,
                var lineMaxLength: Float = 0.8f,
                var color: Int = 0,
                var duration: Int = 1000,
                var hasHole: Boolean = false,
                var radiusFixed: Float = 4f,
                var radiusMoving: Float = 1f,
                var distanceFromCenter: Float = 3f,
                var numberOfCycles: Int = 1) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.precision)
        dest.writeFloat(this.strokeWidth)
        dest.writeFloat(this.sizeMultiplier)
        dest.writeFloat(this.lineMinLength)
        dest.writeFloat(this.lineMaxLength)
        dest.writeInt(this.color)
        dest.writeInt(this.duration)
        dest.writeByte(if (this.hasHole) 1.toByte() else 0.toByte())
        dest.writeFloat(this.radiusFixed)
        dest.writeFloat(this.radiusMoving)
        dest.writeFloat(this.distanceFromCenter)
        dest.writeInt(this.numberOfCycles)
    }

    protected constructor(`in`: Parcel) : this() {
        this.precision = `in`.readInt()
        this.strokeWidth = `in`.readFloat()
        this.sizeMultiplier = `in`.readFloat()
        this.lineMinLength = `in`.readFloat()
        this.lineMaxLength = `in`.readFloat()
        this.color = `in`.readInt()
        this.duration = `in`.readInt()
        this.hasHole = `in`.readByte().toInt() != 0
        this.radiusFixed = `in`.readFloat()
        this.radiusMoving = `in`.readFloat()
        this.distanceFromCenter = `in`.readFloat()
        this.numberOfCycles = `in`.readInt()
    }

    companion object {
        val CREATOR: Parcelable.Creator<CurveData> = object : Parcelable.Creator<CurveData> {
            override fun createFromParcel(source: Parcel): CurveData {
                return CurveData(source)
            }

            override fun newArray(size: Int): Array<CurveData?> {
                return arrayOfNulls(size)
            }
        }
    }
}
