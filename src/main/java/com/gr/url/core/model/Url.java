package com.gr.url.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Url implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String longUrl;

	private String shortUrl;

	private Date dateCreated;
	
	private int expiryDays;

	private List<Click> clicks;

	public Url() {
		
	}
	

	public Url(String longUrl, String shortUrl, Date dateCreated, List<Click> clicks) {
		super();
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
		this.dateCreated = dateCreated;
		this.clicks = clicks;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getExpiryDays() {
		return expiryDays;
	}


	public void setExpiryDays(int expiryDays) {
		this.expiryDays = expiryDays;
	}

	public List<Click> getClicks() {
		return clicks;
	}

	public void setClicks(List<Click> clicks) {
		this.clicks = clicks;
	}
	
	
	

}
