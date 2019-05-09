package muic.ooc.hw1.q4.crawler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static muic.ooc.hw1.q4.crawler.DirectoryStringFixer.*;


public class DirectoryCrawler{

    private long counter = 0;
    private LinkedList<String> dirsToVisit = new LinkedList<String>();
    private Set<String> dirsInQueue = new HashSet<String>();
    private String rootUrl;
    private String targetFolder;
    private HttpClient httpClient;

    public DirectoryCrawler(String rootUrl, String targetFolder){
        this.rootUrl = rootUrl;
        this.targetFolder = targetFolder;
    }


    public void DownloadFilesFromUrl(){

        System.out.println("Crawling from " + rootUrl);
        System.out.println("Saving to " + targetFolder);
        System.out.println();

        long startTime = System.currentTimeMillis();

        httpClient = HttpClientBuilder.create().build();

        dirsToVisit.add("index.html");

        while(!dirsToVisit.isEmpty()){

            String currentDir = dirsToVisit.poll();
            String currentUrl = rootUrl + currentDir;

            HttpGet httpGet = new HttpGet(currentUrl);

            try {

                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();

                File file = new File(targetFolder + currentDir);

                performTasks(entity, file, currentUrl, currentDir);
                System.out.println("Processed: " + currentDir);

                EntityUtils.consume(entity);

            } catch (Exception e) {
                e.printStackTrace();
//            } finally {
//                httpGet.releaseConnection();
            }

        }

        long endTime = System.currentTimeMillis();

        System.out.println();
        System.out.println("Done, time taken = " + (endTime - startTime)/1000 + " seconds");
        System.out.println("Total word count = " + counter);

    }


    private void performTasks(HttpEntity entity, File outputFile, String currentUrl, String currentDir) {
        saveFile(entity, outputFile);
        if(FilenameUtils.isExtension(currentUrl, "html"))
            scanHtmlFile(outputFile, currentDir);
    }


    private void saveFile(HttpEntity entity, File outputFile) {
        try {
            if(!outputFile.isDirectory()) {
                FileUtils.forceMkdirParent(outputFile);
                entity.writeTo(new FileOutputStream(outputFile));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private void scanHtmlFile(File outputFile, String currentDir){

        try {

            Document doc = Jsoup.parse(outputFile, "UTF-8");

            Elements links = doc.select("[href],[src]");

            for (Element elt : links) {
                String newDir;
                if(elt.hasAttr("href")) newDir = elt.attr("href");
                else newDir = elt.attr("src");
                String newLink = connectUrl(currentDir, newDir);
                if (!newDir.startsWith("http") && !dirsInQueue.contains(newLink)) {
                    dirsToVisit.add(newLink);
                    dirsInQueue.add(newLink);
                }

            }

            countWords(doc);

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    private void countWords(Document doc){

        String cleanedString = Jsoup.clean(doc.toString(), Whitelist.none());
        String[] words = cleanedString.split("\\s+");
        counter += words.length;

    }


}
