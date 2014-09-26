package se.urlshortener.util;

import org.apache.commons.codec.binary.Base64;
import se.urlshortener.representation.Url;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Alberto on 25/09/2014.
 */
public class UrlShortenerUtil {

    public static Url encodeURL(String URL,String hostname) {
        String encodedURL ="";
        try {
            byte[] bytesOfMessage = URL.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digestedBytes = messageDigest.digest(bytesOfMessage);

            byte[] MD5LowerBytes = getLowerBytes(digestedBytes);
            byte[] encoded = Base64.encodeBase64(MD5LowerBytes);
            encodedURL = new String(encoded,"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new Url(hostname+encodedURL);
    }

    private static byte[] getLowerBytes(byte[] digest){
        byte[] fetchedBytes = new byte[4];

        int j=0;
        for(int i=digest.length-5;i<digest.length-1;i++){
            fetchedBytes[j]= digest[i];
            j++;
        }
        return fetchedBytes;
    }
}
