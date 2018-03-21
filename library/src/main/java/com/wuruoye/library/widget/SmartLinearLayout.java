package com.wuruoye.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wuruoye.library.R;

/**
 * Created by wuruoye on 2018/2/14.
 * this file is to
 */

public class SmartLinearLayout extends ViewGroup {
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 0;

    private int mOrientation;

    public SmartLinearLayout(Context context) {
        super(context);
    }

    public SmartLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttr(attrs);
    }

    public SmartLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(attrs);
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SmartLinearLayout);
        mOrientation = array.getInt(R.styleable.SmartLinearLayout_orientation, VERTICAL);
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        getMeasuredHeight();
        getMeasuredWidth();
        int width = getWidth() - getPaddingStart() - getPaddingEnd();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();

        int currentWidth = 0;
        int currentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            MarginLayoutParams marginLP = (MarginLayoutParams) view.getLayoutParams();
            view.measure(view.getMeasuredWidth(), view.getMeasuredHeight());
            int w = MeasureSpec.getSize(view.getMeasuredWidth()) +
                    marginLP.leftMargin + marginLP.rightMargin;
            int h = MeasureSpec.getSize(view.getMeasuredHeight()) +
                    marginLP.topMargin + marginLP.bottomMargin;
            if (mOrientation == VERTICAL) {
                if (currentWidth + w <= width) {
                    int lv = currentWidth + marginLP.leftMargin + getPaddingStart();
                    int tv = currentHeight + marginLP.topMargin + getPaddingTop();
                    int rv = lv + MeasureSpec.getSize(view.getMeasuredWidth());
                    int bv = tv + MeasureSpec.getSize(view.getMeasuredHeight());
                    view.layout(lv, tv, rv, bv);
                    currentWidth += w;
                }else {
                    currentHeight += h;
                    currentWidth = 0;
                    i --;
                }
            }else {
                if (currentHeight + h <= height) {
                    int lv = currentWidth + marginLP.leftMargin + getPaddingStart();
                    int tv = currentHeight + marginLP.topMargin + getPaddingTop();
                    int rv = lv + MeasureSpec.getSize(view.getMeasuredWidth());
                    int bv = tv + MeasureSpec.getSize(view.getMeasuredHeight());
                    view.layout(lv, tv, rv, bv);
                    currentHeight += h;
                }else {
                    currentWidth += w;
                    currentHeight = 0;
                    i --;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(getMeasuredWidth());
        int height = MeasureSpec.getSize(getMeasuredHeight());
        int currentWidth = 0;
        int currentHeight = 0;
        boolean isNewLine = true;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            MarginLayoutParams marginLP = (MarginLayoutParams) view.getLayoutParams();
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            int w = MeasureSpec.getSize(view.getMeasuredWidth()) + marginLP.leftMargin
                    + marginLP.rightMargin;
            int h = MeasureSpec.getSize(view.getMeasuredHeight()) + marginLP.topMargin
                    + marginLP.bottomMargin;

            if (mOrientation == VERTICAL) {
                if (isNewLine) {
                    currentHeight += marginLP.topMargin + marginLP.bottomMargin + MeasureSpec.getSize(
                            view.getMeasuredHeight());
                    isNewLine = false;
                }
                if (currentWidth + w <= width || currentWidth == 0) {
                    currentWidth +=
                            w;
                }else {
                    currentWidth = 0;
                    isNewLine = true;
                    i --;
                }
            }else {
                if (isNewLine) {
                    currentWidth += marginLP.leftMargin + marginLP.rightMargin +
                            MeasureSpec.getSize(view.getMeasuredWidth());
                    isNewLine = false;
                }
                if (currentHeight + h <= height) {
                    currentHeight += h;
                }else {
                    currentHeight = 0;
                    isNewLine = true;
                    i --;
                }
            }
        }

        if (mOrientation == VERTICAL) {
            currentHeight += getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, currentHeight);
        }else if (mOrientation == HORIZONTAL){
            currentWidth += getPaddingStart() + getPaddingEnd();
            setMeasuredDimension(currentWidth, height);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
        invalidate();
    }
}
