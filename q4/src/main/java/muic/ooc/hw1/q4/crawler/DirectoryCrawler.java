package muic.ooc.hw1.q4.crawler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
                InputStream inputStream = response.getEntity().getContent();

                File file = new File(targetFolder + currentDir);

                performTasks(inputStream, file, currentUrl, currentDir, dirsToVisit, dirsInQueue);
                System.out.println("Processed: " + currentDir);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpGet.releaseConnection();
            }

        }

        long endTime = System.currentTimeMillis();

        System.out.println();
        System.out.println("Done, time taken = " + (endTime - startTime)/1000 + " seconds");

    }


    private static void performTasks(InputStream inputStream, File outputFile, String currentUrl, String currentDir, LinkedList<String> dirsToVisit, Set<String> dirsInQueue) {
        if(FilenameUtils.isExtension(currentUrl, "html"))
            scanHtmlFile(inputStream, outputFile, currentDir, dirsToVisit, dirsInQueue);
        else saveFile(inputStream, outputFile);
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


    private static void scanHtmlFile(InputStream inputStream, File outputFile, String currentDir, LinkedList<String> dirsToVisit, Set<String> dirsInQueue){

        BufferedReader br;
        BufferedWriter bw;

        try {

            FileUtils.forceMkdirParent(outputFile);
            br = new BufferedReader(new InputStreamReader(inputStream));
            bw = new BufferedWriter(new FileWriter(outputFile));
            String line;

            while((line = br.readLine()) != null) {

                bw.write(line);

                Document doc = Jsoup.parse(line);

                Elements links = doc.select("[href],[src]");

                for (Element elt : links) {
                    String newDir;
                    if(elt.hasAttr("href")) newDir = elt.attr("href");
                    else newDir = elt.attr("src");
                    String newLink = connectUrl(currentDir, newDir);
                    addToQueue(newLink, newDir, dirsToVisit, dirsInQueue);
                }

            }

            bw.close();
            br.close();

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


}