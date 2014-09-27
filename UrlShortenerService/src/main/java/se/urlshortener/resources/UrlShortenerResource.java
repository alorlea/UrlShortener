package se.urlshortener.resources;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.codahale.metrics.annotation.Timed;
import se.urlshortener.UrlShortenerConfiguration;
import se.urlshortener.representation.Url;
import se.urlshortener.util.AmazonDBDAOStore;
import se.urlshortener.util.UrlShortenerUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alberto on 25/09/2014.
 */

@Path("/UrlShortener")
@Produces(MediaType.APPLICATION_JSON)
public class UrlShortenerResource {
    private Map<String, String> cachedKeys;
    private boolean amazonDBEnabled;
    private String baseURL;

    public UrlShortenerResource(Map<String, String> storedValuesTest) {
        this.cachedKeys = storedValuesTest;
        this.amazonDBEnabled = false;
        this.baseURL = "http://localhost:8080/UrlShortener";
    }

    public UrlShortenerResource(UrlShortenerConfiguration urlShortenerConfiguration) {
        this.baseURL = urlShortenerConfiguration.getBaseURL();
        if (urlShortenerConfiguration.getEnableAmazonDB().equals("true")) {
            this.amazonDBEnabled = true;
            AmazonDBDAOStore.init(urlShortenerConfiguration);
        } else {
            this.cachedKeys = new ConcurrentHashMap<String, String>();
            this.amazonDBEnabled = false;
        }
    }

    @GET
    @Path("/{shortUrl}")
    @Timed
    public Response fetchOriginalURL(@PathParam("shortUrl") String shortUrl) {
        try {
            if (amazonDBEnabled) {
                Map<String, AttributeValue> result = AmazonDBDAOStore.getOriginalURL(shortUrl);
                if (result != null) {
                    String originalUrl = result.get("originalurl").getS();
                    URI location = new URI(originalUrl);
                    return Response.temporaryRedirect(location).build();
                } else {
                    return Response.status(404).build();
                }
            } else {
                if (cachedKeys.containsKey(shortUrl)) {
                    URI location = new URI(cachedKeys.get(shortUrl));
                    return Response.temporaryRedirect(location).build();
                } else {
                    return Response.status(404).build();
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Url createNewShortURL(Url url) {
        String originalUrl = url.getUrl();
        String shortUrl = UrlShortenerUtil.encodeURL(originalUrl);
        storeNewEntry(shortUrl, originalUrl);
        return new Url(baseURL+"/"+shortUrl);
    }

    private void storeNewEntry(String shortUrl, String url) {
        //check my if exists
        if (amazonDBEnabled) {
            Map<String, AttributeValue> result = AmazonDBDAOStore.getOriginalURL(shortUrl);
            if (result == null) {
                AmazonDBDAOStore.putNewEncodedURL(shortUrl, url);
            }
        } else {
            if (!cachedKeys.containsKey(shortUrl)) {
                cachedKeys.put(shortUrl, url);
            }
        }

    }
}
