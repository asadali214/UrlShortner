package com.gr.url.core.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.gr.common.dao.GenericDao;
import com.gr.url.core.model.Click;
import com.gr.url.core.model.Url;

public interface UrlDao extends GenericDao<Url, Integer> {

	public List<Url> getAllUrls();

	public int addNewUrl(Url url);

	public Url updateUrl(int UrlId, Url urlNew);

	public int deleteUrl(int UrlId);

	public Url getUrl(int UrlId);

	public List<Click> getAllClicksOnUrl(int UrlId);

	public int addClickOnUrl(Click click);

	public Url getLastUrl();

	public HashMap<Date, Integer> getDateStats(int UrlId);

	public HashMap<String, Integer> getBrowserStats(int UrlId);

	public HashMap<String, Integer> getPlatformStats(int UrlId);

}
