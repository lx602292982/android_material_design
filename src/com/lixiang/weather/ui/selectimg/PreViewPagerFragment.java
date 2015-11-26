/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.lixiang.weather.ui.selectimg;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixiang.weather.R;
import com.lixiang.weather.config.ImageLoaderHepler.ImageLoaderListener;
import com.lixiang.weather.config.InitHelper;
import com.lixiang.weather.support.view.ProgressWheel;
import com.lixiang.weather.support.view.preview.ImageSource;
import com.lixiang.weather.support.view.preview.SubsamplingScaleImageView;

public class PreViewPagerFragment extends Fragment {
	public static final String PATH = "Path";
	public static final String SOURCE = "source";
	public static final String BUNDLE_ASSET = "asset";

	private View rootView;
	private ProgressWheel progress_wheel;
	private SubsamplingScaleImageView imageView;
	private String path;
	private int source;

	public static PreViewPagerFragment getInstance(String imgPath, int source) {
		PreViewPagerFragment fragment = new PreViewPagerFragment();
		Bundle bundle = new Bundle();
		bundle.putString(PATH, imgPath);
		bundle.putInt(SOURCE, source);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootView == null){
			Bundle bundle = getArguments();
			path = bundle.getString(PATH);
			source = bundle.getInt(SOURCE);
			rootView = inflater.inflate(R.layout.pager_preview, container, false);
			imageView = (SubsamplingScaleImageView) rootView.findViewById(R.id.imageView);
			progress_wheel = (ProgressWheel)rootView.findViewById(R.id.progress_wheel);
//			imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
//			imageView.setDoubleTapZoomScale(2f);
			if (savedInstanceState != null) {
				if (path == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
					path = savedInstanceState.getString(BUNDLE_ASSET);
				}
			}
			if (path != null) {
				InitHelper.getImageLoader().loadeImage(source == PreviewPagerActivity.FILE?("file://"+path):path, new ImageLoaderListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
						imageView.setImage(ImageSource.bitmap(loadedImage));
						progress_wheel.setVisibility(View.GONE);
					}
				});
			}
		}else{
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}
		return rootView;
	}

//	class LoadImageTask extends AsyncTask<Integer, Integer, Bitmap> {
//		@Override
//		protected Bitmap doInBackground(Integer... params) {
//			Bitmap bitmap = null;
//			try {
//				if (source == PreviewPagerActivity.FILE) {
//					bitmap = Picasso.with(getActivity()).load(new File(path))
//							.get();
//				} else {
//					bitmap = Picasso.with(getActivity()).load(path).
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return bitmap;
//		}
//		@Override
//		protected void onPostExecute(Bitmap result) {
//			imageView.setImage(ImageSource.bitmap(result));
//		}
//	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		View rootView = getView();
		if (rootView != null) {
			outState.putString(BUNDLE_ASSET, path);
		}
	}

}
