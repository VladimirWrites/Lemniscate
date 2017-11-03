package com.vlad1m1r.lemniscate.utils;

import com.vlad1m1r.lemniscate.base.models.Point;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Created by vlad1m1r on 16-Oct-17.
 */
public class CurveUtilsTest {

    @Test
    public void checkPointForHole() {
        float viewSize = 100f;
        float strokeWidth = 10f;
        Point point = new Point(5,0, strokeWidth, viewSize);

        assertSame(CurveUtils.checkPointForHole(point, 1f, viewSize), point);
        assertNull(CurveUtils.checkPointForHole(point, 5f, viewSize));
        assertNull(CurveUtils.checkPointForHole(null, 1f, viewSize));
    }

}