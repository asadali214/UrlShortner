package com.gr.url.core.model;

import java.util.Date;
import java.util.HashMap;

public class ClickStats {
	HashMap<Date, Integer> dateStats = new HashMap<Date, Integer>();
	HashMap<String, Integer> browserStats = new HashMap<String, Integer>();
	HashMap<String, Integer> platformStats = new HashMap<String, Integer>();

	public HashMap<Date, Integer> getDateStats() {
		return dateStats;
	}

	public void setDateStats(HashMap<Date, Integer> dateStats) {
		this.dateStats = dateStats;
	}

	public HashMap<String, Integer> getBrowserStats() {
		return browserStats;
	}

	public void setBrowserStats(HashMap<String, Integer> browserStats) {
		this.browserStats = browserStats;
	}

	public HashMap<String, Integer> getPlatformStats() {
		return platformStats;
	}

	public void setPlatformStats(HashMap<String, Integer> platformStats) {
		this.platformStats = platformStats;
	}

}
