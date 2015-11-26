package com.lixiang.weather.support.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.os.Environment;

import com.lixiang.weather.config.AppConfig;

public class FileUtils {
	public static File createCameraFile(Context context){
		return createImageFile(context, AppConfig.cameraPath);
	}
	public static File createCropperFile(Context context){
		return createImageFile(context, AppConfig.cropperPath);
	}
    public static File createImageFile(Context context,String path){
        String state = Environment.getExternalStorageState();
        File file = null;
        if(state.equals(Environment.MEDIA_MOUNTED)){
            file = Environment.getExternalStorageDirectory();
        }else{
        	file = context.getCacheDir();
        }
        File newFile = new File(file.getAbsolutePath()+path);
        if(!newFile.exists() && newFile.mkdirs()){
        	
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = AppConfig.APP_NAME+"_"+timeStamp+"";
        File tmpFile = new File(newFile, fileName+".jpeg");
        return tmpFile;
    }
}
