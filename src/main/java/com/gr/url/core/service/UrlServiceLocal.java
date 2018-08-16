package com.gr.url.core.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import com.gr.url.core.model.Click;
import com.gr.url.core.model.Url;

@Local
public interface UrlServiceLocal {

	List<Url> getAllUrls();
	
	Url getLongUrl(String shortUrl);
	
	Url checkLongUrl(String longUrl);

	int addNewUrl(Url url);

	Url updateUrl(int UrlId, Url urlNew);

	int deleteUrl(int UrlId);

	Url getUrl(int UrlId);
	
	List<Click> getAllClicksOnUrl(int UrlId);
	
	int addClickOnUrl(Click click); 
	
	String generateShortUrl();
	
	Boolean checkUrlExpiry(Url url);
	
	HashMap<Date, Integer> getDateStats(int UrlId);
	
	HashMap<String, Integer> getBrowserStats(int UrlId);
	
	HashMap<String, Integer> getPlatformStats(int UrlId);
}
