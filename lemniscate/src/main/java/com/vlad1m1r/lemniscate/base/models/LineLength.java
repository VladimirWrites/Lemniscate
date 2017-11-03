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

public class LineLength implements Parcelable {

    private float lineMinLength = 0.4f;
    private float lineMaxLength = 0.8f;

    public LineLength() {
    }

    protected LineLength(Parcel in) {
        this.lineMinLength = in.readFloat();
        this.lineMaxLength = in.readFloat();
    }

    public float getLineMinLength() {
        return lineMinLength;
    }

    public void setLineMinLength(float lineMinLength) {
        if (lineMinLength > 0 && lineMinLength <= 1) {
            this.lineMinLength = lineMinLength;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public float getLineMaxLength() {
        return lineMaxLength;
    }

    public void setLineMaxLength(float lineMaxLength) {
        if (lineMaxLength > 0 && lineMaxLength <= 1) {
            this.lineMaxLength = lineMaxLength;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static final Parcelable.Creator<LineLength> CREATOR = new Parcelable.Creator<LineLength>() {
        @Override
        public LineLength createFromParcel(Parcel source) {
            return new LineLength(source);
        }

        @Override
        public LineLength[] newArray(int size) {
            return new LineLength[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.lineMinLength);
        dest.writeFloat(this.lineMaxLength);
    }
}
