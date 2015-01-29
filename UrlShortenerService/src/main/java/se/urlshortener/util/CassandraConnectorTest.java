package se.urlshortener.util;

import com.datastax.driver.core.*;


/**
 * Created by Alberto on 2015-01-27.
 */
public class CassandraConnectorTest {
    private Cluster cluster;
    private Session session;

    public static void main(String[] args) {
        CassandraConnectorTest client = new CassandraConnectorTest();
        client.connect("172.31.41.159");
        client.createSchema();
        /*client.loadData();
        client.querySchema();
        client.dropSchema("urls");*/
        client.close();
    }


    public void connect(String node){
        cluster = Cluster.builder()
                .addContactPoint(node)
                .build();

        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts() ) {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }

        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void createSchema() {
        session.execute("CREATE KEYSPACE urls WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':3};");

        session.execute(
        "CREATE TABLE urls.encodedUrls (id uuid,shortUrl text, originalUrl text, PRIMARY KEY (id, shortUrl));");
    }

    public void loadData() {
        session.execute(
                "INSERT INTO urls.encodedUrls (id, shortUrl, originalUrl)" +
                        "VALUES (" +
                        "756716f7-2e54-4715-9f00-91dcbea6cf50,"+
                        "'test'," +
                        "'prueba')" +
                        ";");
    }


    public void querySchema(){
        ResultSet results = session.execute("SELECT * FROM urls.encodedUrls " +
                "WHERE shortUrl = 'test' ALLOW FILTERING;");

        System.out.println(String.format("%-30s\t%-20s\n%s", "shortUrl",
                "originalUrl","-------------------------------+-----------------------+--------------------"));
        for (Row row : results) {
            System.out.println(String.format("%-30s\t%-20s",
                    row.getString("shortUrl"),
                    row.getString("originalUrl")));
        }
        System.out.println();
    }

    public void dropSchema(String keyspace) {
        getSession().execute("DROP KEYSPACE " + keyspace);
        System.out.println("Finished dropping " + keyspace + " keyspace.");
    }


    public void close() {
        cluster.close();
    }

}
