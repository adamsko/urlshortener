package com.sko;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.sko.configuration.UrlShortenerConfiguration;
import com.sko.health.MongoHealthCheck;
import com.sko.model.ShortUrl;
import com.sko.resource.UrlRedirectorResource;
import com.sko.resource.UrlResource;
import com.sko.resource.UrlShortenerResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import net.vz.mongodb.jackson.JacksonDBCollection;

public class UrlShortenerService extends Service<UrlShortenerConfiguration> {

    public static void main(String[] args) throws Exception {
        new UrlShortenerService().run(args);
    }

    @Override
    public void initialize(Bootstrap<UrlShortenerConfiguration> bootstrap) {
        bootstrap.setName("url-shortener");
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(UrlShortenerConfiguration configuration, Environment environment) throws Exception {
        Mongo mongo = new Mongo(configuration.mongohost, configuration.mongoport);
        DB db = mongo.getDB(configuration.mongodb);
        JacksonDBCollection<ShortUrl, String> urls =
                JacksonDBCollection.wrap(db.getCollection("urls"), ShortUrl.class, String.class);

        MongoManaged mongoManaged = new MongoManaged(mongo);
        environment.manage(mongoManaged);

        environment.addHealthCheck(new MongoHealthCheck(mongo));

        environment.addResource(new UrlShortenerResource(urls));
        environment.addResource(new UrlRedirectorResource(urls));
        environment.addResource(new UrlResource(urls));
    }
}
