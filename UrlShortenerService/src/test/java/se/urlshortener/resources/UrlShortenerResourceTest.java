package se.urlshortener.resources;

import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import se.urlshortener.representation.ShortUrl;
import se.urlshortener.representation.Url;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Alberto on 26/09/2014.
 */
public class UrlShortenerResourceTest{

    private static final Map<String,String> testURLs = new HashMap<String,String>();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UrlShortenerResource(testURLs))
            .build();

    private final ShortUrl shortUrl = new ShortUrl("http://dice.se/","0Cx8xg==");
    private final ShortUrl otherShortUrl = new ShortUrl("http://www.google.com","sTE3IQ==");

    @Before
    public void setup(){
        testURLs.put(shortUrl.getShortURL(),shortUrl.getOriginalURL());
        testURLs.put(otherShortUrl.getShortURL(),otherShortUrl.getOriginalURL());
    }

    @Test
    public void testGetOriginalURL(){
        ClientResponse response = resources.client().resource("/UrlShortener/0Cx8xg==").get(ClientResponse.class);
        assertEquals(response.getStatus(),307);
        assertEquals(response.getLocation().toString(),"http://dice.se/");
    }
    @Test
    public void testGetFailsWithUnprocessedURL(){
        ClientResponse response = resources.client().resource("/UrlShortener/thisTestFails").get(ClientResponse.class);
        assertEquals(response.getStatus(),404);
    }
    @Test
    public void testPutOriginalURL(){
        Url url = new Url("http://www.elotrolado.net/");
        Url newUrl = resources.client().resource("/UrlShortener").type(MediaType.APPLICATION_JSON_TYPE).put(Url.class,
                url);
        assertEquals(newUrl.getUrl(),"http://localhost:8080/UrlShortener/zBByyw==");
    }
}
