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
        String shortUrl = UrlShortenerUtil.encodeURL("http://dice.se/");
        assertEquals("0Cx8xg==",shortUrl);
    }

    @Test
    public void twoDifferentURLsGiveDifferentResult(){
        String shortUrl = UrlShortenerUtil.encodeURL("http://dice.se/");
        String otherShortUrl = UrlShortenerUtil.encodeURL("http://www.google.com");
        assertNotEquals(shortUrl,otherShortUrl);
    }
}
