package com.wuruoye.library.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.wuruoye.library.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/2/14.
 * this file is to
 */

public class SmartLinearLayout extends ViewGroup {
    private List<View> mChildren = new ArrayList<>();

    private int mOrientation;

    public SmartLinearLayout(Context context) {
        super(context);
    }

    public SmartLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = MeasureSpec.getSize(getMeasuredWidth());
        int height = MeasureSpec.getSize(getMeasuredHeight())
                - getPaddingStart() - getPaddingEnd();
        int currentWidth = 0;
        int currentHeight = 0;
        int oneHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec((1<<30) - 1,
                    MeasureSpec.AT_MOST);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec((1<<30) - 1,
                    MeasureSpec.AT_MOST);
            view.measure(widthMeasureSpec, heightMeasureSpec);
            int w = view.getMeasuredWidth() + (int)DensityUtil.dp2px(getContext(), 10F);
            int h = view.getMeasuredHeight() + (int)DensityUtil.dp2px(getContext(), 10F);
            if (currentWidth + w <= width) {
                int lv = currentWidth + (int)DensityUtil.dp2px(getContext(), 5F)
                        + getPaddingStart();
                int tv = currentHeight + (int)DensityUtil.dp2px(getContext(), 5F)
                        + getPaddingTop();
                int rv = lv + view.getMeasuredWidth();
                int bv = tv + view.getMeasuredHeight();
                view.layout(lv, tv, rv, bv);
                currentWidth += w;
                oneHeight = h;
            }else {
                currentHeight += h;
                currentWidth = 0;
                i --;
            }
            LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = currentHeight + oneHeight + getPaddingTop() + getPaddingBottom();
            setLayoutParams(layoutParams);
        }
    }

    public void setView(final List<View> viewList) {
        for (View v : viewList) {
            addView(v);
        }
    }
}
