package com.vlad1m1r.lemniscate.utils;

import android.graphics.Path;
import android.util.Pair;

/**
 * Created by vladimirjovanovic on 2/10/17.
 */

public class CurveUtils {

    public static Path addPointsToPath(Pair<Float, Float> start, Pair<Float, Float> middle, Pair<Float, Float> end, Path path, boolean useCubicInterpolation) {
        if(start != null && middle != null && end != null) {
            path.moveTo(start.first, start.second);
            if(useCubicInterpolation) path.cubicTo(start.first, start.second, middle.first, middle.second, end.first, end.second);
            else {
                path.quadTo(start.first, start.second, middle.first, middle.second);
                path.quadTo(middle.first, middle.second, end.first, end.second);
            }
        }
        else if(start != null && middle != null) {
            path.moveTo(start.first, start.second);
            path.quadTo(start.first, start.second, middle.first, middle.second);
        }
        else if(middle != null && end != null) {
            path.moveTo(middle.first, middle.second);
            path.lineTo(end.first, end.second);
        }
        else if (start != null) {
            path.moveTo(start.first, start.second);
            path.lineTo(start.first, start.second);
        } else if(end != null) {
            path.moveTo(end.first, end.second);
        }
        return path;
    }

    public static Pair<Float, Float> checkPointForHole(Pair<Float, Float> point, float holeSize, float viewHeight, float viewWidth) {
        if(point != null &&
                Math.abs(point.first - viewWidth / 2) < holeSize &&
                Math.abs(point.second - viewHeight / 2) < holeSize) {
            return null;
        }
        return point;
    }
}
