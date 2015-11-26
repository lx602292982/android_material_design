package com.lixiang.weather.support.utils.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BlurUtils {
	public static Drawable getBlurDrawable(Context context,int resId,int radius){
		Bitmap image = BitmapFactory.decodeResource(context.getResources(), resId);
		return getBlurDrawable(context, image, radius);
	}
	public static Drawable getBlurDrawable(Context context,Bitmap bitmap,int radius){
		StackBlurManager blurManager = new StackBlurManager(bitmap);
		BitmapDrawable drawable = new BitmapDrawable(context.getResources(),blurManager.process(radius));
		drawable.setColorFilter(Color.parseColor("#80000000"), PorterDuff.Mode.DARKEN);
		return drawable;
	}
}
