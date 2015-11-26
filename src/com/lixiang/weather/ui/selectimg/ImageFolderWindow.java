package com.lixiang.weather.ui.selectimg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lixiang.weather.R;

public class ImageFolderWindow extends PopupWindow {
	private View main;
	private ListView listView;

	public ImageFolderWindow(Context context, ListAdapter adapter) {
		main = LayoutInflater.from(context).inflate(R.layout.window_imgfolder,
				null);
		listView = (ListView) main.findViewById(R.id.list);
		listView.setAdapter(adapter);
		// MarginLayoutParams params = (MarginLayoutParams)
		// listView.getLayoutParams();
		// params.topMargin = ScreenUtils.getScreenHeight(context)/3;
		// listView.setLayoutParams(params);
		this.setContentView(main);
		this.setWindowLayoutMode(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// this.setAnimationStyle(R.style.window_bg_animation);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		this.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));

		initView();
	}

	public void setSelection(int position) {
		listView.setSelection(position);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		listView.setOnItemClickListener(listener);
	}

	public void initView() {
		main.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getY() > listView.getBottom()
						|| event.getY() < listView.getTop()) {
					dismiss();
				}
				return true;
			}
		});
	}
}
