package com.wuruoye.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
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

    public static Bitmap getFromView(View view) {
        return null;
    }
}
