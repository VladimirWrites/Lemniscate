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

/**
 * Created by vladimirjovanovic on 12/30/16.
 */

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


    Path mPath = new Path();
    ArrayList<Pair<Float, Float>> mListOfPoints = new ArrayList<>();

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

    public abstract double getGraphY(int i);

    public abstract double getGraphX(int i);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //TODO: mListOfPoints should be removed and everything should be done just with @param mPath

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

        mPath.reset();

        float holeSize = Math.max(mStrokeWidth, 10);

        boolean isDrawingHole = false;

        //adds points to path and creates hole if mHasHole
        for (int i = 0; i < mListOfPoints.size() - 2; i += 1) {
            Pair<Float, Float> start = mListOfPoints.get(i);
            Pair<Float, Float> middle = mListOfPoints.get(i + 1);
            Pair<Float, Float> end = mListOfPoints.get(i + 2);

            if (!(mHasHole &&
                    Math.abs(start.second - mViewHeight / 2) < holeSize &&
                    Math.abs(end.second - mViewHeight / 2) < holeSize &&
                    Math.abs(start.first - mViewWidth / 2) < holeSize &&
                    Math.abs(end.first - mViewWidth / 2) < holeSize &&
                    start.first > end.first)) {
                if(i == 0 || isDrawingHole)  {
                    mPath.moveTo(start.first, start.second);
                    isDrawingHole = false;
                }
                mPath.quadTo(middle.first, middle.second, end.first, end.second);
            } else {
                isDrawingHole = true;
            }
        }

        canvas.drawPath(mPath, mPaint);
    }

    private int getPointsOnCurve(ArrayList<Pair<Float, Float>> list, @Nullable Integer start, int leftPoints) {
        for (int i = start != null ? start : 0; i < mPrecision; i++) {

            // translates points to positive coordinates
            double x = getGraphX(i) + mLemniscateParamX;
            double y = getGraphY(i) + mLemniscateParamY;

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

        //finds smallest ratio for which lemniscate should be resized because of stroke width
        //it's not just 1 * mStrokeWidth because it's behavior is strange for tick lines
        float ratio = mViewHeight/(mViewHeight + 2 * mStrokeWidth);

        //move every point for ratio
        x = x * ratio;
        y = y * ratio;

        //moves points so that lemniscate is centered
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
                if (mLineMinLength < mLineMaxLength && mIsLineLengthChangeable) {
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.end();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.strokeWidth = this.mStrokeWidth;
        ss.sizeMultiplier = this.mSizeMultiplier;
        ss.lineLength = this.mLineLength;
        ss.lineMinLength = this.mLineMinLength;
        ss.lineMaxLength = this.mLineMaxLength;
        ss.isLineLengthChangeable = this.mIsLineLengthChangeable;
        ss.color = this.mColor;
        ss.duration = this.mDuration;
        ss.hasHole = this.mHasHole;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
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
    }

    static class SavedState extends BaseSavedState {
        float strokeWidth;
        float sizeMultiplier;
        float lineLength;
        float lineMinLength;
        float lineMaxLength;
        boolean isLineLengthChangeable;
        int color;
        long duration;
        boolean hasHole;


        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
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
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
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
        mPaint.setColor(color);
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

    public double getT(int i) {
        return i*2*Math.PI/mPrecision;
    }
}