package com.lixiang.weather.ui.selectimg;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiang.weather.R;
import com.lixiang.weather.config.InitHelper;
import com.lixiang.weather.model.selectimg.Folder;
import com.lixiang.weather.model.selectimg.Image;
import com.lixiang.weather.support.utils.FileUtils;
import com.lixiang.weather.ui.BaseActivity;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.soundcloud.android.crop.Crop;
import com.umeng.analytics.MobclickAgent;

public class SelectorImageActivity extends BaseActivity {
	private static final String TAG = "MultiImageSelector";
	/** 最大图片选择次数，int类型，默认9 */
	public static final String EXTRA_SELECT_COUNT = "max_select_count";
	/** 图片选择模式，默认多选 */
	public static final String EXTRA_SELECT_MODE = "select_count_mode";
	/** 是否显示相机，默认显示 */
	public static final String EXTRA_SHOW_CAMERA = "show_camera";
	/** 是否剪切图片 */
	public static final String EXTRA_CROPPER = "is_cropper";
	/** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合 */
	public static final String EXTRA_RESULT = "select_result";
	/** 默认选择集 */
	public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
	
	public static final int RESQUEST_PREVIEW = 97;
	public static final int RESQUEST_CROPIMAGE = 98;
	public static final int RESQUEST_SLECTIMAGE = 99;
	private static final int REQUEST_CAMERA = 100;
	/** 单选 */
	public static final int MODE_SINGLE = 0;
	/** 多选 */
	public static final int MODE_MULTI = 1;

	private ArrayList<String> resultList = new ArrayList<String>();
	private int mDefaultCount = 9;
	private int mode = MODE_MULTI;
	private boolean isShow = true;
	private boolean isCropper = false;

	// 不同loader定义
	private static final int LOADER_ALL = 0;
	private static final int LOADER_CATEGORY = 1;
	
	// 文件夹数据
	private ArrayList<Folder> mResultFolder = new ArrayList<Folder>();

	// 图片Grid
	private GridView mGridView;

	private ImageGridAdapter mImageAdapter;
	private FolderAdapter mFolderAdapter;

	// private ListPopupWindow mFolderPopupWindow;
	// private PopupWindow folderWindow;
	private ImageFolderWindow mFolderWindow;
	// 时间线
	private TextView mTimeLineText;
	// 类别
	private TextView mCategoryText;
	// 预览按钮
	private Button mPreviewBtn;
	// 底部View
	// private View mPopupAnchorView;

	private boolean hasFolderGened = false;

	private File mTmpFile;

	@Override
	public void setToolBar(Toolbar bar) {
		bar.setNavigationIcon(R.drawable.ic_back);
		setCenterTextView(R.string.select_image);
	}

	@Override
	public int setContentView() {
		return R.layout.activity_selectimg;
	}

	@Override
	public void onNavigationClick() {
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mode != MODE_SINGLE) {
			getMenuInflater().inflate(R.menu.done, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_done) {
			if (resultList.size() == 0) {
				Toast.makeText(this, R.string.please_select_img,Toast.LENGTH_LONG).show();
			} else {
				setResultAndFinish();
			}
		}
		return true;
	}

