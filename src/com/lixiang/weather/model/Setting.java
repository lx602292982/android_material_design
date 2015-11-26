package com.lixiang.weather.model;

import com.lixiang.weather.support.database.db.annotation.Id;
import com.lixiang.weather.support.database.db.annotation.Table;

@Table(name="setting")
public class Setting {
	public static final int OPEN = 1;
	public static final int VSERSION = 2;
	
	@Id
	private int did;
	private int id;
	private String name;
	private long lastOpenTime;
	
	public Setting() {
	}
	
	public Setting(int id, String name, long lastOpenTime) {
		super();
		this.id = id;
		this.name = name;
		this.lastOpenTime = lastOpenTime;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getLastOpenTime() {
		return lastOpenTime;
	}
	public void setLastOpenTime(long lastOpenTime) {
		this.lastOpenTime = lastOpenTime;
	}
	
	
}
