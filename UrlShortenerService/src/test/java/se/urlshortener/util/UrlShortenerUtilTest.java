package se.urlshortener.util;

import org.junit.Test;
import se.urlshortener.representation.Url;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Alberto on 25/09/2014.
 */
public class UrlShortenerUtilTest {

    @Test
    public void encodingURLGivesShortURL(){
        Url shortUrl = UrlShortenerUtil.encodeURL("http://dice.se/","http://localhost:8080/UrlShortener/");
        assertEquals("http://localhost:8080/UrlShortener/0Cx8xg==",shortUrl.getUrl());
    }

    @Test
    public void twoDifferentURLsGiveDifferentResult(){
        Url shortUrl = UrlShortenerUtil.encodeURL("http://dice.se/","http://localhost:8080/UrlShortener/");
        Url otherShortUrl = UrlShortenerUtil.encodeURL("http://www.google.com","http://localhost:8080/UrlShortener/");
        assertNotEquals(shortUrl.getUrl(),otherShortUrl.getUrl());
    }
}
