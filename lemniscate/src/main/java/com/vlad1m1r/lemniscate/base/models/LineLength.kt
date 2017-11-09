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
package com.vlad1m1r.lemniscate.base.models

import android.os.Parcel
import android.os.Parcelable

class LineLength : Parcelable {

    var lineMinLength = 0.4f
        set(value) {
            if (value > 0 && value <= 1) {
                field = value
            } else {
                throw IllegalArgumentException()
            }
        }

    var lineMaxLength = 0.8f
        set(value) {
            if (value > 0 && value <= 1) {
                field = value
            } else {
                throw IllegalArgumentException()
            }
        }

    constructor()

    protected constructor(`in`: Parcel) {
        this.lineMinLength = `in`.readFloat()
        this.lineMaxLength = `in`.readFloat()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloat(this.lineMinLength)
        dest.writeFloat(this.lineMaxLength)
    }

    companion object CREATOR : Parcelable.Creator<LineLength> {
        override fun createFromParcel(parcel: Parcel): LineLength {
            return LineLength(parcel)
        }

        override fun newArray(size: Int): Array<LineLength?> {
            return arrayOfNulls(size)
        }
    }
}
