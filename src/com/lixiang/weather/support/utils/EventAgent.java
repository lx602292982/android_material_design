package com.lixiang.weather.support.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;

import com.lixiang.weather.config.Constans;
import com.lixiang.weather.config.InitHelper;
import com.lixiang.weather.model.EventData;
import com.lixiang.weather.support.http.HttpResponseHandler;
import com.lixiang.weather.support.http.RequestParams;

public class EventAgent {
	/**
	 * 添加普通事件
	 * 
	 * @param context
	 * @param eventType
	 */
	public static void push(int eventType) {
		EventData data = new EventData();
		data.setType(eventType);
		data.setWhen(DateTimeUtils
				.getCurrentTimeInString(DateTimeUtils.DEFAULT_DATE_FORMAT));
		InitHelper.getDb().save(data);
	}

	/**
	 * 添加带数据事件
	 * 
	 * @param context
	 * @param eventType
	 * @param dataType
	 * @param data
	 */
	public static void push(int eventType, String key, String value) {
		try {
			EventData data = new EventData();
			data.setType(eventType);
			data.setWhen(DateTimeUtils
					.getCurrentTimeInString(DateTimeUtils.DEFAULT_DATE_FORMAT));
			JSONObject object = new JSONObject();
			object.put(key, value);
			data.setData(object.toString());
			InitHelper.getDb().save(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void push(int eventType, Map<String, String> map) {
		EventData data = new EventData();
		data.setType(eventType);
		data.setWhen(DateTimeUtils
				.getCurrentTimeInString(DateTimeUtils.DEFAULT_DATE_FORMAT));
		try {
			if (map != null && map.size() > 0) {
				JSONObject object = new JSONObject();
				Iterator<String> keys = map.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					object.put(key, map.get(key));
				}
				data.setData(object.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		InitHelper.getDb().save(data);
	}

	/**
	 * 将数据库event事件push到服务器 push时机：打开或关闭App时
	 */
	public static void pushServer(Context context) {
		List<EventData> list = InitHelper.getDb().findAll(EventData.class);
		try {
			if (list != null && list.size() > 0) {
				JSONArray array = new JSONArray();
				for (int i = 0; i < list.size(); i++) {
					EventData data = list.get(i);
					JSONObject object = new JSONObject();
					object.put("when", data.getWhen());
					object.put("type", data.getType());
					if(data.getData()!=null){
						object.put("data", new JSONObject(data.getData()));
					}
					array.put(object);
				}
//				pushData(context, array.toString(),list);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

//	private static void pushData(Context context,String data,final List<EventData> list){
//		RequestParams params = new RequestParams();
//		params.put("c_ver", DeviceUtils.getVersionName(context));
//		params.put("device_os", Build.BRAND+Build.MODEL + " "+ Build.VERSION.RELEASE);
//		params.put("device_id", InitHelper.getXMRegisterId());
//		if(InitHelper.isLogin()){
//			params.put("user_id", InitHelper.getUser().getId());
//		}
//		params.put("events", data);
////		InitHelper.getRequest().post(Constans.postEventData, params, new HttpConfig(), new HttpResponseHandler(){
////			@Override
////			public void onSuccess(String data) {
////				InitHelper.getDb().deleteAll(list);
////			}
////		});
//		InitHelper.getRequest().post(Constans.postEventData, params, new HttpResponseHandler(){
//			@Override
//			public void onSuccess(String data) {
//				InitHelper.getDb().deleteAll(list);
//			}
//		});
//	}
}
