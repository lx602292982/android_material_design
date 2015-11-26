package com.lixiang.weather.support.listener;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.lixiang.weather.ui.selectimg.PreviewPagerActivity;
import com.lixiang.weather.ui.selectimg.SelectorImageActivity;

public class ImagePreviewListener implements OnClickListener{
	private Context context;
	private ArrayList<String> list = new ArrayList<String>();
	private int index;
	public ImagePreviewListener(Context context,int index,List<String> list) {
		this.list.addAll(list);
		this.context = context;
		this.index = index;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context,PreviewPagerActivity.class);
		intent.putStringArrayListExtra(PreviewPagerActivity.EXTRA_PREVIEWLIST, list);
		intent.putExtra(PreviewPagerActivity.EXTRA_SOURCE, PreviewPagerActivity.NETWORK);
		intent.putExtra(PreviewPagerActivity.EXTRA_POSITION, index);
		intent.putExtra(PreviewPagerActivity.EXTRA_ISDELETE, false);
		((Activity)context).startActivityForResult(intent, SelectorImageActivity.RESQUEST_PREVIEW);
	}
	
}
