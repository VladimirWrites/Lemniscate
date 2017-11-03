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

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

public class CurveSettings implements Parcelable {

    private final Paint paint;
    private int precision = 200;
    private float strokeWidth;
    private int color;
    private LineLength lineLength;
    private boolean hasHole = false;

    public CurveSettings(Paint paint, LineLength lineLength) {
        this.paint = paint;
        this.lineLength = lineLength;
    }

    public CurveSettings() {
        this(new Paint(
                Paint.ANTI_ALIAS_FLAG){{
                 setStyle(Paint.Style.STROKE);
                 setStrokeCap(Paint.Cap.ROUND);
             }},
                new LineLength()
        );
    }

    protected CurveSettings(Parcel in) {
        this();
        this.precision = in.readInt();
        this.strokeWidth = in.readFloat();
        this.color = in.readInt();
        this.lineLength = in.readParcelable(LineLength.class.getClassLoader());
        this.hasHole = in.readByte() != 0;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        if (strokeWidth >= 0) {
            this.strokeWidth = strokeWidth;
            this.paint.setStrokeWidth(this.strokeWidth);
        } else {
            throw new IllegalArgumentException("\'strokeWidth\' must be positive!");
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(this.color);
    }

    public LineLength getLineLength() {
        return lineLength;
    }

    public void setLineLength(LineLength lineLength) {
        this.lineLength = lineLength;
    }

    public boolean hasHole() {
        return hasHole;
    }

    public void setHasHole(boolean hasHole) {
        this.hasHole = hasHole;
    }

    public Paint getPaint() {
        return paint;
    }

    public static final Parcelable.Creator<CurveSettings> CREATOR = new Parcelable.Creator<CurveSettings>() {
        @Override
        public CurveSettings createFromParcel(Parcel source) {
            return new CurveSettings(source);
        }

        @Override
        public CurveSettings[] newArray(int size) {
            return new CurveSettings[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.precision);
        dest.writeFloat(this.strokeWidth);
        dest.writeInt(this.color);
        dest.writeParcelable(this.lineLength, flags);
        dest.writeByte(this.hasHole ? (byte) 1 : (byte) 0);
    }
}
