package com.wuruoye.library.widget.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Created : wuruoye
 * @Date : 2018/5/6 10:56.
 * @Description : 显示图表的 view
 */

public class SortView extends View {
    public static final int AIDL = 0;
    public static final int GENERATION = 1;
    public static final int CLEAR = 2;
    public static final int LINE_MOVE = 3;
    public static final int BOX_MOVE = 4;

    public static final int UP = 1;
    public static final int DOWN = -1;

    private int[] mOldValues;
    private int[] mValues;
    private int mBgColor = Color.BLUE;
    private int mBorderColor = Color.WHITE;
    private int mTextColor = Color.BLACK;
    private int mLineColor = Color.GRAY;
    private int mBorderWidth = 2;
    private float mTextSize = 25F;
    private float mRx = 5F;
    private float mRy = 5F;
    private int mTopSpace = 50;
    private int mFrequency = 15;


    private int mState = AIDL;
    private boolean mValuesChanged;
    private boolean mShowLine;

    private ReentrantLock mLock;
    private Condition mLineLock;

    private float mSplitHeight;
    private float mSplitWidth;

    private Paint mBgPaint;
    private Paint mBorderPaint;
    private Paint mTextPaint;
    private Paint mLinePaint;
    private RectF mRect = new RectF();

    // generation
    private float mGenerationProcess;

    // move line
    private float mProcess1;
    private float mProcess2;

    public SortView(Context context) {
        super(context);
        init();
    }

    public SortView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setValues(int[] values) {
        if (mValues != null) {
            mState = CLEAR;
            mValuesChanged = false;
            mGenerationProcess = 1;
        }else {
            mState = GENERATION;
            mValuesChanged = true;
            mGenerationProcess = 0;
        }
        mOldValues = mValues;
        mValues = values;
        invalidate();
    }

    public void doBubble() {
        if (mGenerationProcess == 1 && mState == GENERATION) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mValuesChanged) {
            computeSplit();
            mValuesChanged = false;
        }

        if (mState == CLEAR) {
            generateValues(canvas, DOWN, mOldValues);
        }else if (mState == GENERATION){
            generateValues(canvas, UP, mValues);
        }else if (mState == LINE_MOVE) {

        }
    }

    private void init() {
        mLock = new ReentrantLock();
        mLineLock = mLock.newCondition();

        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(mBgColor);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mTextColor);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(mLineColor);
    }

    private void generateValues(Canvas canvas, int direct, int[] values) {
        mGenerationProcess += (1F / mFrequency) * direct;
        alignProgress();
        for (int i = 0; i < values.length; i++) {
            int alpha = (int) (mGenerationProcess * 255);
            mBgPaint.setAlpha(alpha);
            mBorderPaint.setAlpha(alpha);
            mTextPaint.setAlpha(alpha);
            float left = i * mSplitWidth;
            float right = left + mSplitWidth;
            float height = values[i] * mSplitHeight * mGenerationProcess;
            float bottom = getHeight();
            float top = bottom - height;
            drawBox(canvas, top, bottom, left, right, values[i]);
        }

        if ((direct > 0 && mGenerationProcess < 1) || (direct < 0 && mGenerationProcess > 0)) {
            invalidate();
        }else if (direct < 0) {
            mState = GENERATION;
            mValuesChanged = true;
            invalidate();
        }
    }

    private void moveLine(Canvas canvas, int from, int to, int position) {
        float process;
        if (position == 1) {
            process = mProcess1 += (1 / mFrequency);
        }else {
            process = mProcess2 += (1 / mFrequency);
        }
        alignProgress();
        float fromX = (float) (mSplitWidth * (from + 0.5));
        float toX = (float) (mSplitWidth * (to + 0.5));
        float x = (toX - fromX) * process + fromX;
        drawLine(canvas, x);
        if (process < 1) {
            invalidate();
        }
    }

    private void drawBox(Canvas canvas, float top, float bottom, float left, float right, int value) {
        mRect.set(left, top, right, bottom);
        canvas.drawRoundRect(mRect, mRx, mRy, mBgPaint);
        canvas.drawRoundRect(mRect, mRx, mRy, mBorderPaint);
        canvas.drawText("" + value, (left + right) / 2, (top + bottom) / 2, mTextPaint);
    }

    private void drawLine(Canvas canvas, float x) {
        canvas.drawLine(x, 0, x, getHeight(), mLinePaint);
    }

    private void computeSplit() {
        int max = getMax(mValues);
        int num = mValues.length;
        int height = getHeight() - mTopSpace;
        int width = getWidth();

        mSplitHeight = height * 1F / max;
        mSplitWidth = width * 1F / num;
    }

    private int getMax(int[] values) {
        int max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (max < values[i]) {
                max = values[i];
            }
        }
        return max;
    }

    private void alignProgress() {
        if (mGenerationProcess > 1F) {
            mGenerationProcess = 1F;
        }else if (mGenerationProcess < 0F) {
            mGenerationProcess = 0;
        }
    }
}
