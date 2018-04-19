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

import android.os.Parcel
import android.os.Parcelable

class AnimationSettings(var startingPointOnCurve:Int = 0, var duration: Int = 1000) : Parcelable {

    constructor(state: Parcel) : this(
            state.readInt(),
            state.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(startingPointOnCurve)
        parcel.writeInt(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimationSettings> {
        override fun createFromParcel(parcel: Parcel): AnimationSettings {
            return AnimationSettings(parcel)
        }

        override fun newArray(size: Int): Array<AnimationSettings?> {
            return arrayOfNulls(size)
        }
    }
}
