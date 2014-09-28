package se.urlshortener.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by Alberto on 27/09/2014.
 */
public class WebApp {
    public static void main(String[]args) throws Exception{
        // The simple Jetty config here will serve static content from the webapp directory
        String webappDirLocation = "src/main/webapp/";


        // Look for that variable and default to 8080 if it isn't there.
        String webPort = "5000";
        Server server = new Server(Integer.valueOf(webPort));

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        webapp.setResourceBase(webappDirLocation);

        server.setHandler(webapp);
        server.start();
        server.join();
    }
}