	@Override
	public void init() {
		Intent intent = getIntent();
		mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
		mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
		isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
		isCropper = intent.getBooleanExtra(EXTRA_CROPPER, false);
		if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
			resultList = intent
					.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
		}
		// 首次加载所有图片
		getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);

		initView();
		registerListener();
	}

	public void initView() {
		// mPopupAnchorView = findViewById(R.id.footer);
		// 初始化，先隐藏当前timeline
		mTimeLineText = (TextView) findViewById(R.id.timeline_area);
		mTimeLineText.setVisibility(View.GONE);
		// 初始化，加载所有图片
		mCategoryText = (TextView) findViewById(R.id.category_btn);
		mCategoryText.setText(R.string.folder_all);
		// 初始化，按钮状态初始化
		mPreviewBtn = (Button) findViewById(R.id.preview);
		if (mode == MODE_SINGLE) {
			mPreviewBtn.setVisibility(View.GONE);
		}
		updatePreviewtext();
		mFolderAdapter = new FolderAdapter(this);
		mFolderWindow = new ImageFolderWindow(this, mFolderAdapter);

		mGridView = (GridView) findViewById(R.id.grid);
		mImageAdapter = new ImageGridAdapter(this, isShow);
		mGridView.setAdapter(mImageAdapter);
		// 是否显示选择指示器
		mImageAdapter.showSelectIndicator(mode == MODE_MULTI);
		// 如果显示了照相机，则创建临时文件
		if (isShow) {
			mTmpFile = FileUtils.createCameraFile(this);
		}
		mGridView.setOnScrollListener(new PauseOnScrollListener(InitHelper.getImageLoader().getLoader(), false, false)); 
	}

	public void registerListener() {
		mCategoryText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mFolderWindow.isShowing()) {
					mFolderWindow.dismiss();
				} else {
					mFolderWindow.showAtLocation(mGridView, Gravity.CENTER, 0,
							0);
					int index = mFolderAdapter.getSelectIndex();
					index = index == 0 ? index : index - 1;
					mFolderWindow.setSelection(index);
				}
			}
		});
		mPreviewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SelectorImageActivity.this,PreviewPagerActivity.class);
				intent.putStringArrayListExtra(PreviewPagerActivity.EXTRA_PREVIEWLIST, resultList);
				intent.putExtra(PreviewPagerActivity.EXTRA_SOURCE, PreviewPagerActivity.FILE);
				startActivityForResult(intent, RESQUEST_PREVIEW);
			}
		});
		mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int state) {

//				final Picasso picasso = Picasso
//						.with(SelectorImageActivity.this);
//				if (state == SCROLL_STATE_IDLE
//						|| state == SCROLL_STATE_TOUCH_SCROLL) {
//					picasso.resumeTag(SelectorImageActivity.this);
//				} else {
//					picasso.pauseTag(SelectorImageActivity.this);
//				}
				
				
				if (state == SCROLL_STATE_IDLE) {
					// 停止滑动，日期指示器消失
					mTimeLineText.setVisibility(View.GONE);
				} else if (state == SCROLL_STATE_FLING) {
					mTimeLineText.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (mTimeLineText.getVisibility() == View.VISIBLE) {
					int index = firstVisibleItem + 1 == view.getAdapter()
							.getCount() ? view.getAdapter().getCount() - 1
							: firstVisibleItem + 1;
					Image image = (Image) view.getAdapter().getItem(index);
					if (image != null) {
						mTimeLineText.setText(TimeUtils
								.formatPhotoDate(image.path));
					}
				}
			}
		});
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				if (mImageAdapter.isShowCamera()) {
					// 如果显示照相机，则第一个Grid显示为照相机，处理特殊逻辑
					if (i == 0) {
						showCameraAction();
					} else {
						// 正常操作
						Image image = (Image) adapterView.getAdapter().getItem(
								i);
						selectImageFromGrid(image, mode);
					}
				} else {
					// 正常操作
					Image image = (Image) adapterView.getAdapter().getItem(i);
					selectImageFromGrid(image, mode);
				}
			}
		});
		mFolderWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					getSupportLoaderManager().restartLoader(LOADER_ALL, null,
							mLoaderCallback);
					mCategoryText.setText(R.string.folder_all);
					// mImageAdapter.setShowCamera(true);
				} else {
					Folder folder = (Folder) mFolderAdapter.getItem(position);
					if (null != folder) {
						Bundle args = new Bundle();
						args.putString("path", folder.path);
						getSupportLoaderManager().restartLoader(
								LOADER_CATEGORY, args, mLoaderCallback);
						mCategoryText.setText(folder.name);
					}
					// mImageAdapter.setShowCamera(false);
				}
				mFolderAdapter.setSelectIndex(position);
				mFolderWindow.dismiss();
				// 滑动到最初始位置
				mGridView.smoothScrollToPosition(0);

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 相机拍照完成后，返回图片路径
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CAMERA) {
				if (mTmpFile != null) {
					if(mode == MODE_SINGLE){
						resultList.clear();
					}
					resultList.add(mTmpFile.getAbsolutePath());
					setResultAndFinish();
				}
			}else if(requestCode == Crop.REQUEST_CROP){
				resultList.clear();
				resultList.add(Crop.getOutput(data).getSchemeSpecificPart());
				isCropper = false;
				setResultAndFinish();
			} else if (requestCode == RESQUEST_CROPIMAGE) {
				resultList.clear();
				resultList.add(data.getStringExtra(CropperActivity.OUTPUT_IMAGE_PATH));
				isCropper = false;
				setResultAndFinish();
			}
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.d(TAG, "on change");
		final int orientation = newConfig.orientation;
		if (mFolderWindow != null) {
			if (mFolderWindow.isShowing()) {
				mFolderWindow.dismiss();
			}
		}
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 选择相机
	 */
	private void showCameraAction() {
		// 跳转到系统照相机
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			// 设置系统相机拍照后的输出路径
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(mTmpFile));
			startActivityForResult(cameraIntent, REQUEST_CAMERA);
		} else {
			Toast.makeText(this, R.string.msg_no_camera, Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 选择图片操作
	 * 
	 * @param image
	 */
	private void selectImageFromGrid(Image image, int mode) {
		if (image != null) {
			// 多选模式
			if (mode == MODE_MULTI) {
				if (resultList.contains(image.path)) {
					resultList.remove(image.path);
				} else {
					// 判断选择数量问题
					if (mDefaultCount == resultList.size()) {
						Toast.makeText(this, R.string.msg_amount_limit,
								Toast.LENGTH_SHORT).show();
						return;
					}
					resultList.add(image.path);
				}
				updatePreviewtext();
				mImageAdapter.select(image);
			} else if (mode == MODE_SINGLE) {
				resultList.clear();
				resultList.add(image.path);
				// 单选模式
				setResultAndFinish();
			}
		}
	}

	public void setResultAndFinish() {
		if (mode == MODE_SINGLE && isCropper) {
			Crop.of(Uri.fromFile(new File(resultList.get(0))), Uri.fromFile(FileUtils.createCropperFile(this))).asSquare().start(this);
//			Intent intent = new Intent(this,CropperActivity.class);
//			intent.putExtra(CropperActivity.INPUT_IMAGE_PATH, resultList.get(0));
//			startActivityForResult(intent, RESQUEST_CROPIMAGE);
		} else {
			Intent intent = new Intent();
			intent.putExtra(EXTRA_RESULT, resultList);
			setResult(RESULT_OK, intent);
			this.finish();
		}
	}

	public void updatePreviewtext() {
		mPreviewBtn.setText(getResources().getString(R.string.preview) + "("
				+ resultList.size() + "/" + mDefaultCount + ")");
	}

	private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

		private final String[] IMAGE_PROJECTION = {
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID };

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			if (id == LOADER_ALL) {
				CursorLoader cursorLoader = new CursorLoader(
						SelectorImageActivity.this,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[2]
								+ " DESC");
				return cursorLoader;
			} else if (id == LOADER_CATEGORY) {
				CursorLoader cursorLoader = new CursorLoader(
						SelectorImageActivity.this,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_PROJECTION, IMAGE_PROJECTION[0] + " like '%"
								+ args.getString("path") + "%'", null,
						IMAGE_PROJECTION[2] + " DESC");
				return cursorLoader;
			}

			return null;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (data != null) {
				List<Image> images = new ArrayList<Image>();
				int count = data.getCount();
				if (count > 0) {
					data.moveToFirst();
					do {
						String path = data.getString(data
								.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
						String name = data.getString(data
								.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
						long dateTime = data.getLong(data
								.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
						Image image = new Image(path, name, dateTime);
						images.add(image);
						if (!hasFolderGened) {
							// 获取文件夹名称
							File imageFile = new File(path);
							File folderFile = imageFile.getParentFile();
							Folder folder = new Folder();
							folder.name = folderFile.getName();
							folder.path = folderFile.getAbsolutePath();
							folder.cover = image;
							if (!mResultFolder.contains(folder)) {
								List<Image> imageList = new ArrayList<Image>();
								imageList.add(image);
								folder.images = imageList;
								mResultFolder.add(folder);
							} else {
								// 更新
								Folder f = mResultFolder.get(mResultFolder
										.indexOf(folder));
								f.images.add(image);
							}
						}

					} while (data.moveToNext());

					mImageAdapter.setData(images);

					// 设定默认选择
					if (resultList != null && resultList.size() > 0) {
						mImageAdapter.setDefaultSelected(resultList);
					}

					// 文件夹按图片数量排序
					Collections.sort(mResultFolder, new Comparator<Folder>() {
						@Override
						public int compare(Folder lhs, Folder rhs) {
							if (lhs.getImages() == null
									|| rhs.getImages() == null) {
								return 0;
							}
							int lsize = lhs.getImages().size();
							int rsize = rhs.getImages().size();
							return lsize == rsize ? 0
									: (lsize < rsize ? 1 : -1);
						}
					});
					mFolderAdapter.setData(mResultFolder);
					hasFolderGened = true;

				}
			}
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}
	};
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
