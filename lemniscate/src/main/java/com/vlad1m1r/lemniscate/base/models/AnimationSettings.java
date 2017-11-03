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
package com.vlad1m1r.lemniscate.base.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimationSettings implements Parcelable {

    private int startingPointOnCurve = 0;
    private long duration = 1000;

    public AnimationSettings() {
    }

    protected AnimationSettings(Parcel in) {
        this.startingPointOnCurve = in.readInt();
        this.duration = in.readLong();
    }

    public int getStartingPointOnCurve() {
        return startingPointOnCurve;
    }

    public void setStartingPointOnCurve(int startingPointOnCurve) {
        this.startingPointOnCurve = startingPointOnCurve;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public static final Parcelable.Creator<AnimationSettings> CREATOR = new Parcelable.Creator<AnimationSettings>() {
        @Override
        public AnimationSettings createFromParcel(Parcel source) {
            return new AnimationSettings(source);
        }

        @Override
        public AnimationSettings[] newArray(int size) {
            return new AnimationSettings[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.startingPointOnCurve);
        dest.writeLong(this.duration);
    }
}
