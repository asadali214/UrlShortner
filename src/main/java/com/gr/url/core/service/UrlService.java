package com.gr.url.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import com.gr.common.service.ServiceManager;
import com.gr.url.core.dao.UrlDaoHibernateImpl;
import com.gr.url.core.model.Click;
import com.gr.url.core.model.Url;

@Stateless
public class UrlService implements UrlServiceLocal {

	public static UrlServiceLocal getService() {
		return (UrlServiceLocal) ServiceManager.getService(UrlServiceLocal.class);
	}

	@Override
	public List<Url> getAllUrls() {
		return UrlDaoHibernateImpl.getDao().getAllUrls();
	}

	@Override
	public int addNewUrl(Url url) {
		return UrlDaoHibernateImpl.getDao().addNewUrl(url);
	}

	@Override
	public Url updateUrl(int UrlId, Url urlNew) {
		return UrlDaoHibernateImpl.getDao().updateUrl(UrlId, urlNew);
	}

	@Override
	public int deleteUrl(int UrlId) {

		return UrlDaoHibernateImpl.getDao().deleteUrl(UrlId);
	}

	@Override
	public Url getUrl(int UrlId) {
		return UrlDaoHibernateImpl.getDao().getUrl(UrlId);
	}

	@Override
	public List<Click> getAllClicksOnUrl(int UrlId) {
		return UrlDaoHibernateImpl.getDao().getAllClicksOnUrl(UrlId);
	}

	@Override
	public int addClickOnUrl(Click click) {
		return UrlDaoHibernateImpl.getDao().addClickOnUrl(click);
	}

	@Override
	public Url getLongUrl(String shortUrl) {
		ArrayList<Url> urls = (ArrayList<Url>) getAllUrls();
		for (Url url : urls) {
			if (url.getShortUrl().equals(shortUrl)) {
				return url;
			}
		}
		return null;
	}

	@Override
	public Url checkLongUrl(String longUrl) {
		ArrayList<Url> urls = (ArrayList<Url>) getAllUrls();
		for (Url url : urls) {
			if (url.getLongUrl().equals(longUrl)) {
				return url;
			}
		}
		return null;
	}

	@Override
	public String generateShortUrl() {
		// there can be so many ways to generate short urls..
		Url url = UrlDaoHibernateImpl.getDao().getLastUrl();
		int key = 0;
		if (url == null)
			key = 9;
		else
			key = url.getId();
		String shortUrl = "gr" + (key + 1);
		return shortUrl;
	}

	@Override
	public Boolean checkUrlExpiry(Url url) {
		Date creation = url.getDateCreated();
		int expiryPeriod = url.getExpiryDays();
		Date now = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(creation);
		cal.add(Calendar.DATE, expiryPeriod);
		Date expiryDate = cal.getTime();

		if (now.compareTo(expiryDate) > 0)
			return false;// if expired
		else
			return true;// if not expired
	}

	@Override
	public HashMap<Date, Integer> getDateStats(int UrlId) {
		return UrlDaoHibernateImpl.getDao().getDateStats(UrlId);
	}

	@Override
	public HashMap<String, Integer> getBrowserStats(int UrlId) {
		return UrlDaoHibernateImpl.getDao().getBrowserStats(UrlId);
	}

	@Override
	public HashMap<String, Integer> getPlatformStats(int UrlId) {
		return UrlDaoHibernateImpl.getDao().getPlatformStats(UrlId);
	}

}
