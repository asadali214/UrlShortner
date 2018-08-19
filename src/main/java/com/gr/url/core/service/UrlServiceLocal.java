package com.gr.url.core.service;

import java.util.List;
import javax.ejb.Local;

import com.gr.url.core.model.Click;
import com.gr.url.core.model.Url;
import com.gr.url.ws.model.ClickInfo;

@Local
public interface UrlServiceLocal {

	List<Url> getAllUrls();
	
	Url getLongUrl(String shortUrl);
	
	Url checkLongUrl(String longUrl);

	String addNewUrl(Url url);

	Url updateUrl(int UrlId, Url urlNew);

	int deleteUrl(int UrlId);

	Url getUrl(int UrlId);
	
	List<Click> getAllClicksOnUrl(int UrlId);
	
	int addClickOnUrl(Click click); 
	
	String generateShortUrl();
	
	Boolean checkUrlExpiry(Url url);
	
	ClickInfo getDateStats(int UrlId);
	
	ClickInfo getBrowserStats(int UrlId);
	
	ClickInfo getPlatformStats(int UrlId);
}
