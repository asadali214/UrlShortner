
package com.gr.url.core.model;

import java.io.Serializable;
import java.util.Date;

public class Click implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private int id;

	private Date dateClicked;

	private String browserClicked;

	private String platformClicked;

	private int urlId;


	public Click() {

	}
	
	
	public Click(Date dateClicked, String browserClicked, String platformClicked, int urlId) {
		super();
		this.dateClicked = dateClicked;
		this.browserClicked = browserClicked;
		this.platformClicked = platformClicked;
		this.urlId = urlId;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getDateClicked() {
		return dateClicked;
	}


	public void setDateClicked(Date dateClicked) {
		this.dateClicked = dateClicked;
	}


	public String getBrowserClicked() {
		return browserClicked;
	}


	public void setBrowserClicked(String browserClicked) {
		this.browserClicked = browserClicked;
	}


	public String getPlatformClicked() {
		return platformClicked;
	}


	public void setPlatformClicked(String platformClicked) {
		this.platformClicked = platformClicked;
	}


	public int getUrlId() {
		return urlId;
	}


	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	

}
