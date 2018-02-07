package com.vlad1m1r.lemniscate.testutils

import com.vlad1m1r.lemniscate.base.models.LineLength
import com.vlad1m1r.lemniscate.base.settings.AnimationSettings
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import com.vlad1m1r.lemniscate.roulette.settings.RouletteCurveSettings

fun AnimationSettings.isEqualTo(animationSettings: AnimationSettings): Boolean {
    return this.duration == animationSettings.duration &&
            this.startingPointOnCurve == animationSettings.startingPointOnCurve
}

fun CurveSettings.isEqualTo(curveSettings: CurveSettings): Boolean {
    return this.hasHole == curveSettings.hasHole &&
            this.color == curveSettings.color &&
            this.precision == curveSettings.precision &&
            this.strokeWidth == curveSettings.strokeWidth &&
            this.lineLength.isEqualTo(curveSettings.lineLength)
}

fun LineLength.isEqualTo(lineLength: LineLength): Boolean {
    return this.lineMinLength == lineLength.lineMinLength &&
            this.lineMaxLength == lineLength.lineMaxLength
}

fun RouletteCurveSettings.isEqualTo(rouletteCurveSettings: RouletteCurveSettings): Boolean {
    return this.distanceFromCenter == rouletteCurveSettings.distanceFromCenter &&
            this.numberOfCycles == rouletteCurveSettings.numberOfCycles &&
            this.radiusFixed == rouletteCurveSettings.radiusFixed &&
            this.radiusMoving == rouletteCurveSettings.radiusMoving
}
