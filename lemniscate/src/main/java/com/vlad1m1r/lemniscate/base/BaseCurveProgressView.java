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

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.vlad1m1r.lemniscate.sample.lemniscate.R;

import java.util.ArrayList;

public abstract class BaseCurveProgressView extends View {


    /**
     * This means that on every animation step curve will become shorter (or longer depending on mIsExpanding)
     * for mPrecision * STEP_SIZE
     */
    protected static final float STEP_SIZE = 0.001f;

    /**
     * Number of points drawn in one full cycle
     */
    protected int mPrecision = 200;

    protected float mStrokeWidth = getResources().getDimension(R.dimen.lemniscate_stroke_width);

    /**
     * Default size of view will be multiplied with this number
     */
    protected float mSizeMultiplier = 1;

    protected double mLemniscateParamX, mLemniscateParamY;

    /**
     * If length is fixed (mIsLineLengthChangeable == false) than line length will always be
     * equal to this value
     */
    protected float mLineLength = 0.6f;

    /**
     * If length is not fixed than it will be changing between these values
     */
    protected float mLineMinLength = 0.4f, mLineMaxLength = 0.8f;

    /**
     * If true, the line length will oscillate between mLineMinLength and mLineMaxLength
     */
    protected boolean mIsLineLengthChangeable = true;

    /**
     * Line color
     */
    protected int mColor = Color.GRAY;

    /**
     * duration of one cycle of animation in milliseconds
     */
    protected long mDuration = 1000;


    /**
     * creates illusion that line is going under itself
     * should be used when line is longer than 600
     */
    protected boolean mHasHole = false;

    /**
     * point from which animation will start
     */
    protected int mStart = 0;


    protected Paint mPaint;

    /**
     * Width of full view without padding
     */
    protected float mViewWidth;
    /**
     * Height of full view without padding
     */
    protected float mViewHeight;
    protected ValueAnimator mValueAnimator;

    /**
     * interpolator of animation
     */
    protected TimeInterpolator mInterpolator = new LinearInterpolator();

    /**
     * is line in shrinking or expanding phase
     */
    protected boolean mIsExpanding = true;


    private final Path mPath = new Path();
    private final ArrayList<Pair<Float, Float>> mListOfPoints = new ArrayList<>();

    public BaseCurveProgressView(Context context) {
        super(context);
        init();
    }

