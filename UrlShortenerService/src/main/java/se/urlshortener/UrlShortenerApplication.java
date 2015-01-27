package se.urlshortener;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import se.urlshortener.health.GetHealthCheck;
import se.urlshortener.representation.ShortUrl;
import se.urlshortener.resources.UrlShortenerResource;
import se.urlshortener.util.AmazonDBDAOStore;

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

        final UrlShortenerResource resource = new UrlShortenerResource(urlShortenerConfiguration);
        final GetHealthCheck healthCheck = new GetHealthCheck("test");

        //TODO HealthCheck correctly
        environment.healthChecks().register("ImAlive",healthCheck);
        environment.jersey().register(resource);
    }
}
