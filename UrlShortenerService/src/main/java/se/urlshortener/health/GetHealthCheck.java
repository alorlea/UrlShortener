package se.urlshortener.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by Alberto on 27/09/2014.
 */
public class GetHealthCheck extends HealthCheck {
    private final String checkString;

    public GetHealthCheck(String checkURL) {
        this.checkString = checkURL;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
