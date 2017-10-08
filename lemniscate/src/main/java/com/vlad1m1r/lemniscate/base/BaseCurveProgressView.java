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

package com.vlad1m1r.lemniscate.base;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.vlad1m1r.lemniscate.base.models.AnimationSettings;
import com.vlad1m1r.lemniscate.base.models.CurveSettings;
import com.vlad1m1r.lemniscate.base.models.DrawState;
import com.vlad1m1r.lemniscate.base.models.Point;
import com.vlad1m1r.lemniscate.base.models.Points;
import com.vlad1m1r.lemniscate.base.models.ViewSize;
import com.vlad1m1r.lemniscate.sample.lemniscate.R;

public abstract class BaseCurveProgressView<T extends CurveSettings & Parcelable> extends View {

    protected T curveSettings;
    protected AnimationSettings animationSettings = new AnimationSettings();
    protected DrawState drawState = new DrawState();
    protected ViewSize viewSize = new ViewSize();
    protected Points points = new Points();

    private ValueAnimator valueAnimator;
    private TimeInterpolator interpolator = new LinearInterpolator();

    public BaseCurveProgressView(Context context) {
        super(context);
        init();
    }

    public BaseCurveProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();

        TypedArray curveAttributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BaseCurveProgressView,
                0, 0);

        TypedArray colorAccentAttributes = context.obtainStyledAttributes(attrs, new int[] { R.attr.colorAccent });

        try {
            int colorAccent = colorAccentAttributes.getColor(0, 0);

            setLineMinLength(curveAttributes.getFloat(R.styleable.BaseCurveProgressView_minLineLength, 0.4f));
            setLineMaxLength(curveAttributes.getFloat(R.styleable.BaseCurveProgressView_maxLineLength, 0.8f));
            setColor(curveAttributes.getColor(R.styleable.BaseCurveProgressView_lineColor, colorAccent));
            setDuration(curveAttributes.getInteger(R.styleable.BaseCurveProgressView_duration, 1000));
            setHasHole(curveAttributes.getBoolean(R.styleable.BaseCurveProgressView_hasHole, false));
            setStrokeWidth(curveAttributes.getDimension(R.styleable.BaseCurveProgressView_strokeWidth, getResources().getDimension(R.dimen.lemniscate_stroke_width)));
            setPrecision(curveAttributes.getInteger(R.styleable.BaseCurveProgressView_precision, 200));
        } finally {
            curveAttributes.recycle();
            colorAccentAttributes.recycle();
        }
    }

    public BaseCurveProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        curveSettings = getCurveSettings();
    }

    protected T getCurveSettings() {
        return (T) new CurveSettings();
    }

    /**
     * This method should return values of y for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for y.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy y∈[-viewSize.getHeight()/2, viewSize.getHeight()/2].
     */
    public abstract float getGraphY(double t);

    /**
     * This method should return values of x for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for x.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy x∈[-viewSize.getWidth()/2, viewSize.getWidth()/2].
     */
    public abstract float getGraphX(double t);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        recreatePoints();
        drawState.addPointsToPath(points.getPoints(), curveSettings, viewSize);
        canvas.drawPath(drawState.getPath(), curveSettings.getPaint());
    }

    private void recreatePoints() {
        points.clear();
        createNewPoints();
    }

    private void createNewPoints() {
        int lineLengthToDraw = getLineLengthToDraw();

        // creates points from mStart till mLineLength points is created, or till mPrecision is reached in first pass
        // if there is more points to be created goes to second pass
        while (lineLengthToDraw > 0) {
            lineLengthToDraw = addPointsToCurve(
                    points.isEmpty() ? animationSettings.getStartingPointOnCurve() : 0,
                    lineLengthToDraw
            );
        }
    }

    private int getLineLengthToDraw() {
        return Math.round(curveSettings.getPrecision() * drawState.getCurrentLineLength());
    }

    private int addPointsToCurve(Integer start, int remainingPoints) {
        for (int i = start; i < curveSettings.getPrecision(); i++) {

            points.addPoint(getPoint(i));

            if (--remainingPoints == 0) {
                return remainingPoints;
            }
        }
        return remainingPoints;
    }

    private Point getPoint(int i) {
        return new Point(
                getGraphX(getT(i)),
                getGraphY(getT(i)),
                curveSettings.getStrokeWidth(),
                viewSize.getSize()
        );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float defaultSize = getResources().getDimension(R.dimen.lemniscate_preferred_height) * viewSize.getSizeMultiplier();

        int xPadding = getPaddingLeft() + getPaddingRight();
        int yPadding = getPaddingTop() + getPaddingBottom();

        int viewSize = getMaxViewSquareSize(
                getMeasuredHeight(),
                getMeasuredWidth(),
                xPadding,
                yPadding
        );

        this.viewSize.setSize(
               getViewDimension(
                       MeasureSpec.getMode(widthMeasureSpec),
                       viewSize,
                       defaultSize
               )
        );

        setMeasuredDimension(Math.round(this.viewSize.getSize() + xPadding), Math.round(this.viewSize.getSize() + yPadding));
    }

    private static  int getMaxViewSquareSize(int height, int width, int xPadding, int yPadding) {
        return Math.min(height - yPadding, width - xPadding);
    }

    private static float getViewDimension(int mode, float viewSize, float defaultSize) {
        if (mode == MeasureSpec.EXACTLY) {
            return viewSize;
        } else if (mode == MeasureSpec.AT_MOST) {
            return Math.min(defaultSize, viewSize);
        } else {
            return defaultSize;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animateLemniscate();
    }

    private void animateLemniscate() {
        if(valueAnimator != null) valueAnimator.end();
        valueAnimator = ValueAnimator.ofInt(curveSettings.getPrecision() - 1, 0);
        valueAnimator.setDuration(animationSettings.getDuration());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationSettings.setStartingPointOnCurve((Integer) animation.getAnimatedValue());
                drawState.recalculateLineLength(curveSettings.getLineLength());
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valueAnimator.end();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        BaseCurveSavedState<T> ss = new BaseCurveSavedState(superState);
        ss.curveSettings = curveSettings;
        ss.animationSettings = animationSettings;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof BaseCurveSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        BaseCurveSavedState<T> ss = (BaseCurveSavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());

        this.curveSettings = ss.curveSettings;
        this.animationSettings = ss.animationSettings;
    }

    public void setSizeMultiplier(float multiplier) {
        this.viewSize.setSizeMultiplier(multiplier);
        requestLayout();
        invalidate();
    }

    protected static class BaseCurveSavedState<T extends CurveSettings & Parcelable> extends BaseSavedState {
        T curveSettings;
        AnimationSettings animationSettings;


        public BaseCurveSavedState(Parcelable superState) {
            super(superState);
        }

        public BaseCurveSavedState(Parcel in) {
            super(in);
            ClassLoader classLoader = this.curveSettings.getClass().getClassLoader();
            this.curveSettings = in.readParcelable(classLoader);
            this.animationSettings = in.readParcelable(AnimationSettings.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeParcelable(this.curveSettings, flags);
            out.writeParcelable(this.animationSettings, flags);
        }

        public static final Parcelable.Creator<BaseCurveSavedState> CREATOR =
                new Parcelable.Creator<BaseCurveSavedState>() {
                    public BaseCurveSavedState createFromParcel(Parcel in) {
                        return new BaseCurveSavedState(in);
                    }
                    public BaseCurveSavedState[] newArray(int size) {
                        return new BaseCurveSavedState[size];
                    }
                };
    }


    public void setLineMinLength(float lineMinLength) {
        curveSettings.getLineLength().setLineMinLength(lineMinLength);
    }

    public void setLineMaxLength(float lineMaxLength) {
        curveSettings.getLineLength().setLineMaxLength(lineMaxLength);
    }

    public void setColor(int color) {
        curveSettings.setColor(color);
    }

    public void setDuration(int duration) {
        animationSettings.setDuration(duration);
        if(valueAnimator != null) valueAnimator.setDuration(duration);
    }

    public void setHasHole(boolean hasHole) {
        curveSettings.setHasHole(hasHole);
    }

    public void setStrokeWidth(float strokeWidth) {
        curveSettings.setStrokeWidth(strokeWidth);
    }

    public float getStrokeWidth() {
        return curveSettings.getStrokeWidth();
    }

    public float getLineMinLength() {
        return curveSettings.getLineLength().getLineMinLength();
    }

    public float getLineMaxLength() {
        return curveSettings.getLineLength().getLineMaxLength();
    }

    public boolean hasHole() {
        return curveSettings.hasHole();
    }

    public int getColor() {
        return curveSettings.getColor();
    }

    public long getDuration() {
        return animationSettings.getDuration();
    }

    public int getPrecision() {
        return curveSettings.getPrecision();
    }

    public void setPrecision(int precision) {
        curveSettings.setPrecision(precision);
        animateLemniscate();
        invalidate();
    }

    /**
     * @param i ∈ [0, mPrecision)
     * @return function is putting i∈[0, curveSettings.getPrecision()) points between [0, 2π]
     */
    protected double getT(int i) {
        return i*2*Math.PI/curveSettings.getPrecision();
    }
}