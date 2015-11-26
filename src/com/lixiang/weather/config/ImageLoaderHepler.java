package com.lixiang.weather.config;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.lixiang.weather.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderHepler {
	private ImageLoader loader;
	private ImageLoaderConfiguration config;
	public ImageLoaderHepler(Context context,File diskCachePath) {
		config = new ImageLoaderConfiguration.Builder(context)
//        .memoryCacheExtraOptions(480, 800) //缓存最大长宽
//        .diskCacheExtraOptions(480, 800, null)
//        .taskExecutor(...)
//        .taskExecutorForCachedImages(...)
        .threadPoolSize(3) // default
        .threadPriority(Thread.NORM_PRIORITY - 2) // default
        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(20 * 1024 * 1024))
        .memoryCacheSize(20 * 1024 * 1024)
        .memoryCacheSizePercentage(13) // default
        .diskCache(new UnlimitedDiskCache(diskCachePath)) // default
        .diskCacheSize(50 * 1024 * 1024)
        .diskCacheFileCount(100)
        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
        .imageDownloader(new BaseImageDownloader(context)) // default 
//        .imageDecoder(new BaseImageDecoder(loggingEnabled)) // default
        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
        .writeDebugLogs()
        .build();
		loader = ImageLoader.getInstance();
		loader.init(config);
	}
	
	public void setConfig(ImageLoaderConfiguration configuration){
		if(configuration!=null){
			this.config = configuration;
		}
	}
//	public DisplayImageOptions getPreviewDisplayOptions(){
//		return setDisplayOptions(R.color.toum);
//	}
	public DisplayImageOptions getDefaultDisplayOptions(){
		return setDisplayOptions(R.color.grey_c);
	}
//	public DisplayImageOptions getUserDisplayOptions(){
//		return setDisplayOptions(R.drawable.ic_user_fail);
//	}
	public DisplayImageOptions setDisplayOptions(int resId){
		return new DisplayImageOptions.Builder()
        .showImageOnLoading(resId)
        .showImageForEmptyUri(resId)
        .showImageOnFail(resId)
        .resetViewBeforeLoading(true)
//        .delayBeforeLoading(1000)
        .cacheInMemory(true)
        .cacheOnDisk(true)
//        .preProcessor(...)
//        .postProcessor(...)
//        .extraForDownloader(...)
        .considerExifParams(true)
//        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
//        .decodingOptions(...)
//        .displayer(new SimpleBitmapDisplayer()) // default
//        .handler(new Handler()) // default
        .build();
	}
	public void display(String uri, ImageView imageView){
		loader.displayImage(uri, imageView, getDefaultDisplayOptions());
	}
	public void display(String uri, ImageView imageView, DisplayImageOptions options){
		loader.displayImage(uri, imageView, options);
	}
	public void display(String uri, ImageView imageView, ImageLoaderListener listener){
		loader.displayImage(uri, imageView,getDefaultDisplayOptions(), new ImageLoaderlister(listener));
	}
	public void display(String uri, ImageView imageView,DisplayImageOptions options,ImageLoaderListener listener){
		loader.displayImage(uri, imageView,options, new ImageLoaderlister(listener));
	}
	public void loadeImage(String uri, ImageLoaderListener listener){
		loader.loadImage(uri, new ImageLoaderlister(listener));
	}
	public void loadeImage(String uri,DisplayImageOptions options, ImageLoaderListener listener){
		loader.loadImage(uri, options, new ImageLoaderlister(listener));
	}
	public void loadeImage(String uri,ImageSize targetImageSize, DisplayImageOptions options, ImageLoaderListener listener){
		loader.loadImage(uri, targetImageSize, options, new ImageLoaderlister(listener));
	}
	public class ImageLoaderlister implements ImageLoadingListener{
		private ImageLoaderListener listener;
		public ImageLoaderlister(ImageLoaderListener listener) {
			this.listener = listener;
		}
		@Override
		public void onLoadingStarted(String imageUri, View view) {
			listener.onLoadingStarted(imageUri, view);
		}
		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
			listener.onLoadingFailed(imageUri, view, failReason);
		}
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			listener.onLoadingComplete(imageUri, view, loadedImage);
		}
		
		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			listener.onLoadingCancelled(imageUri, view);
		}
	}
	public ImageLoader getLoader(){
		return loader;
	}
	public static class ImageLoaderListener {
		public void onLoadingStarted(String imageUri, View view) {}
		public void onLoadingFailed(String imageUri, View view,FailReason failReason) {}
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {}
		public void onLoadingCancelled(String imageUri, View view) {}
	}
}
