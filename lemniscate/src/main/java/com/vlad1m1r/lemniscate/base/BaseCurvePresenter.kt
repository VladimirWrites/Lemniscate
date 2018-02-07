package com.vlad1m1r.lemniscate.base

import com.vlad1m1r.lemniscate.base.models.DrawState
import com.vlad1m1r.lemniscate.base.models.Point
import com.vlad1m1r.lemniscate.base.models.Points
import com.vlad1m1r.lemniscate.base.models.ViewSize
import com.vlad1m1r.lemniscate.base.settings.AnimationSettings
import com.vlad1m1r.lemniscate.base.settings.CurveSettings
import kotlin.math.round

class BaseCurvePresenter(override val view: IBaseCurveView,
                         override var curveSettings: CurveSettings,
                         override val viewSize: ViewSize,
                         override var animationSettings: AnimationSettings,
                         override val drawState: DrawState,
                         override val points: Points) : IBaseCurvePresenter {

    override fun updateStartingPointOnCurve(point: Int) {
        animationSettings.startingPointOnCurve = point
        drawState.recalculateLineLength(curveSettings.lineLength)
        view.invalidateProgressView()
    }

    internal val lineLengthToDraw: Int
        get() = round(curveSettings.precision * drawState.currentLineLength).toInt()

    override fun recreatePoints() {
        points.clear()
        createNewPoints()
        addPointsToPath()
    }

    internal fun createNewPoints() {
        var lineLengthToDraw = lineLengthToDraw

        while (lineLengthToDraw > 0) {
            lineLengthToDraw = addPointsToCurve(
                    getStartingPoint(),
                    lineLengthToDraw
            )
        }
    }

    internal fun getStartingPoint() = if (points.isEmpty) animationSettings.startingPointOnCurve else 0

    internal fun addPointsToPath() {
        drawState.addPointsToPath(points.getPoints(), curveSettings, viewSize)
    }

    internal fun addPointsToCurve(start: Int, remainingPoints: Int): Int {
        var remainingPointsTemp = remainingPoints
        for (i in start until curveSettings.precision) {

            points.addPoint(getPoint(i))

            if (--remainingPointsTemp == 0) {
                return remainingPointsTemp
            }
        }
        return remainingPointsTemp
    }

    internal fun getPoint(i: Int): Point {
        return Point(
                view.getGraphX(getT(i)),
                view.getGraphY(getT(i)),
                curveSettings.strokeWidth,
                viewSize.size
        )
    }

    internal fun getT(i: Int) = view.getT(i, curveSettings.precision)
}
