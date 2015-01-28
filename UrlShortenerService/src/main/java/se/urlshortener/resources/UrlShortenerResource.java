package se.urlshortener.resources;

import com.codahale.metrics.annotation.Timed;
import se.urlshortener.UrlShortenerConfiguration;
import se.urlshortener.representation.Url;
import se.urlshortener.util.CassandraDBDAO;
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
    private boolean cassandraDBEnabled;
    private String baseURL;

    public UrlShortenerResource(Map<String, String> storedValuesTest) {
        this.cachedKeys = storedValuesTest;
        this.cassandraDBEnabled = false;
        this.baseURL = "http://localhost:8080/UrlShortener";
    }

    public UrlShortenerResource(UrlShortenerConfiguration urlShortenerConfiguration) {
        this.baseURL = urlShortenerConfiguration.getBaseURL();
        if (urlShortenerConfiguration.getEnableCassandra().equals("true")) {
            this.cassandraDBEnabled = true;
            CassandraDBDAO.init(urlShortenerConfiguration);
        } else {
            this.cachedKeys = new ConcurrentHashMap<String, String>();
            this.cassandraDBEnabled = false;
        }
    }

    @GET
    @Path("/{shortUrl}")
    @Timed
    public Response fetchOriginalURL(@PathParam("shortUrl") String shortUrl) {
        try {
            if (cassandraDBEnabled) {
                String result = CassandraDBDAO.getOriginalURL(shortUrl);
                if (result != null) {
                    URI location = new URI(result);
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
        if (cassandraDBEnabled) {
            String result = CassandraDBDAO.getOriginalURL(shortUrl);
            if (result == null) {
                CassandraDBDAO.putNewEncodedURL(shortUrl, url);
            }
        } else {
            if (!cachedKeys.containsKey(shortUrl)) {
                cachedKeys.put(shortUrl, url);
            }
        }

    }
}
