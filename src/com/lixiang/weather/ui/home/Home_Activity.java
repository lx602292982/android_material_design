package com.lixiang.weather.ui.home;

import android.support.v7.widget.Toolbar;

import com.lixiang.weather.R;
import com.lixiang.weather.ui.BaseActivity;

public class Home_Activity extends BaseActivity {

	@Override
	public void setToolBar(Toolbar bar) {
		bar.setNavigationIcon(R.drawable.ic_back);
		setCenterTextView(R.string.app_name);
	}

	@Override
	public int setContentView() {
		return R.layout.activity_home;
	}

	@Override
	public void init() {
		initView();
		registerListener();
		initdate();
	}

	private void initView() {
		
	}

	private void registerListener() {
		
	}
	
	private void initdate() {
		
	}

}
