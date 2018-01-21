package com.vlad1m1r.lemniscate.roulette.settings

import android.os.Parcel
import android.os.Parcelable

class RouletteCurveSettings : Parcelable {

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

    constructor()

    protected constructor(`in`: Parcel) {
        this.radiusFixed = `in`.readFloat()
        this.radiusMoving = `in`.readFloat()
        this.distanceFromCenter = `in`.readFloat()
        this.numberOfCycles = `in`.readFloat()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloat(this.radiusFixed)
        dest.writeFloat(this.radiusMoving)
        dest.writeFloat(this.distanceFromCenter)
        dest.writeFloat(this.numberOfCycles)
    }

    companion object {

        val CREATOR: Parcelable.Creator<RouletteCurveSettings> = object : Parcelable.Creator<RouletteCurveSettings> {
            override fun createFromParcel(source: Parcel): RouletteCurveSettings {
                return RouletteCurveSettings(source)
            }

            override fun newArray(size: Int): Array<RouletteCurveSettings?> {
                return arrayOfNulls(size)
            }
        }
    }
}
