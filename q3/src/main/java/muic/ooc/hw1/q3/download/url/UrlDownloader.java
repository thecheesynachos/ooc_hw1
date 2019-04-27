package muic.ooc.hw1.q3.download.url;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class UrlDownloader {

    public static void downloadUrlMethod1(String urlLink, String targetFile) {

        try {
            URL url = new URL(urlLink);
            // writes to file using built-in function copyUrlToFile
            FileUtils.copyURLToFile(url, new File(targetFile));
        } catch(Exception e){
            System.err.println(e.getMessage());
        }

    }

    public static void downloadUrlMethod2(String urlLink, String targetFile){

        BufferedWriter writer = null;
        BufferedReader reader = null;

        try {
            // create BufferedWriter to file in specified location
            writer = new BufferedWriter(new FileWriter(targetFile));
            // create connection to url and BufferedReader from that location
            URLConnection urlConnection = new URL(urlLink).openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            // keep writing lines from the url to the file until runs out
            String inputLine;
            while ((inputLine = reader.readLine()) != null) writer.write(inputLine);
        } catch(Exception e){
            System.err.println(e.getMessage());
        } finally {
            try {
                if (writer != null) writer.close();
                if (reader != null) reader.close();
            } catch(Exception e){
                System.err.println(e.getMessage());
            }
        }

    }

    public static void downloadUrlMethod3(String urlLink, String targetFile){

        try {
            // creates a client
            HttpClient httpClient = HttpClientBuilder.create().build();
            // creates get request and gets response from the link
            HttpResponse response = httpClient.execute(new HttpGet(urlLink));
            HttpEntity entity = response.getEntity();
            InputStream inputStream = response.getEntity().getContent();
            if (entity != null) {
                // writes the content from the entity into a file
                File file = new File(targetFile);
                FileUtils.forceMkdirParent(file);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                IOUtils.copy(inputStream, bos);
                bos.close();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        String tocUrl = "https://cs.muic.mahidol.ac.th/courses/ooc/docs/index.html";
        UrlDownloader.downloadUrlMethod1(tocUrl, "/Volumes/DOCUMENTS/CS Stuff/OOC/hw1/test_folder/test1.html");
        UrlDownloader.downloadUrlMethod2(tocUrl, "/Volumes/DOCUMENTS/CS Stuff/OOC/hw1/test_folder/test2.html");
        UrlDownloader.downloadUrlMethod3(tocUrl, "/Volumes/DOCUMENTS/CS Stuff/OOC/hw1/test_folder/test3.html");

        // the last one works on any files
        UrlDownloader.downloadUrlMethod3("https://cs.muic.mahidol.ac.th/courses/ooc/docs/images/javalogo.gif",
                "/Volumes/DOCUMENTS/CS Stuff/OOC/hw1/test_folder/test_img.gif");

    }

}
