package com.wuruoye.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.wuruoye.library.R;
import com.wuruoye.library.util.ResourceUtil;

/**
 * @Created : wuruoye
 * @Date : 2018/5/24 22:05.
 * @Description : 可以左右旋转的 ImageView
 */
public class ChangeImageView extends android.support.v7.widget.AppCompatImageView {
    public static final int MAX_ALPHA = 255;
    public static final float DEFAULT_CLIP = 0.05F;
    public static final float DEFAULT_SCALE = 0.6F;
    public static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    // 圆形蒙版颜色
    private int mTint;
    // 勾选图片颜色
    private int mImgTint;
    // 勾选图片资源
    private int mImg;
    // 步进
    private float mClip;
    // 勾选图片缩放
    private float mScale;

    // 圆形画笔
    private Paint mPaint;
    private Drawable mOriginDrawable;
    private Drawable mImgDrawable;
    private Rect mAllRect;
    private Rect mCheckRect;
    private RectF mAllRectF;

    // 插值器
    private Interpolator mInterpolator = DEFAULT_INTERPOLATOR;
    private float mProgress;
    private int mLength;
    // 是否正在翻转
    private boolean mIsChecking;

    public ChangeImageView(Context context) {
        super(context);
        init();
    }

    public ChangeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttr(context, attrs);
        init();
    }

    public ChangeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChangeImageView);
        mTint = ta.getColor(R.styleable.ChangeImageView_civTint,
                ResourceUtil.getColorPrimary(context));
        mImgTint = ta.getColor(R.styleable.ChangeImageView_civImgTint,
                ResourceUtil.getColorAccent(context));
        mImg = ta.getResourceId(R.styleable.ChangeImageView_civImg, 0);
        mClip = ta.getFloat(R.styleable.ChangeImageView_civClip, DEFAULT_CLIP);
        mScale = ta.getFloat(R.styleable.ChangeImageView_civScale, DEFAULT_SCALE);
        ta.recycle();
    }

    // 初始化
    private void init() {
        mAllRect = new Rect();
        mAllRectF = new RectF();
        mCheckRect = new Rect();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTint);

        setImgResource(mImg);
        initDrawable();
    }

    // 勾选开始
    public void select() {
        mProgress = 0;
        mIsChecking = true;
        postInvalidate();
    }

    // 返回开始
    public void back() {
        mProgress = 1;
        mIsChecking = false;
        postInvalidate();
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setTint(int tint) {
        mTint = tint;
        mPaint.setColor(tint);
    }

    public void setImgTint(int tint) {
        mImgTint = tint;
        DrawableCompat.setTint(mImgDrawable, tint);
    }

    private void setImgResource(int resource) {
        mImgDrawable = AppCompatResources.getDrawable(getContext(), resource);
        DrawableCompat.setTint(mImgDrawable, mImgTint);
    }

    private void setImgDrawable(Drawable drawable) {
        mImgDrawable = drawable;
        DrawableCompat.setTint(mImgDrawable, mImgTint);
    }

    private void setImgBitmap(Bitmap bitmap) {
        mImgDrawable = new BitmapDrawable(getResources(), bitmap);
        DrawableCompat.setTint(mImgDrawable, mImgTint);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        initDrawable();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initDrawable();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        initDrawable();
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        initDrawable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取插值后的 mProgress
        float progress = getProgress();

        canvas.save();
        int width = canvas.getWidth() / 2;
        if (progress < 0.5) {
            // 0 ~ 0.5 -> 1 ~ 0
            // 画原图片
            progress = beforeFirst(progress);
            canvas.scale(progress, 1, width, width);

            drawOriginImg(canvas);
        }else {
            // 0.5 ~ 1 -> 0 ~ 1
            // 画勾选图片
            progress = beforeSecond(progress);
            canvas.scale(progress, 1, width, width);

            canvas.drawOval(mAllRectF, mPaint);
            drawCheckImg(progress, canvas);
        }
        canvas.restore();

        // 更新 mProgress
        afterDraw();
    }

    private void initDrawable() {
        if (getDrawable() != null) {
            mOriginDrawable = getDrawable();
            setImageDrawable(null);
        }
    }

    private float getProgress() {
        return mInterpolator.getInterpolation(mProgress);
    }

    private float beforeFirst(float progress) {
        return 1 - progress * 2;
    }

    private float beforeSecond(float progress) {
        return progress * 2 - 1;
    }

    private void drawOriginImg(Canvas canvas) {
        if (mOriginDrawable != null) {
            mOriginDrawable.setBounds(mAllRect);
            mOriginDrawable.draw(canvas);
        }
    }

    private void drawCheckImg(float progress, Canvas canvas) {
        if (mImgDrawable != null) {
            mImgDrawable.setAlpha(getAlpha(progress));

            int w = getWidth() / 2;
            int width = (int) (w * mScale * progress);
            mCheckRect.set(w - width, w - width, w + width, w + width);
            mImgDrawable.setBounds(mCheckRect);

            mImgDrawable.draw(canvas);
        }
    }

    private int getAlpha(float progress) {
        if (progress > 1) {
            progress = 1;
        }else if (progress < 0) {
            progress = 0;
        }

        return (int) (progress * MAX_ALPHA);
    }

    private void afterDraw() {
        if (mIsChecking) {
            mProgress += mClip;
            if (mProgress < 1) {
                postInvalidate();
            }else {
                if (mProgress - 1 < mClip) {
                    mProgress = 1;
                    postInvalidate();
                }
            }
        }else {
            mProgress -= mClip;
            if (mProgress > 0) {
                postInvalidate();
            }else {
                if (mProgress > -mClip) {
                    mProgress = 0;
                    postInvalidate();
                }
            }
        }
    }

    // 保证控件是个方形
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec > heightMeasureSpec) {
            widthMeasureSpec = heightMeasureSpec;
        }else if (heightMeasureSpec > widthMeasureSpec) {
            heightMeasureSpec = widthMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int l = w / 4;
        mAllRectF.set(0, 0, w, w);
        mAllRect.set(0, 0, w, w);
        mCheckRect.set(l, l, w - l, w - l);
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
