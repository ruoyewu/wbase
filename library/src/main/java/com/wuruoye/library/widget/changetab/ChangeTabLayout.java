package com.wuruoye.library.widget.changetab;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.wuruoye.library.R;
import com.wuruoye.library.adapter.OnPageChangeListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/5/17 13:22.
 * @Description : 底部 tab 栏
 */

public class ChangeTabLayout extends LinearLayout {
    private List<ChangeTabItem> mTabItems = new ArrayList<>();
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    private int mNormalTextColor;
    private int mSelectTextColor;
    private int mNormalImgBorderColor;
    private int mSelectImgBorderColor;
    private int mNormalImgStuffColor;
    private int mSelectImgStuffColor;

    private ViewPager mViewPager;
    private int mCurrentTab;

    public ChangeTabLayout(Context context) {
        super(context);
        init();
    }

    public ChangeTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttr(context, attrs);
        init();
    }

    public ChangeTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttr(context, attrs);
        init();
    }

    public void attachViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        post(new Runnable() {
            @Override
            public void run() {
                initChild();
            }
        });
    }

    private void setAttr(Context context, AttributeSet attr) {
        TypedArray arr = context.obtainStyledAttributes(attr, R.styleable.ChangeTabLayout);
        mNormalTextColor = arr.getColor(R.styleable.ChangeTabLayout_normalTextColor, 0);
        mSelectTextColor = arr.getColor(R.styleable.ChangeTabLayout_selectTextColor, 0);
        mNormalImgBorderColor = arr.getColor(R.styleable.ChangeTabLayout_normalBorderColor, 0);
        mSelectImgBorderColor = arr.getColor(R.styleable.ChangeTabLayout_selectBorderColor, 0);
        mNormalImgStuffColor = arr.getColor(R.styleable.ChangeTabLayout_normalStuffColor, 0);
        mSelectImgStuffColor = arr.getColor(R.styleable.ChangeTabLayout_selectStuffColor, 0);
        arr.recycle();
    }

    private void initChild() {
        int childCount = getChildCount();
        if (mViewPager != null && mViewPager.getAdapter().getCount() != childCount) {
            throw new IllegalArgumentException("Viewpager's size must be equal to the size of" +
                    "ChangeTabItems");
        }

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof ChangeTabItem) {
                ChangeTabItem item = (ChangeTabItem) view;
                mTabItems.add(item);
                if (mViewPager != null) {
                    item.setTitle(mViewPager.getAdapter().getPageTitle(i).toString());
                    mViewPager.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
                        @Override
                        public void onPageSelected(int position) {
                            onPageChanged(position);
                        }

                        @Override
                        public void onPageScrolled(int position, float positionOffset,
                                                   int positionOffsetPixels) {
                            onPageSlide(position, positionOffset);
                        }
                    });
                }
                final int finalI = i;
                item.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onTabClick(finalI);
                    }
                });
            }else {
                throw new IllegalStateException("ChangeTabLayout's children must be ChangeTabItem");
            }
        }

        changeTabColor();
    }

    private void onTabClick(int i) {
        if (mViewPager == null) {
            onPageChanged(i);
        }else {
            mViewPager.setCurrentItem(i, true);
        }
    }

    private void onPageSlide(int position, float offset) {
        if (position < mTabItems.size() - 1) {
            int left = position;
            int right = position + 1;
            float leftProgress = 1 - offset;
            float rightProgress = offset;
            int[] leftColor = getColor(leftProgress);
            int[] rightColor = getColor(rightProgress);
            if (leftColor != null && rightColor != null) {
                mTabItems.get(left).setColor(leftColor[0], leftColor[1],
                        leftColor[2], leftProgress);
                mTabItems.get(right).setColor(rightColor[0], rightColor[1],
                        rightColor[2], rightProgress);
            }
            for (int i = 0; i < mTabItems.size(); i++) {
                if (i != left && i != right) {
                    mTabItems.get(i).setColor(mNormalTextColor, mNormalImgBorderColor,
                            mNormalImgStuffColor, 0F);
                }
            }
        }
    }

    private void onPageChanged(int position) {
        mCurrentTab = position;
    }

    private int[] getColor(float progress) {
        int textColor;
        int borderColor;
        int stuffColor;
        float dProgress;
        if (progress < 0.5 && progress > 0) {
            dProgress = progress * 2;
            textColor = (int) mArgbEvaluator.evaluate(dProgress, mNormalTextColor,
                    mSelectTextColor);
            borderColor = (int) mArgbEvaluator.evaluate(dProgress, mNormalImgBorderColor,
                    mSelectImgBorderColor);
            stuffColor = mNormalImgStuffColor;
            return new int[] {textColor, borderColor, stuffColor};
        }else if (progress > 0.5 && progress < 1) {
            dProgress = progress * 2 - 1;
            textColor = mSelectTextColor;
            borderColor = mSelectImgBorderColor;
            stuffColor = (int) mArgbEvaluator.evaluate(dProgress, mNormalImgStuffColor,
                    mSelectImgStuffColor);
            return new int[] {textColor, borderColor, stuffColor};
        }
        return null;
    }

    private void changeTabColor() {
        for (int i = 0; i < mTabItems.size(); i++) {
            if (i == mCurrentTab) {
                mTabItems.get(i).setColor(mSelectTextColor, mSelectImgBorderColor,
                        mSelectImgStuffColor, 1F);
            }else {
                mTabItems.get(i).setColor(mNormalTextColor, mNormalImgBorderColor,
                        mNormalImgStuffColor, 0F);
            }
        }
    }
}
