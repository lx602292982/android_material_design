package com.lixiang.weather.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lixiang.weather.R;
import com.lixiang.weather.support.utils.ScreenUtils;
import com.lixiang.weather.support.view.SystemBarTintManager;

@SuppressLint("ResourceAsColor")
public abstract class BaseActivity extends AppCompatActivity {
	public FrameLayout main;
	private View content;
	public Toolbar bar;
	private SystemBarTintManager manager;
	private int statusBarHeight = 0;
	private TextView centerText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		main = (FrameLayout) findViewById(R.id.main);
		centerText = (TextView) findViewById(R.id.centerText);
		bar = (Toolbar) findViewById(R.id.toolbar);
		if (setContentView() != 0) {
			content = getLayoutInflater()
					.inflate(setContentView(), null, false);
		}
		View contentView = getContentView();
		if (contentView != null) {
			content = contentView;
		}
		if (content != null) {
			main.addView(content, 0, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		setLayoutMargin(statusBarHeight,
				statusBarHeight + ScreenUtils.dip2px(this, 48));
		bar.setTitle("");
		bar.setTitleTextAppearance(this, R.style.ToolBarTitle);
		bar.setBackgroundResource(R.color.blue);
		resetToolBar();
		setToolBar(bar);
		setSupportActionBar(bar);
		registerButtonListener();
		init();
	}

	public void resetToolBar() {
		setStatusBarColor(R.color.blue);
		bar.setBackgroundResource(R.color.blue);
		setBackgroundAlpha(255);
		setStatusBarAlpha(1f);
		setLayoutMargin(statusBarHeight,
				statusBarHeight + ScreenUtils.dip2px(this, 48));
	}

	public void registerButtonListener() {
		bar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onNavigationClick();
			}
		});
		centerText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onCenterTextClick();
			}
		});
	}

	public void setLayoutMargin(int barMargin, int contentMargin) {
		MarginLayoutParams params = (MarginLayoutParams) bar.getLayoutParams();
		params.topMargin = barMargin;
		bar.setLayoutParams(params);
		if (content != null) {
			MarginLayoutParams cparams = (MarginLayoutParams) content
					.getLayoutParams();
			cparams.topMargin = contentMargin;
			content.setLayoutParams(cparams);
		}
	}

	public void setFullscreen() {
		setLayoutMargin(0, 0);
		setStatusBarColor(android.R.color.transparent);
		bar.setVisibility(View.GONE);
	}

	public void setCenterTextView(int resId) {
		setCenterTextView(getResources().getString(resId));
	}

	public void setCenterTextView(String text) {
		centerText.setVisibility(View.VISIBLE);
		centerText.setText(text);
	}

	public void setCenterTextRightDrawble(int resId) {
		@SuppressWarnings("deprecation")
		Drawable drawable = getResources().getDrawable(resId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		centerText.setCompoundDrawables(null, null, drawable, null);
	}

	public void addCenterView(View view) {
		bar.removeAllViews();
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		bar.addView(view);
	}

	public void addView(View child, int index) {
		main.addView(child, index);
	}

	public void setStatusBarColor(int color) {
		if (manager != null) {
			// manager.setStatusBarTintColor(color);
		}
	}

	public void setStatusBarAlpha(float alpha) {
		if (manager != null) {
			// manager.setStatusBarAlpha(alpha);
		}
	}

	public void setBackgroundAlpha(float alpha) {
		Drawable drawable = bar.getBackground();
		drawable.setAlpha((int) alpha);
	}

	public TextView getCenterText() {
		return centerText;
	}

	public int getStatusBarHeight() {
		return statusBarHeight;
	}

	public Toolbar getToolBar() {
		return bar;
	}

	public void onNavigationClick() {
	}

	public void onRightButtonClick() {
	}

	public void onCenterTextClick() {
	};

	public abstract void setToolBar(Toolbar bar);

	public abstract int setContentView();

	public abstract void init();

	public View getContentView() {
		return null;
	}
}
