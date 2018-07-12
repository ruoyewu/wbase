package com.wuruoye.library.widget.changetab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuruoye.library.R;

/**
 * @Created : wuruoye
 * @Date : 2018/5/17 13:09.
 * @Description : 底部 tab 项
 */

public class ChangeTabItem extends LinearLayout {
    private ImageView iv;
    private TextView tv;

    private int icon;
    private int iconSelect;
    private String text;
    private int imgLength;
    private float textSize;

    private Drawable mBorder;
    private Drawable mStuff;

    public ChangeTabItem(Context context) {
        super(context);
        init();
    }

    public ChangeTabItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttr(context, attrs);
        init();
    }

    public ChangeTabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttr(context, attrs);
        init();
    }

    public void setColor(int textColor, int imgBorColor, int imgStuColor, float progress) {
        tv.setTextColor(textColor);
        iv.setImageDrawable(getDrawable(imgBorColor, imgStuColor, progress));
    }

    public void setTitle(String title) {
        tv.setText(title);
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.item_change_tab, this);
        iv = findViewById(R.id.iv_change_tab);
        tv = findViewById(R.id.tv_change_tab);

        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
        layoutParams.width = imgLength;
        layoutParams.height = imgLength;
        iv.setLayoutParams(layoutParams);
        iv.setImageResource(icon);

        tv.setTextSize(textSize);
        tv.setText(text);

        mBorder = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), icon));
        mStuff = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), iconSelect));
    }

    private void setAttr(Context context, AttributeSet attr) {
        TypedArray arr = context.obtainStyledAttributes(attr, R.styleable.ChangeTabItem);
        text = arr.getString(R.styleable.ChangeTabItem_text);
        icon = arr.getResourceId(R.styleable.ChangeTabItem_icon, 0);
        iconSelect = arr.getResourceId(R.styleable.ChangeTabItem_iconSelect, 0);
        textSize = arr.getDimension(R.styleable.ChangeTabItem_textSize, 13);
        imgLength = arr.getDimensionPixelSize(R.styleable.ChangeTabItem_imgLength, 80);
        arr.recycle();
    }

    private Drawable getDrawable(int borColor, int stuColor, float process) {
        if (process <= 0.5) {
            DrawableCompat.setTint(mBorder, borColor);
            return mBorder;
        }else {
            DrawableCompat.setTint(mBorder, borColor);
            DrawableCompat.setTint(mStuff, stuColor);
            return new LayerDrawable(new Drawable[]{mBorder, mStuff});
        }
    }
}
