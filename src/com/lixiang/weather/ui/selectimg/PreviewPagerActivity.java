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

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lixiang.weather.R;
import com.lixiang.weather.ui.BaseActivity;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("ResourceAsColor")
public class PreviewPagerActivity extends BaseActivity {
	public static final String EXTRA_PREVIEWLIST = "preview_list";
	public static final String EXTRA_SOURCE = "source";
	public static final String EXTRA_POSITION = "position";
	public static final String EXTRA_ISDELETE = "is_delete";
	public static final String DELETE_INDEX = "delete_index";

	public static final int FILE = 1;
	public static final int NETWORK = 2;

	public int source = FILE;
	private ViewPager page;
	private ArrayList<String> list = new ArrayList<String>();
	private PagerAdapter pagerAdapter;
	private int currPosition = 0;

	// private boolean isDelete = false;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_delete) {
			Intent intent = new Intent();
			intent.putExtra(DELETE_INDEX, page.getCurrentItem());
			setResult(RESULT_OK, intent);
			this.finish();
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (getIntent().getBooleanExtra(EXTRA_ISDELETE, false)) {
			getMenuInflater().inflate(R.menu.delete, menu);
		}
		return true;
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return PreViewPagerFragment.getInstance(list.get(position), source);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

	@Override
	public void setToolBar(Toolbar bar) {
		bar.setNavigationIcon(R.drawable.ic_back);
		bar.setBackgroundResource(android.R.color.transparent);
		setStatusBarColor(android.R.color.transparent);
		setLayoutMargin(getStatusBarHeight(), 0);
	}

	@Override
	public int setContentView() {
		return R.layout.activity_pager;
	}

	@Override
	public void init() {
		source = getIntent().getIntExtra(EXTRA_SOURCE, FILE);
		list.addAll(getIntent().getStringArrayListExtra(EXTRA_PREVIEWLIST));
		setCenterTextView("1/" + list.size());
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		page = (ViewPager) findViewById(R.id.pager);
		page.setAdapter(pagerAdapter);
		currPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
		if (currPosition < list.size()) {
			setCenterTextView((currPosition + 1) + "/" + list.size());
			page.setCurrentItem(currPosition);
		}
		page.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				setCenterTextView((arg0 + 1) + "/" + list.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onNavigationClick() {
		this.finish();
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
