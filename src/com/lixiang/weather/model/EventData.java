package com.lixiang.weather.model;

import com.lixiang.weather.support.database.db.annotation.Column;
import com.lixiang.weather.support.database.db.annotation.Id;
import com.lixiang.weather.support.database.db.annotation.Table;
@Table(name="event")
public class EventData {
	
	
//	用户打开应用	0	无
	public static final int OPEN_APP = 0;
//	用户关闭应用	1	无
	public static final int COLSE_APP = 1;
//	点击焦点图	2	{"id": "焦点图id"}
	public static final int CLICK_CAROUSEL = 2;
//	点击攻略	3	{"id": "攻略id"}
	public static final int CLICK_ARTICLE = 3;
//	点击活动	4	{"id": "活动id"}
	public static final int CLICK_SUBJECT = 4;
//	点击话题	5	{"id": "话题id"}
	public static final int CLICK_TOPIC = 5;
//	收藏攻略文章	6	{"id": "攻略文章id"}
	public static final int COLLECT_ARTICLE = 6;
//	点击攻略分类	7	{"id": "分类id"}
	public static final int CLICK_ARTICLE_CATEGORY = 7;
//	页面－攻略	8	无
	public static final int OPEN_ARTICLE = 8;
//	页面－帮会	9	无
	public static final int OPEN_GROUP = 9;
//	页面－我	10	无
	public static final int OPEN_ME = 10;
//	分享文章	11	{"id": "攻略id"}
	public static final int SHARE_ATRICLE = 11;
	@Id
	private int did;
	private int type;
	@Column(column="when_time")
	private String when;
	private String data;
	public EventData() {
		// TODO Auto-generated constructor stub
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
