package se.urlshortener;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Alberto on 25/09/2014.
 */
public class UrlShortenerConfiguration extends Configuration {
    @NotEmpty
    private String enableAmazonDB;
    private String awsAccessKeyId;
    private String secretAccessKey;
    private String awsRegion;
    private String awsTableName;

    @JsonProperty
    public String getEnableAmazonDB() {
        return enableAmazonDB;
    }

    @JsonProperty
    public void setEnableAmazonDB(String enableAmazonDB) {
        this.enableAmazonDB = enableAmazonDB;
    }

    @JsonProperty
    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    @JsonProperty
    public void setAwsAccessKeyId(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    @JsonProperty
    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    @JsonProperty
    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    @JsonProperty
    public String getAwsRegion() {
        return awsRegion;
    }

    @JsonProperty
    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    @JsonProperty
    public String getAwsTableName() {
        return awsTableName;
    }

    @JsonProperty
    public void setAwsTableName(String awsTableName) {
        this.awsTableName = awsTableName;
    }
}
