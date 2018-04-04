package com.vlad1m1r.lemniscate.roulette.settings

import android.os.Parcel
import android.os.Parcelable

class RouletteCurveSettings() : Parcelable {

    /**
     * Radius of the non-moving circle
     */
    var radiusFixed = 3.0f
    /**
     * Radius of the moving circle
     */
    var radiusMoving = 1.0f
    /**
     * Distance from the center of the moving circle
     */
    var distanceFromCenter = 1.0f
    /**
     * Curve will be drawn on interval  [0, 2*numberOfCycles*π] before repeating
     */
    var numberOfCycles = 1.0f

    constructor(parcel: Parcel) : this() {
        radiusFixed = parcel.readFloat()
        radiusMoving = parcel.readFloat()
        distanceFromCenter = parcel.readFloat()
        numberOfCycles = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(radiusFixed)
        parcel.writeFloat(radiusMoving)
        parcel.writeFloat(distanceFromCenter)
        parcel.writeFloat(numberOfCycles)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RouletteCurveSettings> {
        override fun createFromParcel(parcel: Parcel): RouletteCurveSettings {
            return RouletteCurveSettings(parcel)
        }

        override fun newArray(size: Int): Array<RouletteCurveSettings?> {
            return arrayOfNulls(size)
        }
    }
}
