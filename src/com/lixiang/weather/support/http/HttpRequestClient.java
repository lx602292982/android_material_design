package com.lixiang.weather.support.http;

import java.io.File;
import java.security.KeyStore;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.Header;
import android.content.Context;

import com.lixiang.weather.model.Secret;
import com.lixiang.weather.support.database.util.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

public class HttpRequestClient {
	private AsyncHttpClient client;
	private ACache aCache;
	private Context context;
	private HttpConfig config;

	public HttpRequestClient(Context context, String httpCachePath) {
		this.context = context;
		aCache = ACache.get(new File(httpCachePath));
		client = new AsyncHttpClient();
		client.setURLEncodingEnabled(false);
		SSLSocketFactory sf = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client.setSSLSocketFactory(sf);
		} catch (Exception e) {
		}
	}

	public void get(String url, HttpResponseHandler handler) {
		get(url, null, new HttpConfig(), handler);
	}

	public void get(String url, Header[] headers, HttpResponseHandler handler) {
		get(url, headers, null, new HttpConfig(), handler);
	}

	public void get(String url, Header[] headers, RequestParams params,
			final HttpResponseHandler handler) {
		get(url, headers, params, new HttpConfig(), handler);
	}

	public void get(String url, HttpConfig config, HttpResponseHandler handler) {
		get(url, null, config, handler);
	}

	public void get(String url, final RequestParams params,
			HttpResponseHandler handler) {
		get(url, null, params, new HttpConfig(), handler);
	}

	public void get(String url, final RequestParams params, HttpConfig config,
			HttpResponseHandler handler) {
		get(url, null, params, config, handler);
	}

	public void get(String url, Header[] headers, final RequestParams params,
			final HttpConfig config, final HttpResponseHandler handler) {
		final String cacheName = RequestParams.getCacheName(url, params);
		LogUtils.i("[GET]---" + RequestParams.getUrlParams(url, params));

		final String page = (params != null && params.getParams().size() > 0) ? params
				.getParams().get("page") : null;
		if (config.getCachePage() > 0 && !config.isRefresh()) {// 从缓存获取数据
			String data = aCache.getAsString(cacheName);
			if ((page == null || Integer.parseInt(page) <= config
					.getCachePage()) && data != null && !data.equals("")) {
				LogUtils.i("---data[cache]");
				handler.onSuccess(data);
				return;
			}
		}
		com.loopj.android.http.RequestParams param = null;
		if (params != null) {
			param = new com.loopj.android.http.RequestParams(params.getParams());
		}
		if (config.isVerify()) {
			if (param == null) {
				param = new com.loopj.android.http.RequestParams();
			}
//			Secret secret = HttpSecret.getSecret();
//			param.put("idfs", secret.getIdfs());
//			param.put("secret", secret.getSecret());
		}
		client.get(context, url, headers, param, new TextHttpResponseHandler(
				handler.getChartSet()) {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				handler.onSuccess(responseString);
				boolean isPut = false;
				try {
					JSONObject obj = new JSONObject(responseString);
					if (obj.optInt("status") == 0) {
						if (config.getCachePage() > 0
								&& (page == null || Integer.parseInt(page) <= config
										.getCachePage())) {
							aCache.put(cacheName, responseString,
									config.getSaveTime());
							isPut = true;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				LogUtils.i("---data[http]---putCache[" + isPut + "]");
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				LogUtils.i("[GET-Error]---" + statusCode + "----"
						+ responseString);
				handler.onFailure(statusCode);
			}
		});
	}

	public void post(String url, HttpResponseHandler handler) {
		post(url, null, handler);
	}

	public void post(String url, RequestParams params,
			HttpResponseHandler handler) {
		post(url, null, params, null, new HttpConfig(), handler);
	}

	public void post(String url, Header[] headers, RequestParams params,
			HttpResponseHandler handler) {
		post(url, headers, params, null, new HttpConfig(), handler);
	}

	public void post(String url, Header[] headers, RequestParams params,
			HttpConfig config, HttpResponseHandler handler) {
		post(url, headers, params, null, config, handler);
	}

	public void post(String url, RequestParams params, String contentType,
			HttpResponseHandler handler) {
		post(url, null, params, contentType, new HttpConfig(), handler);
	}

	public void post(String url, RequestParams params, HttpConfig config,
			HttpResponseHandler handler) {
		post(url, null, params, null, config, handler);
	}

	public void post(String url, Header[] headers, RequestParams params,
			String contentType, HttpConfig config,
			final HttpResponseHandler handler) {
		com.loopj.android.http.RequestParams param = null;
		if (params != null) {
			param = new com.loopj.android.http.RequestParams(params.getParams());
		}
		if (config.isVerify()) {
			if (param == null) {
				param = new com.loopj.android.http.RequestParams();
			}
//			Secret secret = HttpSecret.getSecret();
//			param.put("idfs", secret.getIdfs());
//			param.put("secret", secret.getSecret());
		}
		LogUtils.i("[POST]---" + RequestParams.getUrlParams(url, params));
		param.setContentEncoding(params.getCharset());
		client.post(context, url, headers, param, contentType,
				new TextHttpResponseHandler(handler.getChartSet()) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						LogUtils.i("---data[http]");
						handler.onSuccess(responseString);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						LogUtils.i("[POST-Error]---" + statusCode + "----"
								+ responseString);
						handler.onFailure(statusCode);
					}
				});
	}

	// public void post(String url, RequestParams params,HttpConfig config,
	// final HttpResponseHandler handler) {
	// com.loopj.android.http.RequestParams param = null;
	// if (params != null) {
	// param = new com.loopj.android.http.RequestParams(params.getParams());
	// }
	// if (config.isVerify()) {
	// if (param == null) {
	// param = new com.loopj.android.http.RequestParams();
	// }
	// Secret secret = HttpSecret.getSecret();
	// param.put("idfs", secret.getIdfs());
	// param.put("secret", secret.getSecret());
	// }
	// LogUtils.i("[POST]---" + RequestParams.getUrlParams(url, params));
	// param.setContentEncoding(params.getCharset());
	//
	// TextHttpResponseHandler responseHandler = new
	// TextHttpResponseHandler(handler.getChartSet()) {
	// @Override
	// public void onSuccess(int statusCode, Header[] headers,
	// String responseString) {
	// LogUtils.i("---data[http]");
	// handler.onSuccess(responseString);
	// }
	//
	// @Override
	// public void onFailure(int statusCode, Header[] headers,
	// String responseString, Throwable throwable) {
	// LogUtils.i("[POST-Error]---" + statusCode + "----"
	// + responseString);
	// handler.onFailure(statusCode);
	// }
	// };
	// try {
	// param.setUseJsonStreamer(true);
	// HttpEntity httpEntity = param.getEntity(responseHandler);
	// client.post(context, url, httpEntity, null, responseHandler);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	public void delete(String url, RequestParams params,
			final HttpResponseHandler handler) {
		delete(url, params, new HttpConfig(), handler);
	}

	public void delete(String url, RequestParams params, HttpConfig config,
			final HttpResponseHandler handler) {
		com.loopj.android.http.RequestParams param = null;
		if (params != null) {
			param = new com.loopj.android.http.RequestParams(params.getParams());
		}
		if (config.isVerify()) {
			if (param == null) {
				param = new com.loopj.android.http.RequestParams();
			}
			// Secret secret = HttpSecret.getSecret();
			// param.put("idfs", secret.getIdfs());
			// param.put("secret", secret.getSecret());
		}
		LogUtils.i("[POST]---" + RequestParams.getUrlParams(url, params));
		param.setContentEncoding(params.getCharset());
		client.delete(url, param,
				new TextHttpResponseHandler(handler.getChartSet()) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						LogUtils.i("---data[http]");
						handler.onSuccess(responseString);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						LogUtils.i("[DELETE-Error]---" + statusCode + "----"
								+ responseString);
						handler.onFailure(statusCode);
					}
				});
	}

	public void cancelRequest(Context context) {
		client.cancelRequests(context, true);
	}

	public void cancelAllRequest() {
		client.cancelAllRequests(true);
	}

}
