package se.urlshortener.representation;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Alberto on 25/09/2014.
 */
public class ShortUrlTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializeToJSON() throws Exception{
        final ShortUrl shortUrl = new ShortUrl("http://www.google.com","http://hostname/hashURL");
        assertThat(MAPPER.writeValueAsString(shortUrl)).isEqualTo(fixture("fixtures/shortUrl.json"));
    }

    @Test
    public void deserializeFromJSON() throws Exception{
        final ShortUrl shortUrl = new ShortUrl("http://www.google.com","http://hostname/hashURL");
        assertThat(MAPPER.readValue(fixture("fixtures/shortUrl.json"), ShortUrl.class));
    }

    @Test
    public void sameShortURLEqualsFields() throws Exception{
        final ShortUrl shortUrl1 = new ShortUrl("http://www.google.com","http://hostname/hashURL");
        final ShortUrl shortUrl2 = new ShortUrl("http://www.google.com","http://hostname/hashURL");
        assertThat(shortUrl1).isEqualsToByComparingFields(shortUrl2);
    }
    @Test
    public void sameShortURLObjects() throws Exception{
        final ShortUrl shortUrl1 = new ShortUrl("http://www.google.com","http://hostname/hashURL");
        final ShortUrl shortUrl2 = new ShortUrl("http://www.google.com","http://hostname/hashURL");
        assertEquals(shortUrl1,shortUrl2);
    }
}
