package com.lixiang.weather.support.http;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;

import com.lixiang.weather.config.AppConfig;
import com.lixiang.weather.model.Secret;
import com.lixiang.weather.support.database.util.LogUtils;
import com.lixiang.weather.support.utils.TextUtils;

public class HttpSecret {
	// 获取小米Token--->获取服务器SFS
	// 生成随机IDFS--->AppKey--->SFS
	// 生成Secret
	private static String sfs = "c927f04afc6df96c1dc38eed76962f5d";
	private static String deviceToken;

	public static void initHttpSecret(Context context) {
//		deviceToken = InitHelper.getXMRegisterId();
//		getsfsByHttp(context, deviceToken);
	}

	public static boolean valuationSfs() {
		return (deviceToken != null && sfs != null && !TextUtils.isEmpty(sfs) && !TextUtils
				.isEmpty(deviceToken));
	}

	// public static Secret getSecret() {
	// String idfs = createIdfs();
	// return new Secret(idfs, createSecret(AppConfig.APP_KEY, sfs, idfs));
	// }

	private static String createIdfs() {
		return UUID.randomUUID().toString();
	}

//	private static String createSecret(String appKey, String sfs, String idfs) {
//		return CodeUtil.MD5(appKey + "-" + sfs + "-" + idfs);
//	}

	// private static void getsfsByHttp(final Context context, String
	// deviceToken) {
	// RequestParams params = new RequestParams();
	// params.put("client_version_num", DeviceUtils.getVersionName(context));
	// params.put("device_type", Build.MODEL + " " + Build.VERSION.RELEASE);
	// params.put("client_uuid", deviceToken);
	// InitHelper.getRequest().get(Constans.checkVersion, params,
	// new HttpResponseHandler() {
	// @Override
	// public void onSuccess(String data) {
	// try {
	// JSONObject object = new JSONObject(data);
	// if (object.optInt("status") == 0) {
	// sfs = object.optString("sfs");
	// PreferencesUtils.putString(context,
	// AppConfig.SFS, sfs);
	// LogUtils.i("sfs------>" + sfs);
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Override
	// public void onFailure(int code) {
	// sfs = PreferencesUtils.getString(context,
	// AppConfig.SFS, sfs);
	// }
	// });
	// }
}
