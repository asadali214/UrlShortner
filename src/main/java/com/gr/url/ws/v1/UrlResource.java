package com.gr.url.ws.v1;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.gr.url.core.model.Click;
import com.gr.url.core.model.Url;
import com.gr.url.core.service.UrlService;
import com.gr.url.core.service.UrlServiceLocal;
import com.gr.url.ws.model.ClickInfo;
import com.gr.url.ws.model.UrlStats;

import eu.bitwalker.useragentutils.UserAgent;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UrlResource {

	UrlServiceLocal service = UrlService.getService();

	@GET
	@Path("/{shortUrl}")
	public Response redirectToLongUrl(@PathParam("shortUrl") String shortUrl,
			@HeaderParam("user-agent") String userAgentString) {
		Response response = null;
		Url url = service.getLongUrl(shortUrl);
		if (url != null) {
			if (service.checkUrlExpiry(url)) {
				URI uriLong = null;
				try {
					uriLong = new URI(url.getLongUrl());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				response = Response.seeOther(uriLong).build();

				UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
				String browser = userAgent.getBrowser().getName();
				String platform = userAgent.getOperatingSystem().getName();

				Date currentDate = new Date();
				int urlId = url.getId();
				Click click = new Click(currentDate, browser, platform, urlId);
				service.addClickOnUrl(click);
				//refresh front end here.. 

			} else {
				URI uriLong = null;
				try {
					uriLong = new URI("http://localhost:4200/expired");
					response = Response.seeOther(uriLong).build();
				} catch (URISyntaxException e) {
					e.printStackTrace();
					response = Response.status(Status.BAD_REQUEST).entity("This ShortUrl is expired").build();
				}

			}
		}
		else {
			URI uriLong = null;
			try {
				uriLong = new URI("http://localhost:4200/notexist");
				response = Response.seeOther(uriLong).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
				response = Response.status(Status.BAD_REQUEST).entity("Page Not found!").build();
			}
		}
		return response;

	}

	@GET
	@Path("/function/get")
	public List<Url> getAllUrls() {
		return service.getAllUrls();
	}

	@POST
	@Path("/function/add")
	public Url addNewUrl(Url url) {
		Url oldUrl = service.checkLongUrl(url.getLongUrl());
		if (oldUrl == null) {// if the long url is not already registered..
			// generate shortUrl by taking the last row id of the database..
			String shortUrl = service.generateShortUrl();
			url.setShortUrl("http://localhost:8080/url/" + shortUrl);
			Date currentDate = new Date();
			url.setDateCreated(currentDate);
			service.addNewUrl(url);
			return url;
		}
		return oldUrl;
	}

	@GET
	@Path("/function/getClickFullStats/{urlId}")
	public UrlStats getClickFullStats(@PathParam("urlId") int urlId) {
		HashMap<String, ClickInfo> urlInfo = new HashMap<>();

		urlInfo.put("DateStats", service.getDateStats(urlId));
		urlInfo.put("BrowserStats", service.getBrowserStats(urlId));
		urlInfo.put("PlatformStats", service.getPlatformStats(urlId));

		UrlStats fullStats = new UrlStats();
		fullStats.setUrlInfo(urlInfo);
		return fullStats;
	}

}
