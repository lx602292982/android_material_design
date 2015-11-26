package  com.lixiang.weather.support.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicHeader;

import android.preference.PreferenceActivity.Header;

import com.lixiang.weather.model.Secret;
import com.lixiang.weather.support.database.util.LogUtils;

public class RequestParams {
	private Map<String, String> params;
	private String charset = "UTF-8";

	public RequestParams() {
		params = new HashMap<String, String>();
	}

	public RequestParams(Secret secret) {
		params = new HashMap<String, String>();
		params.put("idfs", secret.getIdfs());
		params.put("secret", secret.getSecret());
	}

	public void put(String key, String value) {
		params.put(key, value);
	}

	public void put(String key, int value) {
		put(key, value + "");
	}

	public void put(String key, Long value) {
		put(key, value + "");
	}

	public void put(String key, boolean value) {
		put(key, value + "");
	}

	public String get(String key) {
		return params.get(key);
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public static String getCacheName(String url, RequestParams params) {
		return getUrlTemp(url, params, true);
	}

	public static String getUrlParams(String url, RequestParams params) {
		return getUrlTemp(url, params, false);
	}

	public static String getUrlTemp(String url, RequestParams params,
			boolean isFilter) {
		if (params == null || params.getParams().size() <= 0) {
			return url;
		}
		List<String> list = new ArrayList<String>();
		for (String key : params.getParams().keySet()) {
			if (!isFilter) {
				String par = key + "=" + params.get(key);
				list.add(par);
				continue;
			}
			if (!key.equals("lng") && !key.equals("lat") && !key.equals("idfs")
					&& !key.equals("secret")) {
				String par = key + "=" + params.get(key);
				list.add(par);
			}
			LogUtils.i("[GET]---" + key + "---" + params.get(key));
		}
		if (list.size() > 1) {
			String temp = "";
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					temp += list.get(i);
				} else {
					temp += list.get(i) + "&";
				}
			}
			return url + "?" + temp;
		} else {
			return url + "?" + list.get(0);
		}
	}

	// public static Header[] assemblyHeader(Map<String, String> params) {
	// Header[] allHeader = new BasicHeader[params.size()];
	// int i = 0;
	// for (String str : params.keySet()) {
	// allHeader[i] = new BasicHeader(str, params.get(str));
	// i++;
	// }
	// return allHeader;
	// }

}
