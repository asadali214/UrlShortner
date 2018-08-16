package com.gr.url.main;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.gr.url.ws.exception.WsExceptionHandler;
import com.gr.url.ws.filter.WsCorsFilter;
import com.gr.url.ws.v1.UrlResource;


@ApplicationPath("")
public class Main extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public Main() {
		
		classes.add(UrlResource.class);
		classes.add(WsExceptionHandler.class);
		classes.add(WsCorsFilter.class);
		
		System.setProperty("jsse.enableSNIExtension", "false");
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
