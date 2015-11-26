package com.lixiang.weather.ui.selectimg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lixiang.weather.R;
import com.lixiang.weather.config.ImageLoaderHepler.ImageLoaderListener;
import com.lixiang.weather.config.InitHelper;
import com.lixiang.weather.support.database.util.LogUtils;
import com.lixiang.weather.support.utils.FileUtils;
import com.lixiang.weather.support.utils.ScreenUtils;
import com.lixiang.weather.support.view.LoadingDialog;
import com.lixiang.weather.support.view.cropper.CropImageView;
import com.lixiang.weather.ui.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.umeng.analytics.MobclickAgent;

public class CropperActivity extends BaseActivity {
	public static final String OUTPUT_IMAGE_PATH = "OUTPUT_IMAGE_PATH";
	public static final String INPUT_IMAGE_PATH = "INPUT_IMAGE_PATH";
	private CropImageView cropImageView;
	private LoadingDialog lDialog;

	@Override
	public void setToolBar(Toolbar bar) {
		bar.setNavigationIcon(R.drawable.ic_back);
		setCenterTextView(R.string.cropper_image);
	}

	@Override
	public int setContentView() {
		return R.layout.activity_cropper;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cropper, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_rotation) {
			cropImageView.rotateImage(90);
		} else if (item.getItemId() == R.id.action_done) {
			lDialog.show(R.string.cropper_image);
			new SaveImageTask().execute();
		}
		return true;
	}

	@Override
	public void init() {
		lDialog = new LoadingDialog(this);

		cropImageView = (CropImageView) findViewById(R.id.cropper_image);
		cropImageView.setAspectRatio(1, 1);
		String path = getIntent().getStringExtra(INPUT_IMAGE_PATH);
		LogUtils.i(path);
		// cropImageView.setImageBitmap(BitmapFactory.decodeFile(path));
		ImageSize imageSize = new ImageSize(ScreenUtils.getScreenWidth(this),
				ScreenUtils.getScreenHeight(this));
		InitHelper.getImageLoader().loadeImage("file://" + path, imageSize,
				getDisplayOptions(), new ImageLoaderListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						System.out.println("----w---" + loadedImage.getWidth()
								+ "---h----" + loadedImage.getHeight());
						cropImageView.setImageBitmap(loadedImage);
					}
				});
		// InitHelper.getImageLoader().loadeImage("file://"+path, new
		// ImageLoaderListener(){
		// @Override
		// public void onLoadingComplete(String imageUri, View view,
		// Bitmap loadedImage) {
		// cropImageView.setImageBitmap(loadedImage);
		// }
		// });
	}

	public DisplayImageOptions getDisplayOptions() {
		return new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
				.cacheInMemory(false).cacheOnDisk(false)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.ARGB_8888) // default
				.build();
	}

	@Override
	public void onNavigationClick() {
		this.finish();
	}

	class SaveImageTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			Bitmap bitmap = cropImageView.getCroppedImage();
			File f = FileUtils.createCropperFile(CropperActivity.this);
			try {
				FileOutputStream out = new FileOutputStream(f);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return f.getAbsolutePath();
		}

		@Override
		protected void onPostExecute(String result) {
			Intent intent = new Intent();
			intent.putExtra(OUTPUT_IMAGE_PATH, result);
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen");
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen");
		MobclickAgent.onPause(this);
	}
}
