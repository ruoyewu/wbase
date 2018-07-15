package com.wuruoye.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

/**
 * Created by wuruoye on 2018/2/7.
 * this file is to
 */

public class BitmapUtil {
    public static Bitmap roundCrop(Bitmap source, float radius) {
        if (source == null) return null;

        Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    public static Bitmap blur(Context context, Bitmap bitmap) {
        float scaleRatio = bitmap.getWidth() / 100;
        scaleRatio = scaleRatio < 1 ? 1 : scaleRatio;
        int width = (int) Math.round(bitmap.getWidth() * 1.0 / scaleRatio);
        int height = (int) Math.round(bitmap.getHeight() * 1.0 / scaleRatio);

        Bitmap image = Bitmap.createScaledBitmap(bitmap, width, height, false);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation input = Allocation.createFromBitmap(rs, image);
        Allocation output = Allocation.createTyped(rs, input.getType());

        blur.setRadius(5F);
        blur.setInput(input);
        blur.forEach(output);
        output.copyTo(image);

        input.destroy();
        output.destroy();
        blur.destroy();
        rs.destroy();

        return image;
    }

    public static Bitmap rotationY(Context context, Bitmap bitmap, float rotation) {
        Matrix matrix = new Matrix();
        matrix.postScale(1, rotation);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    public static Bitmap rotationX(Context context, Bitmap bitmap, float rotation) {
        Matrix matrix = new Matrix();
        matrix.postScale(rotation, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
    }

    public static Bitmap scale(Context context, Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap getFromView(View view) {
        return null;
    }

    public static Bitmap getFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }else {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE
                    ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);
            return bitmap;
        }
    }
}
