package com.lixiang.weather.model;


public class ShareData {
	public static final int IMGTEXT =0;
	public static final int TEXT = 1;
	public static final int IMG = 2;
	private int type = IMGTEXT;
	private String title;
	private String text;
	private String url;
	private String img;
	private boolean isTopicShare = true;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public boolean isTopicShare() {
		return isTopicShare;
	}
	public void setTopicShare(boolean isTopicShare) {
		this.isTopicShare = isTopicShare;
	}
}
