package com.lixiang.weather.support.utils;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lixiang.weather.config.InitHelper;

public class FlowImageUtils {
	
//	public static void setImageView(Context context, final int width,
//			final FlowLayout fview, List<String> imgs) {
//		// final int newWidth = width - ScreenUtils.dip2px(context, 36);
//		int tr = ScreenUtils.dip2px(context, 2);
//		for (int i = 0; i < fview.getChildCount(); i++) {
//			ImageView imgView = (ImageView) fview.getChildAt(i);
//			if (i >= imgs.size()) {
//				imgView.setVisibility(View.GONE);
//				continue;
//			}
//			imgView.setVisibility(View.VISIBLE);
//			imgView.setAdjustViewBounds(true);
//			imgView.setBackgroundResource(R.color.grey_c);
//			imgView.setOnClickListener(new ImagePreviewListener(context, i,imgs));
//			String imgSize = "-b.5.5.webp";
//			imgView.setScaleType(ScaleType.CENTER_CROP);
//			if (imgs.size() == 1) {
//				// imgView.setLayoutParams(new
//				// FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
//				// FlowLayout.LayoutParams.WRAP_CONTENT));
//				String imgUrl = imgs.get(i).trim();
//				int start = imgUrl.lastIndexOf("_");
//				int end = imgUrl.lastIndexOf(".");
//				if (start != -1 && end != -1) {
//					String[] wh = imgUrl.substring(start + 1, end).split(":");
//					int oWidth = Integer.parseInt(wh[0]);
//					int oHeight = Integer.parseInt(wh[1]);
//
//					float nWidth = oWidth;
//					float nHeight = oHeight;
//					if (oWidth >= oHeight && oWidth >= width) {
//						nWidth = width;
//						float scale = nWidth / oWidth;
//						nHeight = (int) (oHeight * scale);
//					} else if (oHeight >= width) {
//						nHeight = width;
//						float scale = nHeight / oHeight;
//						nWidth = (int) (oWidth * scale);
//					}
//					imgView.setLayoutParams(new FlowLayout.LayoutParams(
//							(int) nWidth, (int) nHeight));
//					InitHelper.getImageLoader().display(
//							imgs.get(i).trim() + imgSize, imgView);
//				} else {
//					InitHelper.getImageLoader().display(
//							imgs.get(i).trim() + imgSize, imgView,
//							new ImageLoaderListener() {
//								@Override
//								public void onLoadingComplete(String imageUri,
//										View view, Bitmap loadedImage) {
//									ImageView image = ((ImageView) view);
//									if (loadedImage.getWidth() > width / 3 * 2) {
//										image.setLayoutParams(new FlowLayout.LayoutParams(
//												width / 3 * 2,
//												FlowLayout.LayoutParams.WRAP_CONTENT));
//									} else if (loadedImage.getHeight() > width / 3 * 2) {
//										image.setLayoutParams(new FlowLayout.LayoutParams(
//												FlowLayout.LayoutParams.WRAP_CONTENT,
//												width / 3 * 2));
//									} else {
//										image.setLayoutParams(new FlowLayout.LayoutParams(
//												FlowLayout.LayoutParams.WRAP_CONTENT,
//												FlowLayout.LayoutParams.WRAP_CONTENT));
//									}
//									image.setImageBitmap(loadedImage);
//								}
//							});
//				}
//			} else {
//				imgView.setScaleType(ScaleType.CENTER_CROP);
//				// String imgSize =
//				// "?imageView2/2/w/"+(int)(width/(imgs.size()==2?2:3))+"/q/85";
//				int imgWidth = width / 3 + (imgs.size() == 4 ? tr * 2 : 0);
//				FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
//						imgWidth, imgWidth);
//				params.setMargins(0, tr, tr, 0);
//				imgView.setLayoutParams(params);
//				InitHelper.getImageLoader().display(
//						imgs.get(i).trim() + imgSize, imgView);
//			}
//		}
//	}

//	public static void setOneImageSize(ImageView imageView, String url,int width) {
//		String imgSize = "-b.5.5.webp";
//		int start = url.lastIndexOf("_");
//		int end = url.lastIndexOf(".");
//		if (start != -1 && end != -1) {
//			String[] wh = url.substring(start + 1, end).split(":");
//			int oWidth = Integer.parseInt(wh[0]);
//			int oHeight = Integer.parseInt(wh[1]);
//
//			float nWidth = oWidth;
//			float nHeight = oHeight;
//			if (oWidth >= oHeight && oWidth >= width) {
//				nWidth = width;
//				float scale = nWidth / oWidth;
//				nHeight = (int) (oHeight * scale);
//			} else if (oHeight >= width) {
//				nHeight = width;
//				float scale = nHeight / oHeight;
//				nWidth = (int) (oWidth * scale);
//			}
//			imageView.setLayoutParams(new ViewGroup.LayoutParams((int) nWidth,(int) nHeight));
//			InitHelper.getImageLoader().display(url + imgSize,imageView);
//		}else{
//			imageView.setLayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,FlowLayout.LayoutParams.WRAP_CONTENT));
//			InitHelper.getImageLoader().display(url + imgSize,imageView);
//		}
//	}
	public static void setArticleImage(ImageView imageView, String url,int width){
		String imgSize = "-b.5.5.webp";
		int start = url.lastIndexOf("_");
		int end = url.lastIndexOf(".");
		if (start != -1 && end != -1) {
			String[] wh = url.substring(start + 1, end).split(":");
			int oWidth = Integer.parseInt(wh[0]);
			int oHeight = Integer.parseInt(wh[1]);
			
			
			float nWidth = oWidth;
			float nHeight = oHeight;
			nWidth = width;
			float scale = nWidth / oWidth;
			nHeight = (int) (oHeight * scale);
//			if (oWidth >= oHeight && oWidth >= width) {
//			} else if (oHeight >= width) {
//				nHeight = width;
//				float scale = nHeight / oHeight;
//				nWidth = (int) (oWidth * scale);
//			}
			imageView.setLayoutParams(new LinearLayout.LayoutParams((int) nWidth,(int) nHeight));
			InitHelper.getImageLoader().display(url + imgSize,imageView);
		}else{
			imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			InitHelper.getImageLoader().display(url + imgSize,imageView);
		}
	}
	
	public static String getOneImageWidthHeight(String imgUrl){
		if(TextUtils.isEmpty(imgUrl)){
			return 0+","+0;
		}
		int start = imgUrl.lastIndexOf("_");
		int end = imgUrl.lastIndexOf(".");
		if (start != -1 && end != -1) {
			String[] wh = imgUrl.substring(start + 1, end).split(":");
			int oWidth = Integer.parseInt(wh[0]);
			int oHeight = Integer.parseInt(wh[1]);
			return (int)oWidth+","+(int)oHeight;
		}
		return 0+","+0;
	}
	
}