    public BaseCurveProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BaseCurveProgressView,
                0, 0);

        try {
            setLineMinLength(a.getFloat(R.styleable.BaseCurveProgressView_minLineLength, mLineMinLength));
            setLineMaxLength(a.getFloat(R.styleable.BaseCurveProgressView_maxLineLength, mLineMaxLength));
            setLineLength(a.getFloat(R.styleable.BaseCurveProgressView_lineLength, mLineLength));
            setIsLineLengthChangeable(a.getBoolean(R.styleable.BaseCurveProgressView_lineLengthChangeable, true));
            setColor(a.getColor(R.styleable.BaseCurveProgressView_lineColor, Color.GRAY));
            setDuration(a.getInteger(R.styleable.BaseCurveProgressView_duration, 1000));
            setHasHole(a.getBoolean(R.styleable.BaseCurveProgressView_hasHole, false));
            setStrokeWidth(a.getDimension(R.styleable.BaseCurveProgressView_strokeWidth, getResources().getDimension(R.dimen.lemniscate_stroke_width)));
            setSizeMultiplier(a.getFloat(R.styleable.BaseCurveProgressView_sizeMultiplier, 1));
            setPrecision(a.getInteger(R.styleable.BaseCurveProgressView_precision, mPrecision));
        } finally {
            a.recycle();
        }
        init();
    }

    public BaseCurveProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * This method should return values of y for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for y.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy y∈[-mLemniscateParamY, mLemniscateParamY].
     */
    public abstract double getGraphY(double t);

    /**
     * This method should return values of x for t∈[0, upper limit of getT() function].
     * We should use parametric representation of curve for x.
     * Curve should be closed and periodic on interval that returns getT().
     * Resulting value should satisfy x∈[-mLemniscateParamX, upper limit of getT() function].
     */
    public abstract double getGraphX(double t);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //TODO: mListOfPoints should be removed and everything should be done just with @param mPath

        createListOfPoints();
        addPointsToPath();

        canvas.drawPath(mPath, mPaint);
    }

    private void createListOfPoints() {
        int lineLengthToDraw = Math.round(mPrecision * mLineLength);
        boolean firstPass = true;
        mListOfPoints.clear();

        // creates points from mStart till mLineLength points is created, or till mPrecision is reached in first pass
        // if there is more points to be created goes to second pass
        while (lineLengthToDraw > 0) {
            if(firstPass) {
                lineLengthToDraw = getPointsOnCurve(mListOfPoints, mStart, lineLengthToDraw);
                firstPass = false;
            } else {
                lineLengthToDraw = getPointsOnCurve(mListOfPoints, 0, lineLengthToDraw);
            }
        }
    }

    private void addPointsToPath() {
        mPath.reset();

        float holeSize = mStrokeWidth; //Math.max(mStrokeWidth, 10);

        //adds points to path and creates hole if mHasHole
        for (int i = 0; i < mListOfPoints.size(); i += 2) {
            Pair<Float, Float> start = mListOfPoints.get(i);
            Pair<Float, Float> middle = null;
            Pair<Float, Float> end = null;

            if(mListOfPoints.size() > i + 2) {
                end = mListOfPoints.get(i + 2);
                middle = mListOfPoints.get(i + 1);
            } else if(mListOfPoints.size() > i + 1)
                middle = mListOfPoints.get(i + 1);

            if(mHasHole) {
                if(start!= null && middle != null && start.first > middle.first ||
                        middle!= null && end != null && middle.first > end.first) {
                    start = checkPointForHole(start, holeSize);
                    middle = checkPointForHole(middle, holeSize);
                    end = checkPointForHole(end, holeSize);
                }
            }

            if(start != null && middle != null && end != null) {
                mPath.moveTo(start.first, start.second);
                mPath.cubicTo(start.first, start.second, middle.first, middle.second, end.first, end.second);
            }
            else if(start != null && middle != null) {
                mPath.moveTo(start.first, start.second);
                mPath.quadTo(start.first, start.second, middle.first, middle.second);
            }
            else if(middle != null && end != null) {
                mPath.moveTo(middle.first, middle.second);
                mPath.lineTo(end.first, end.second);
            }
            else if (start != null) {
                mPath.moveTo(start.first, start.second);
                mPath.lineTo(start.first, start.second);
            } else if(end != null) {
                mPath.moveTo(end.first, end.second);
            }
        }
    }

    private Pair<Float, Float> checkPointForHole(Pair<Float, Float> point, float holeSize) {
        if(point != null &&
                Math.abs(point.first - mViewWidth / 2) < holeSize &&
                Math.abs(point.second - mViewHeight / 2) < holeSize) {
            return null;
        }
        return point;
    }

    private int getPointsOnCurve(ArrayList<Pair<Float, Float>> list, @Nullable Integer start, int leftPoints) {
        for (int i = start != null ? start : 0; i < mPrecision; i++) {

            // translates points to positive coordinates
            double x = getGraphX(getT(i)) + mLemniscateParamX;
            double y = getGraphY(getT(i)) + mLemniscateParamY;

            addPointToList(list, x, y);

            //removes number of points left to draw and checks is there any to be drawn
            leftPoints--;
            if (leftPoints == 0) {
                return leftPoints;
            }
        }
        return leftPoints;
    }

    private void addPointToList(ArrayList<Pair<Float, Float>> list, double x, double y) {

        //finds smallest ratio for which curve should be resized because of stroke width
        float ratio = mViewHeight/(mViewHeight + 2 * mStrokeWidth);

        //move every point for ratio
        x = x * ratio;
        y = y * ratio;

        //moves points so that curve is centered
        x = x + mStrokeWidth * ratio;
        y = y + mStrokeWidth * ratio;

        list.add(new Pair<>((float) x, (float) y));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float desiredWidth = getResources().getDimension(R.dimen.lemniscate_preferred_width) * mSizeMultiplier;
        float desiredHeight = getResources().getDimension(R.dimen.lemniscate_preferred_height) * mSizeMultiplier;

        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();

        int viewSize = Math.min(getMeasuredHeight() - yPad, getMeasuredWidth() - xPad);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mViewWidth = viewSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mViewWidth = Math.min(desiredWidth, viewSize);
        } else {
            mViewWidth = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = viewSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            mViewHeight = Math.min(desiredHeight, viewSize);
        } else {
            mViewHeight = desiredHeight;
        }

        mLemniscateParamX = mViewWidth/2;
        mLemniscateParamY = mViewHeight/2;

        setMeasuredDimension(Math.round(mViewWidth + xPad), Math.round(mViewHeight + yPad));
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
        if(mValueAnimator != null) mValueAnimator.end();
        mValueAnimator = ValueAnimator.ofInt(mPrecision, 0);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setInterpolator(mInterpolator);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStart = (Integer) animation.getAnimatedValue();
                recalculateLineLength();
                invalidate();
            }
        });
        mValueAnimator.start();
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    private void recalculateLineLength() {
        if (mIsLineLengthChangeable && mLineMinLength < mLineMaxLength) {
            if (mLineLength < mLineMinLength) mLineLength = mLineMinLength;
            if (mLineLength > mLineMaxLength) mLineLength = mLineMaxLength;
            if (mLineLength < mLineMaxLength && mIsExpanding) {
                mLineLength+=STEP_SIZE;
            } else if (mLineLength > mLineMinLength && !mIsExpanding) {
                mLineLength-=STEP_SIZE;
            } else if (mLineLength >= mLineMaxLength) {
                mIsExpanding = false;
            } else if (mLineLength <= mLineMinLength) {
                mIsExpanding = true;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.end();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        BaseCurveSavedState ss = new BaseCurveSavedState(superState);

        ss.strokeWidth = this.mStrokeWidth;
        ss.sizeMultiplier = this.mSizeMultiplier;
        ss.lineLength = this.mLineLength;
        ss.lineMinLength = this.mLineMinLength;
        ss.lineMaxLength = this.mLineMaxLength;
        ss.isLineLengthChangeable = this.mIsLineLengthChangeable;
        ss.color = this.mColor;
        ss.duration = this.mDuration;
        ss.hasHole = this.mHasHole;
        ss.precision = this.mPrecision;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof BaseCurveSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        BaseCurveSavedState ss = (BaseCurveSavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        setStrokeWidth(ss.strokeWidth);
        setSizeMultiplier(ss.sizeMultiplier);
        setLineLength(ss.lineLength);
        setLineMinLength(ss.lineMinLength);
        setLineMaxLength(ss.lineMaxLength);
        setIsLineLengthChangeable(ss.isLineLengthChangeable);
        setColor(ss.color);
        setDuration((int)ss.duration);
        setHasHole(ss.hasHole);
        setPrecision(ss.precision);
    }

    protected static class BaseCurveSavedState extends BaseSavedState {
        float strokeWidth;
        float sizeMultiplier;
        float lineLength;
        float lineMinLength;
        float lineMaxLength;
        boolean isLineLengthChangeable;
        int color;
        long duration;
        boolean hasHole;
        int precision;

        public BaseCurveSavedState(Parcelable superState) {
            super(superState);
        }

        public BaseCurveSavedState(Parcel in) {
            super(in);
            this.strokeWidth = in.readFloat();
            this.sizeMultiplier = in.readFloat();
            this.lineLength = in.readFloat();
            this.lineMinLength = in.readFloat();
            this.lineMaxLength = in.readFloat();
            this.isLineLengthChangeable = in.readByte() != 0;
            this.color = in.readInt();
            this.duration = in.readLong();
            this.hasHole = in.readByte() != 0;
            this.precision = in.readInt();

        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.strokeWidth);
            out.writeFloat(this.sizeMultiplier);
            out.writeFloat(this.lineLength);
            out.writeFloat(this.lineMinLength);
            out.writeFloat(this.lineMaxLength);
            out.writeByte((byte) (isLineLengthChangeable ? 1 : 0));
            out.writeInt(this.color);
            out.writeLong(this.duration);
            out.writeByte((byte) (hasHole ? 1 : 0));
            out.writeInt(this.precision);
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
        if (lineMinLength > 0 && lineMinLength <= 1) mLineMinLength = lineMinLength;
    }

    public void setLineMaxLength(float lineMaxLength) {
        if (lineMaxLength > 0 && lineMaxLength <= 1) mLineMaxLength = lineMaxLength;
    }

    public void setLineLength(float lineLength) {
        if (lineLength > 0 && lineLength <= 1) mLineLength = lineLength;
    }

    public void setIsLineLengthChangeable(boolean lineLengthChangeable) {
        mIsLineLengthChangeable = lineLengthChangeable;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setColor(int color) {
        mColor = color;
        if(mPaint != null) mPaint.setColor(color);
    }

    public void setDuration(int duration) {
        mDuration = duration;
        if(mValueAnimator != null) mValueAnimator.setDuration(mDuration);
    }

    public void setHasHole(boolean hasHole) {
        mHasHole = hasHole;
    }

    public void setStrokeWidth(float strokeWidth) {
        if(strokeWidth > 0) {
            mStrokeWidth = strokeWidth;
            if (mPaint != null) mPaint.setStrokeWidth(mStrokeWidth);
            requestLayout();
            invalidate();
        }
    }

    public void setSizeMultiplier(float sizeMultiplier) {
        mSizeMultiplier = sizeMultiplier;
        requestLayout();
        invalidate();
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public float getLineMinLength() {
        return mLineMinLength;
    }

    public float getLineLength() {
        return mLineLength;
    }

    public float getLineMaxLength() {
        return mLineMaxLength;
    }

    public boolean isHasHole() {
        return mHasHole;
    }

    public boolean isLineLengthChangeable() {
        return mIsLineLengthChangeable;
    }

    public int getColor() {
        return mColor;
    }

    public long getDuration() {
        return mDuration;
    }

    public int getPrecision() {
        return mPrecision;
    }

    public void setPrecision(int precision) {
        mPrecision = precision;
        animateLemniscate();
        invalidate();
    }


    /**
     * @param i ∈ [0, mPrecision)
     * @return function is putting i∈[0, mPrecision) points between [0, 2π]
     */
    protected double getT(int i) {
        return i*2*Math.PI/mPrecision;
    }
}