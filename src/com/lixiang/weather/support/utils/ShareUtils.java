package com.lixiang.weather.support.utils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.lixiang.weather.model.ShareData;

public class ShareUtils {
	private ShareParams params;
	private String imgUrl = "http://pp.myapp.com/ma_icon/0/icon_11362184_20668449_1426212967/96";
	private PlatformActionListener actionListener;

	public ShareUtils() {

	}

	public void setImgTextData(ShareData share) {
		String url = share.getUrl()
				+ (share.isTopicShare() ? "?download_url=1" : "");
		this.params = new ShareParams();
		params.setText(share.getText());
		params.setShareType(Platform.SHARE_WEBPAGE);
		params.setTitle(!TextUtils.isEmpty(share.getTitle()) ? share.getTitle()
				: share.getText());
		params.setTitleUrl(url);
		params.setUrl(url);
		params.setImageUrl(!TextUtils.isEmpty(share.getImg()) ? TextUtils
				.isHttpHost(share.getImg()) : imgUrl);
	}

	public void setTextData(ShareData share) {
		String url = share.getUrl()
				+ (share.isTopicShare() ? "?download_url=1" : "");
		this.params = new ShareParams();
		params.setShareType(Platform.SHARE_TEXT);
		params.setText(share.getText() + " " + url);
		params.setTitle(!TextUtils.isEmpty(share.getTitle()) ? share.getTitle()
				: share.getText());
	}

	public void setImgData(ShareData share) {
		this.params = new ShareParams();
		params.setText("");
		params.setTitle("");
		params.setShareType(Platform.SHARE_IMAGE);
		params.setImagePath(share.getImg());
		// params.setImageUrl(imgUrl);
	}

	public void setPlatformActionListener(PlatformActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void doShare(String name) {
		Platform plat = ShareSDK.getPlatform(name);
		if (actionListener != null) {
			plat.setPlatformActionListener(actionListener);
		}
		if (name.equals(SinaWeibo.NAME)) {
			if (params.getText().length() >= 120) {
				params.setText(params.getText().substring(0, 120));
			}
			params.setText(params.getText() + " " + params.getUrl());
		} else {
			params.setText(params.getText());
		}
		plat.share(params);
	}
}
