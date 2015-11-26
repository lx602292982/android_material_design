package com.lixiang.weather.support.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiang.weather.R;
import com.lixiang.weather.support.utils.DeviceUtils;

public class LoadingDialog implements DialogInterface.OnCancelListener{
	private Context context;
	private AlertDialog dialog;
	private View loadView;
	private TextView loadText;
	private ImageView loadingCar;
	private String showText = "加载中...";
	
	private RotateAnimation mRotateAnimation;
	private OnCancelListener cancelListener;
	public LoadingDialog(Context context) {
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loadView = inflater.inflate(R.layout.dialog_loading, null);
		loadText = (TextView) loadView.findViewById(R.id.lab_message);
		loadingCar = (ImageView) loadView.findViewById(R.id.loading_car);

		dialog = new AlertDialog.Builder(context).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnCancelListener(this);
		
		mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		mRotateAnimation.setInterpolator(new LinearInterpolator());
		mRotateAnimation.setDuration(1000L);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setRepeatMode(Animation.RESTART);

	}

	public void show() {
		if (DeviceUtils.isValidContext(context)) {
			loadingCar.startAnimation(mRotateAnimation);
			dialog.show();
			dialog.getWindow().setContentView(loadView);
		}
	}

	public void show(String text) {
		loadText.setText(text);
		show();
	}
	public void show(int resId){
		show(context.getString(resId));
	}
	public void setText(String showText) {
		loadText.setText(showText);
	}
	public void setCancelable(boolean cancel){
		dialog.setCancelable(cancel);
		dialog.setCanceledOnTouchOutside(cancel);
	}
	public void dismiss() {
		if (DeviceUtils.isValidContext(context) && dialog.isShowing()) {
			loadingCar.clearAnimation();
			dialog.dismiss();
		}
	}
	public void setOnCancelListener(OnCancelListener cancelListener){
		this.cancelListener = cancelListener;
	}
	public interface OnCancelListener{
		public void onCancel(DialogInterface dialog);
	}
	@Override
	public void onCancel(DialogInterface dialog) {
		if(cancelListener != null){
			cancelListener.onCancel(dialog);
		}
	}
}
