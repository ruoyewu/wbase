package com.wuruoye.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wuruoye.library.R;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class HeartBeatView extends View {
    public static final float C = 0.551915024494f;

    private int mColor;
    private int mDuration;
    private int mCount;
    private float mPress;
    private float mPiece;
    private float mCurrent;
    private float mStep;

    private int mCenterX;
    private int mCenterY;

    private Paint mPaint;
    private float[] mData = new float[8];
    private float[] mCtrl = new float[16];

    private int mDir = 1;

    public HeartBeatView(Context context) {
        super(context);
        init(null);
    }

    public HeartBeatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HeartBeatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            initValue();
        }else {
            getAttr(attrs);
        }

        initSize();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
    }

    private void initSize() {
        float radius = mCenterX;
        float difference = radius * C;

        mData[0] = 0;
        mData[1] = radius;
        mData[2] = radius;
        mData[3] = 0;
        mData[4] = 0;
        mData[5] = -radius;
        mData[6] = -radius;
        mData[7] = 0;

        mCtrl[0] = mData[0] + difference;
        mCtrl[1] = mData[1];
        mCtrl[2] = mData[2];
        mCtrl[3] = mData[3] + difference;
        mCtrl[4] = mData[2];
        mCtrl[5] = mData[3] - difference;
        mCtrl[6] = mData[4] + difference;
        mCtrl[7] = mData[5];
        mCtrl[8] = mData[4] - difference;
        mCtrl[9] = mData[5];
        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7] - difference;
        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7] + difference;
        mCtrl[14] = mData[0] - difference;
        mCtrl[15] = mData[1];

        mPiece = mDuration / mCount;
        mStep = mCenterX / mPress;
        mCurrent = 0;
        mDir = 1;
    }

    private void initValue() {
        mColor = Color.BLACK;
        mDuration = 1000;
        mCount = 30;
        mPress = 9;
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.HeartBeatView);
        mColor = array.getColor(R.styleable.HeartBeatView_heartColor, Color.BLACK);
        mDuration = array.getInt(R.styleable.HeartBeatView_duration, 1000);
        mCount = array.getInt(R.styleable.HeartBeatView_count, 30);
        mPress = array.getFloat(R.styleable.HeartBeatView_press, 9);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mCenterY, mCenterX);
        canvas.scale(1, -1);

        @SuppressLint("DrawAllocation")
        Path path = new Path();
        path.moveTo(mData[0], mData[1]);

        path.cubicTo(mCtrl[0], mCtrl[1], mCtrl[2], mCtrl[3], mData[2], mData[3]);
        path.cubicTo(mCtrl[4], mCtrl[5], mCtrl[6], mCtrl[7], mData[4], mData[5]);
        path.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0], mData[1]);

        canvas.drawPath(path, mPaint);

        mCurrent += mPiece * mDir;
        if (mCurrent >= mDuration) {
            mDir = -mDir;
        }else if (mCurrent <= 0) {
            mDir = -mDir;
        }

        mData[1] -= mStep * 7 / mCount * mDir;
        mCtrl[7] += mStep * 5 / mCount * mDir;
        mCtrl[9] += mStep * 5 / mCount * mDir;
        mCtrl[4] -= mStep / mCount * mDir;
        mCtrl[10] += mStep / mCount * mDir;

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > h) {
            super.onSizeChanged(h, h, oldw, oldh);
        }else {
            super.onSizeChanged(w, w, oldw, oldh);
        }
        mCenterX = w / 2;
        mCenterY = h / 2;
        initSize();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setPress(float press) {
        mPress = press;
        initSize();
    }

    public void setCount(int count) {
        mCount = count;
        initSize();
    }

    public void setDuration(int duration) {
        mDuration = duration;
        initSize();
    }

    public int getColor() {
        return mColor;
    }

    public float getPress() {
        return mPress;
    }

    public int getCount() {
        return mCount;
    }

    public int getDuration() {
        return mDuration;
    }
}
