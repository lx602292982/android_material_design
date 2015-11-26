package com.lixiang.weather.support.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	public static String listToString(List<String> list){
        if (list==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : list) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	public static List<String> getVeriftyImagesList(String images){
		List<String> list = new ArrayList<String>();
		if(TextUtils.isEmpty(images)){
			return list;
		}
		String[] imgs = images.split(",");
		for (String string : imgs) {
			list.add(TextUtils.isHttpHost(string));
		}
		return list;
	}
}
