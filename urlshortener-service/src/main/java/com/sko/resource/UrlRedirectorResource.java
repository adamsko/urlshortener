package com.sko.resource;

import com.sko.model.ShortUrl;
import net.vz.mongodb.jackson.JacksonDBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/go")
public class UrlRedirectorResource {

    private static final Logger log = LoggerFactory.getLogger(UrlShortenerResource.class);

    private final JacksonDBCollection<ShortUrl, String> urls;

    public UrlRedirectorResource(JacksonDBCollection<ShortUrl, String> urls) {
        this.urls = urls;
    }

    @GET
    @Path("{id}")
    public Response redirectShortUrl(@PathParam("id") String id) {
        ShortUrl shortUrl = urls.findOneById(id);
        return Response.seeOther(URI.create(shortUrl.getOriginalUrl())).build();
    }
}
