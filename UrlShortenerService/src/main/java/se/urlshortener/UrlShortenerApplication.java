package se.urlshortener;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import se.urlshortener.health.GetHealthCheck;
import se.urlshortener.representation.ShortUrl;
import se.urlshortener.resources.UrlShortenerResource;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alberto on 25/09/2014.
 */
public class UrlShortenerApplication extends Application<UrlShortenerConfiguration> {

    public static void main(String[] args) throws Exception {
        new UrlShortenerApplication().run(args);
    }

    @Override
    public String getName() {
        return "url-shortener";
    }
    @Override
    public void initialize(Bootstrap<UrlShortenerConfiguration> urlShortenerConfigurationBootstrap) {

    }

    @Override
    public void run(UrlShortenerConfiguration urlShortenerConfiguration, Environment environment) throws Exception {
        ConcurrentHashMap<String,String> cache = new ConcurrentHashMap<String,String>(100);
        final ShortUrl shortUrl = new ShortUrl("http://dice.se/","0Cx8xg==");
        final ShortUrl otherShortUrl = new ShortUrl("http://www.google.com","sTE3IQ==");
        cache.put(shortUrl.getShortURL(),shortUrl.getOriginalURL());
        cache.put(otherShortUrl.getShortURL(),otherShortUrl.getOriginalURL());

        final UrlShortenerResource resource = new UrlShortenerResource(cache);
        final GetHealthCheck healthCheck = new GetHealthCheck("http://dice.se/");

        //TODO HealthCheck correctly
        environment.healthChecks().register("GetURLToDICEWorks",healthCheck);
        environment.jersey().register(resource);
    }
}
