package com.lixiang.weather.model;

import java.util.List;

public class ModelHandler<T>{
	public void onSuccess(String data){};
	public void onSuccess(T t){};
	public void onSuccess(List<T> t){};
	public void onFail(){};
}
