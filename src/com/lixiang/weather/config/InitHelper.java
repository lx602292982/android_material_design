package com.lixiang.weather.config;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.lixiang.weather.support.http.HttpRequestClient;

public class InitHelper {
	private static Context context;
	private static DbHepler db;
//	private static BdLocation location;
	private static HttpRequestClient client;
	private static ImageLoaderHepler imageLoader;
//	private static User user;

	private static String httpPath;
	private static String imagePath;

	private static int notificationNum = 0;

	public static void onInit(Context context) {
		InitHelper.context = context;
		httpPath = initCachePath(context, AppConfig.httpCachePath);
		imagePath = initCachePath(context, AppConfig.imageCachePath);
		client = new HttpRequestClient(context, httpPath);
		imageLoader = new ImageLoaderHepler(context, new File(imagePath));
//		location = new BdLocation(context);
		db = new DbHepler(context, AppConfig.DB_NAME);
//		user = db.findFirst(User.class);

//		getGpsLocation(context);
//		LogUtils.isDebug = true;
	}

//	public static void getGpsLocation(final Context context) {
//		final String city = PreferencesUtils.getString(context, AppConfig.CITY);
//		location.requestLocation(new OnLocationLinstener() {
//			@Override
//			public void onStart() {
//			}
//
//			@Override
//			public void onLocationSuccess(Location location) {
//				if (TextUtils.isEmpty(city)) {
//					ProvinceCityActivity.savePrefrences(context,
//							location.getProvince(), location.getCity());
//				}
//			}
//
//			@Override
//			public void OnLocationFail(int errorCode) {
//				if (TextUtils.isEmpty(city)) {
//					ProvinceCityActivity.savePrefrences(context, "北京", "北京");
//				}
//			}
//		});
//	}

	public static HttpRequestClient getRequest() {
		return client;
	}

	public static ImageLoaderHepler getImageLoader() {
		return imageLoader;
	}

	public static DbHepler getDb() {
		return db;
	}

	public static String initCachePath(Context context, String path) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory() + path;
		} else {
			path = context.getCacheDir() + path;
		}
		return path;
	}

//	public static boolean isLogin() {
//		return user == null ? false : true;
//	}

//	public static void Logout() {
//		InitHelper.user = null;
//		InitHelper.getDb().dropTable(User.class);
//	}

//	public static void saveUser(User user) {
//		// User dbUser = InitHelper.getDb().findFirst(User.class);
//		// if(dbUser!=null){
//		// user.setDbId(dbUser.getDbId());
//		// }
//		InitHelper.getDb().dropTable(User.class);
//		InitHelper.getDb().save(user);
//		InitHelper.user = user;
//	}

	public static int getNotificationNum() {
		return notificationNum;
	}

	public static void setNotificationNum(int notificationNum) {
		InitHelper.notificationNum = notificationNum;
	}

//	public static BdLocation getLocation() {
//		return location;
//	}

//	public static User getUser() {
//		return user;
//	}

	public static String getHttpCachePath() {
		return httpPath;
	}

	public static String getImageCachePath() {
		return imagePath;
	}

//	public static String getXMRegisterId() {
//		String device_token = PreferencesUtils.getString(context,
//				AppConfig.DEVICE_TOKEN, "");
//		// LogUtils.i("device_token----->" + device_token);
//		return device_token;
//	}

//	public static void setUserAccount() {
//		if (InitHelper.isLogin()) {
//			LogUtils.i("pushUuid-------"
//					+ InitHelper.getUser().getPusher_uuid());
//			MiPushClient.setUserAccount(context, InitHelper.getUser()
//					.getPusher_uuid(), null);
//		}
//	}

//	public static void bindDevice() {
//		if (!InitHelper.isLogin()) {
//			return;
//		}
//		RequestParams params = new RequestParams();
//		params.put("device_token", getXMRegisterId());
//		params.put("token", InitHelper.getUser().getAccess_token());
//		params.put("model", Build.MODEL);
//		params.put("platform", Build.VERSION.RELEASE);
//		params.put("app", DeviceUtils.getVersionName(context));
//		InitHelper.client.post(Constans.bindDevice, params,
//				new HttpResponseHandler() {
//					@Override
//					public void onSuccess(String data) {
//						LogUtils.i("bind device success !!");
//					}
//				});
//	}
}
