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

    public static void DownloadFilesFromUrl(String rootUrl, String targetFolder){

        System.out.println("Crawling from " + rootUrl);
        System.out.println("Saving to " + targetFolder);
        System.out.println();

        long startTime = System.currentTimeMillis();

        Counter counter = new Counter();

        LinkedList<String> dirsToVisit = new LinkedList<String>();
        Set<String> dirsInQueue = new HashSet<String>();
        HttpClient httpClient = HttpClientBuilder.create().build();

        dirsToVisit.add("index.html");

        while(!dirsToVisit.isEmpty()){

            String currentDir = dirsToVisit.poll();
            String currentUrl = rootUrl + currentDir;

            HttpGet httpGet = new HttpGet(currentUrl);

            try {

                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();

                File file = new File(targetFolder + currentDir);

                performTasks(inputStream, file, currentUrl, currentDir, dirsToVisit, dirsInQueue, counter);
                System.out.println("Processed: " + currentDir);

                EntityUtils.consume(entity);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpGet.releaseConnection();
            }

        }

        long endTime = System.currentTimeMillis();

        System.out.println();
        System.out.println("Done, time taken = " + (endTime - startTime)/1000 + " seconds");
        System.out.println("Total word count = " + counter.getCount());

    }


    private static void performTasks(InputStream inputStream, File outputFile, String currentUrl, String currentDir,
                                     LinkedList<String> dirsToVisit, Set<String> dirsInQueue, Counter counter) {
        saveFile(inputStream, outputFile);
        if(FilenameUtils.isExtension(currentUrl, "html"))
            scanHtmlFile(outputFile, currentDir, dirsToVisit, dirsInQueue, counter);
    }


    private static void saveFile(InputStream inputStream, File outputFile) {
        try {
            if(!outputFile.isDirectory()) {
                FileUtils.forceMkdirParent(outputFile);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));
                IOUtils.copy(inputStream, bos);
                bos.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private static void scanHtmlFile(File outputFile, String currentDir, LinkedList<String> dirsToVisit,
                                     Set<String> dirsInQueue, Counter counter){

        try {

            Document doc = Jsoup.parse(outputFile, "UTF-8");

            Elements links = doc.select("[href],[src]");

            for (Element elt : links) {
                String newDir;
                if(elt.hasAttr("href")) newDir = elt.attr("href");
                else newDir = elt.attr("src");
                String newLink = connectUrl(currentDir, newDir);
                addToQueue(newLink, newDir, dirsToVisit, dirsInQueue);

            }

            countWords(doc, counter);

        } catch (Exception e){
            e.printStackTrace();
        }


    }


    private static void addToQueue(String newLink, String newDir, LinkedList<String> dirsToVisit, Set<String> dirsInQueue){
        if (!newDir.startsWith("http") && !dirsInQueue.contains(newLink)) {
            dirsToVisit.add(newLink);
            dirsInQueue.add(newLink);
        }
    }


    private static void countWords(Document doc, Counter counter){

        String cleanedString = Jsoup.clean(doc.toString(), Whitelist.none());
        String[] words = cleanedString.split("\\s+");
        counter.addCount(words.length);

    }


}


class Counter{

    long count;

    Counter(){
        count = 0;
    }

    void addCount(long num){
        count += num;
    }

    long getCount(){
        return count;
    }

}