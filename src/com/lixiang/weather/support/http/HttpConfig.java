package com.lixiang.weather.support.http;

public class HttpConfig {
	// 缓存的页数，为0不缓存，大于0则判断是否有分页，有则缓存当前页数，没有缓存URL
	private int cachePage = 0;
	// 是否是刷新操作，刷新则直接从服务器获取数据
	private boolean isRefresh = false;
	// 缓存时间
	private int saveTime = 60 * 60 * 6;
	// 请求是否带验证
	private boolean isVerify = true;

	public int getCachePage() {
		return cachePage;
	}

	public void setCachePage(int cachePage) {
		this.cachePage = cachePage;
	}

	public boolean isRefresh() {
		return isRefresh;
	}

	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	public int getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(int saveTime) {
		this.saveTime = saveTime;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

}
