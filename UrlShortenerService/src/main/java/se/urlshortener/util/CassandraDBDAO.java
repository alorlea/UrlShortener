package se.urlshortener.util;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import se.urlshortener.UrlShortenerConfiguration;

import java.util.UUID;

/**
 * Created by Alberto on 2015-01-28.
 */
public class CassandraDBDAO {
    private static String tableName;
    private static String keyspace;

    private static Cluster cassandraDB;

    public static void init(UrlShortenerConfiguration configuration){
        String cluster = configuration.getClient();
        keyspace = configuration.getKeyspace();
        tableName = configuration.getTableName();
        cassandraDB = Cluster.builder().addContactPoint(cluster).build();
    }

    private static Session getSession(){
        return cassandraDB.connect();
    }

    public static void putNewEncodedURL(String shortUrl, String originalUrl){
        String uuid = UUID.randomUUID().toString();
        getSession().execute("INSERT INTO " + keyspace + "." + tableName + " (id, shortUrl, originalUrl)" +
                "VALUES (" + uuid + ",'" + shortUrl + "'," + "'" + originalUrl + "')" + ";");
    }


    public static String getOriginalURL(String shortUrl){
        ResultSet results = getSession().execute("SELECT * FROM " + keyspace + "." + tableName
                +" WHERE shortUrl = '" + shortUrl + "' ALLOW FILTERING;");

        Row row1 = results.one();
        String result = null;
        if(row1 != null) {
            result = row1.getString("originalUrl");
        }
        for (Row row : results) {
            System.out.println(String.format("%-30s\t%-20s",
                    row.getString("shortUrl"),
                    row.getString("originalUrl")));
        }
        return result;
    }

}
