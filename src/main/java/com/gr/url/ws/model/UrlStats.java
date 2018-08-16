package com.gr.url.ws.model;

import java.util.HashMap;

public class UrlStats {
	
	HashMap<String, ClickInfo> urlInfo=new HashMap<>();

	public HashMap<String, ClickInfo> getUrlInfo() {
		return urlInfo;
	}

	public void setUrlInfo(HashMap<String, ClickInfo> urlInfo) {
		this.urlInfo = urlInfo;
	}
	

}
