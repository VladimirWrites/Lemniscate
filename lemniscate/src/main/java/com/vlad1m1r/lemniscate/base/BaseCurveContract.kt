package com.vlad1m1r.lemniscate.base

import com.vlad1m1r.lemniscate.base.models.DrawState
import com.vlad1m1r.lemniscate.base.models.Points
import com.vlad1m1r.lemniscate.base.models.ViewSize
import com.vlad1m1r.lemniscate.base.settings.AnimationSettings
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import kotlin.math.PI

interface IBaseCurvePresenter {

    val view:IBaseCurveView
    var curveSettings: CurveSettings
    val viewSize: ViewSize
    var animationSettings: AnimationSettings
    val drawState: DrawState
    val points: Points

    fun recreatePoints()
    fun updateStartingPointOnCurve(point: Int)
}

interface IBaseCurveView {
    /**
     * This method should return values of x for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for x.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy x∈[-viewSize.getWidth()/2, viewSize.getWidth()/2].
     */
    fun getGraphX(t: Float): Float

    /**
     * This method should return values of y for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for y.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy y∈[-viewSize.getHeight()/2, viewSize.getHeight()/2].
     */
    fun getGraphY(t: Float): Float

    /**
     * @param i ∈ [0, mPrecision)
     * @return function is putting i∈[0, curveSettings.getPrecision()) points between [0, 2π]
     */
    fun getT(i: Int, precision: Int): Float {
        return i * 2f * PI.toFloat() / precision
    }

    fun invalidateView()

    fun requestViewLayout()
}

