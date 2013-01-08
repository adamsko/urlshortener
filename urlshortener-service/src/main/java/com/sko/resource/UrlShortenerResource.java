package com.sko.resource;

import com.sko.model.ShortUrl;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

import static com.sko.resource.util.ResourceHelper.notFoundIfNull;

@Path("/shorten")
@Produces(MediaType.APPLICATION_JSON)
public class UrlShortenerResource {

    private static final Logger log = LoggerFactory.getLogger(UrlShortenerResource.class);

    private final JacksonDBCollection<ShortUrl, String> urls;

    public UrlShortenerResource(JacksonDBCollection<ShortUrl, String> urls) {
        this.urls = urls;
    }

    @GET
    public ShortUrl createShortUrl(@QueryParam("longUrl") String longUrl) {
        notFoundIfNull(longUrl);
        try {
            URL url = new URL(longUrl);
        } catch (MalformedURLException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(longUrl);
        shortUrl.setShortUrl("shorturl");
        WriteResult<ShortUrl, String> result = urls.save(shortUrl);
        shortUrl = result.getSavedObject();
        return shortUrl;
    }

}

