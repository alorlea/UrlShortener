package se.urlshortener.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alberto on 26/09/2014.
 */
public class Url {
    private String url;

    public Url() {
    }

    public Url(String url) {
        this.url = url;
    }
    @JsonProperty
    public String getUrl() {
        return url;
    }
}
