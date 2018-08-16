package com.gr.url.ws.v1;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
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

import com.gr.url.core.model.Click;
import com.gr.url.core.model.ClickStats;
import com.gr.url.core.model.Url;
import com.gr.url.core.service.UrlService;
import com.gr.url.core.service.UrlServiceLocal;

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

			} else {
				URI uriLong = null;
				try {
					uriLong = new URI("link to the expired error page here..");
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				response = Response.seeOther(uriLong).build();

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
	public int addNewUrl(Url url) {
		if (service.checkLongUrl(url.getLongUrl()) == null) {// if the long url is not already registered..
			// generate shortUrl by taking the last row id of the database..
			String shortUrl = service.generateShortUrl();
			url.setShortUrl(shortUrl);
			Date currentDate = new Date();
			url.setDateCreated(currentDate);
			return service.addNewUrl(url);
		}
		return -1;
	}

	@GET
	@Path("/function/getClickFullStats/{urlId}")
	public ClickStats getClickFullStats(@PathParam("urlId") int urlId) {
		ClickStats fullStats = new ClickStats();
		fullStats.setDateStats(service.getDateStats(urlId));
		fullStats.setBrowserStats(service.getBrowserStats(urlId));
		fullStats.setPlatformStats(service.getPlatformStats(urlId));
		return fullStats;
	}

}
