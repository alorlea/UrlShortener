package se.urlshortener.util;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import se.urlshortener.UrlShortenerConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alberto on 27/09/2014.
 */
public class AmazonDBDAOStore {
    private static String tableName;
    private static AmazonDynamoDBClient dynamoDB;

    public static void init(UrlShortenerConfiguration configuration){
        String access_key_id = configuration.getAwsAccessKeyId();
        String secret_access_key = configuration.getSecretAccessKey();
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(access_key_id, secret_access_key);

        tableName = configuration.getAwsTableName();
        dynamoDB = new AmazonDynamoDBClient(awsCreds);
        dynamoDB.setRegion(Region.getRegion(Regions.fromName(configuration.getAwsRegion())));
    }

    public static void putNewEncodedURL(String shortURL, String originalURL){
        // Add an item
        Map<String, AttributeValue> item = newItem(shortURL,originalURL);
        PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result: " + putItemResult);
    }

    public static Map<String, AttributeValue> getOriginalURL(String shorturl){
        Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("shorturl", new AttributeValue(shorturl));

        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName(tableName)
                .withKey(key)
                .withAttributesToGet(Arrays.asList("originalurl"));

        GetItemResult result = dynamoDB.getItem(getItemRequest);

        // Check the response.
        System.out.println("Printing item after retrieving it....");
        printItem(result.getItem());
        return result.getItem();
    }

    private static Map<String, AttributeValue> newItem(String shorturl, String originalurl) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("shorturl", new AttributeValue(shorturl));
        item.put("originalurl", new AttributeValue(originalurl));

        return item;
    }

    private static void printItem(Map<String, AttributeValue> attributeList) {
        if(attributeList!=null) {
            for (Map.Entry<String, AttributeValue> item : attributeList.entrySet()) {
                String attributeName = item.getKey();
                AttributeValue value = item.getValue();
                System.out.println(attributeName + " "
                        + (value.getS() == null ? "" : "S=[" + value.getS() + "]")
                        + (value.getN() == null ? "" : "N=[" + value.getN() + "]")
                        + (value.getB() == null ? "" : "B=[" + value.getB() + "]")
                        + (value.getSS() == null ? "" : "SS=[" + value.getSS() + "]")
                        + (value.getNS() == null ? "" : "NS=[" + value.getNS() + "]")
                        + (value.getBS() == null ? "" : "BS=[" + value.getBS() + "] \n"));
            }
        }
    }
}
