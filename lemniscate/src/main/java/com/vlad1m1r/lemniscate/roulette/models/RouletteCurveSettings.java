package com.vlad1m1r.lemniscate.roulette.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.vlad1m1r.lemniscate.base.models.CurveSettings;

public class RouletteCurveSettings extends CurveSettings implements Parcelable {

    public static final Parcelable.Creator<RouletteCurveSettings> CREATOR = new Parcelable.Creator<RouletteCurveSettings>() {
        @Override
        public RouletteCurveSettings createFromParcel(Parcel source) {
            return new RouletteCurveSettings(source);
        }

        @Override
        public RouletteCurveSettings[] newArray(int size) {
            return new RouletteCurveSettings[size];
        }
    };
    /**
     * Radius of the non-moving circle
     */
    private float radiusFixed = 3f;
    /**
     * Radius of the moving circle
     */
    private float radiusMoving = 1f;
    /**
     * Distance from the center of the moving circle
     */
    private float distanceFromCenter = 1f;
    /**
     * Curve will be drawn on interval  [0, 2*numberOfCycles*π] before repeating
     */
    private float numberOfCycles = 1;

    public RouletteCurveSettings() {
    }

    protected RouletteCurveSettings(Parcel in) {
        this.radiusFixed = in.readFloat();
        this.radiusMoving = in.readFloat();
        this.distanceFromCenter = in.readFloat();
        this.numberOfCycles = in.readFloat();
    }

    public float getRadiusFixed() {
        return radiusFixed;
    }

    public void setRadiusFixed(float radiusFixed) {
        this.radiusFixed = radiusFixed;
    }

    public float getRadiusMoving() {
        return radiusMoving;
    }

    public void setRadiusMoving(float radiusMoving) {
        this.radiusMoving = radiusMoving;
    }

    public float getDistanceFromCenter() {
        return distanceFromCenter;
    }

    public void setDistanceFromCenter(float distanceFromCenter) {
        this.distanceFromCenter = distanceFromCenter;
    }

    public float getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(float numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.radiusFixed);
        dest.writeFloat(this.radiusMoving);
        dest.writeFloat(this.distanceFromCenter);
        dest.writeFloat(this.numberOfCycles);
    }
}
