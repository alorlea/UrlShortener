package se.urlshortener.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alberto on 25/09/2014.
 */
public class ShortUrl {
    private String originalURL;
    private String shortURL;

    public ShortUrl() {

    }

    public ShortUrl(String originalURL, String shortURL) {
        this.originalURL = originalURL;
        this.shortURL = shortURL;
    }

    @JsonProperty
    public String getOriginalURL() {
        return originalURL;
    }

    @JsonProperty
    public String getShortURL() {
        return shortURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortUrl shortUrl = (ShortUrl) o;

        if (!originalURL.equals(shortUrl.originalURL)) return false;
        if (!shortURL.equals(shortUrl.shortURL)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originalURL.hashCode();
        result = 31 * result + shortURL.hashCode();
        return result;
    }
}
