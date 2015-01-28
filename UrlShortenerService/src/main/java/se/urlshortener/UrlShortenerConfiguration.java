package se.urlshortener;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Alberto on 25/09/2014.
 */
public class UrlShortenerConfiguration extends Configuration {
    @NotEmpty
    private String baseURL;
    @NotEmpty
    private String enableCassandra;
    private String client;
    private String tableName;
    private String keyspace;

    @JsonProperty
    public String getBaseURL() {
        return baseURL;
    }

    @JsonProperty
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    @JsonProperty
    public String getEnableCassandra() {
        return enableCassandra;
    }

    @JsonProperty
    public void setEnableCassandra(String enableCassandra) {
        this.enableCassandra = enableCassandra;
    }

    @JsonProperty
    public String getClient() {
        return client;
    }

    @JsonProperty
    public void setClient(String client) {
        this.client = client;
    }

    @JsonProperty
    public String getTableName() {
        return tableName;
    }

    @JsonProperty
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @JsonProperty
    public String getKeyspace() {
        return keyspace;
    }

    @JsonProperty
    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }
}
