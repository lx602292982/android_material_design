package com.lixiang.weather.support.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.view.Display;
import android.view.View;

public class ImageUtils {
	/**
	 * @Title: imageZoom
	 * @Description: TODO(根据质量大小压缩)
	 * @param @param path
	 * @param @param maxSize
	 * @param @return 设定文件
	 * @return Bitmap 返回类型
	 * @throws
	 */
	public static Bitmap imageZoom(String path, Double maxSize) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		double mid = b.length / 1024;
		if (mid > maxSize) {
			double i = mid / maxSize;
			bitmap = getSmallBitmap(path, bitmap.getWidth() / Math.sqrt(i),
					bitmap.getHeight() / Math.sqrt(i));
		}
		return bitmap;
	}
	/**
	 * @Title: getSmallBitmap
	 * @Description: TODO(根据长宽比例压缩)
	 * @param @param filePath
	 * @param @param width
	 * @param @param height
	 * @param @return 设定文件
	 * @return Bitmap 返回类型
	 * @throws
	 */
	public static Bitmap getSmallBitmap(String filePath, double width,
			double height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now  
        Bitmap bitmap = BitmapFactory.decodeFile(filePath,options);
          
        options.inJustDecodeBounds = false;  

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, width, height);

		return rotateBitmap(BitmapFactory.decodeFile(filePath, options),readPictureDegree(filePath));
	}
	/**
	 * @Title: calculateInSampleSize
	 * @Description: TODO(计算图片缩放值)
	 * @param @param options
	 * @param @param reqWidth
	 * @param @param reqHeight
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			double reqWidth, double reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
//		
//		if (height > reqHeight || width > reqWidth) {
//			inSampleSize = Math.max(Math.round((float) height/ (float) reqHeight), Math.round((float) width / (float) reqWidth));
//			final int heightRatio = Math.round((float) height/ (float) reqHeight);
//			final int widthRatio = Math.round((float) width / (float) reqWidth);
//			inSampleSize = heightRatio < widthRatio ? heightRatio:widthRatio;
//		}
		if (width > height && width > reqWidth) {//如果宽度大的话根据宽度固定大小缩放  
			inSampleSize = (int) (width / reqWidth);  
        } else if (width < height && height > reqHeight) {//如果高度高的话根据宽度固定大小缩放  
        	inSampleSize = (int) (width / reqHeight);  
        } 
		if (inSampleSize <= 0) inSampleSize = 1;
		return inSampleSize;
	}

	/**
	 * @Title: readPictureDegree
	 * @Description: TODO(获取旋转角度)
	 * @param @param path
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	public static Bitmap rotateImage(String path){
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		return rotateBitmap(bitmap, readPictureDegree(path));
	}
	/**
	 * @Title: rotateBitmap
	 * @Description: TODO(旋转图片)
	 * @param @param bitmap
	 * @param @param degress
	 * @param @return 设定文件
	 * @return Bitmap 返回类型
	 * @throws
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
		if (bitmap != null && degress != 0) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}
	
	public Bitmap combineImagesLR(Bitmap c, Bitmap s) {
		Bitmap cs = null;

		int width, height = 0;

		if (c.getWidth() > s.getWidth()) {
			width = c.getWidth() + s.getWidth();
			height = c.getHeight();
		} else {
			width = s.getWidth() + s.getWidth();
			height = c.getHeight();
		}

		cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas comboImage = new Canvas(cs);

		comboImage.drawBitmap(c, 0f, 0f, null);
		comboImage.drawBitmap(s, c.getWidth(), 0f, null);

		return cs;
	}

	/**
	 * 上下连接拼图
	 * @param c
	 * @param s
	 * @return
	 */
	public static Bitmap combineImagesUpDown(Bitmap c, Bitmap s) { 
		Bitmap cs = null;

		int width, height = 0;
		
		width = c.getWidth();
		height = c.getHeight() + s.getHeight();

		if (c.getWidth() < s.getWidth()) {
			float scale = (float)c.getWidth() / s.getWidth();
			s = scaleBitmap(s,scale);
		}

		cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas comboImage = new Canvas(cs);
		Paint p = new Paint();
		p.setColor(Color.rgb(0x25, 0xb6, 0xed));//25b6ed

		comboImage.drawBitmap(c, (width - c.getWidth())/2, 0f, null);
		comboImage.drawRect(0, height - s.getHeight(), width, height, p);
		comboImage.drawBitmap(s, (width - s.getWidth())/2, c.getHeight(), null);
		
		c.recycle();
		s.recycle();

		return cs;
	}
	
	/**
	 * 第二张图片覆盖第一张 下边
	 * (前提 第二张图必须比第一张 矮)
	 * @param c
	 * @param s
	 * @return
	 */
	public static Bitmap combineImagesAlienDown(Bitmap c, Bitmap s) { 
		Bitmap cs = null;

		int width, height = 0;
		
		width = c.getWidth();
		height = c.getHeight();
		
//		if (c.getWidth() < s.getWidth()) {
//			float scale = (float)c.getWidth() / s.getWidth();
//			s = scaleBitmap(s,scale);
//		}

		cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas comboImage = new Canvas(cs);
		Paint p = new Paint();
		p.setColor(Color.rgb(0x25, 0xb6, 0xed));//25b6ed

		comboImage.drawBitmap(c,(width - c.getWidth())/2, 0f, null);
		comboImage.drawRect(0, height - s.getHeight(), width, height, p);
		comboImage.drawBitmap(s, (width - s.getWidth())/2, height - s.getHeight(), null);

		c.recycle();
		s.recycle();
		
		return cs;
	}
	
	@SuppressWarnings("deprecation")
	public static Bitmap getShot(Activity activity,View view) {
		// 获取windows中最顶层的view
//		View view = activity.getWindow().getDecorView();
//		View view = mPopupWindow.getContentView();
		view.buildDrawingCache();

		// 获取状态栏高度
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		Display display = activity.getWindowManager().getDefaultDisplay();

		// 获取屏幕宽和高
		int widths = display.getWidth();
		int heights = display.getHeight();

		// 允许当前窗口保存缓存信息
		view.setDrawingCacheEnabled(true);

		// 去掉状态栏
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
				statusBarHeights, widths, heights - statusBarHeights);

		// 销毁缓存信息
		view.destroyDrawingCache();

		return bmp;
	}
	
	
	public static Bitmap scaleBitmap(Bitmap bitmap, float size) {
		Matrix matrix = new Matrix();
		matrix.postScale(size, size); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		bitmap.recycle();
		return resizeBmp;
	}
}
