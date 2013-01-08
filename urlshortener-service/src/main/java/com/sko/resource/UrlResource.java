package com.sko.resource;

import com.sko.model.ShortUrl;
import net.vz.mongodb.jackson.JacksonDBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.sko.resource.util.ResourceHelper.notFoundIfNull;

@Path("/url")
@Produces(MediaType.APPLICATION_JSON)
public class UrlResource {

    private static final Logger log = LoggerFactory.getLogger(UrlShortenerResource.class);

    private final JacksonDBCollection<ShortUrl, String> urls;

    public UrlResource(JacksonDBCollection<ShortUrl, String> urls) {
        this.urls = urls;
    }

    @GET
    public List<ShortUrl> getAll() {
        return urls.find().toArray();
    }

    @GET
    @Path("{id}")
    public ShortUrl getOne(@PathParam("id") String id) {
        ShortUrl shortUrl = urls.findOneById(id);
        notFoundIfNull(shortUrl);
        return shortUrl;
    }

}
