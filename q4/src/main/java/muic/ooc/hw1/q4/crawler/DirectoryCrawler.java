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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static muic.ooc.hw1.q4.crawler.DirectoryStringFixer.*;


public class DirectoryCrawler{

//    final static String TAGS_FILTER = "(?:href=\"|src=\")(.+?)(?:\")";


    public static void DownloadFilesFromUrl(String rootUrl, String targetFolder){

        System.out.println("Crawling from " + rootUrl);
        System.out.println("Saving to " + targetFolder);
        System.out.println();

        long startTime = System.currentTimeMillis();

        LinkedList<String> dirsToVisit = new LinkedList<String>();
        Set<String> dirsInQueue = new HashSet<String>();
        HttpClient httpClient = HttpClientBuilder.create().build();

        dirsToVisit.add("index.html");
        int counter = 0;

        while(!dirsToVisit.isEmpty()){

            String currentDir = dirsToVisit.poll();
            String currentUrl = rootUrl + currentDir;

            HttpGet httpGet = new HttpGet(currentUrl);

            try {

                HttpResponse response = httpClient.execute(httpGet);
                InputStream inputStream = response.getEntity().getContent();

//                System.out.print("Saving file: " + currentDir);
                File file = new File(targetFolder + currentDir);

                performTasks(inputStream, file, currentUrl, currentDir, dirsToVisit, dirsInQueue);

//                System.out.println(" done");
//                counter++;
//                System.out.println("Files saved: " + counter);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpGet.releaseConnection();
            }

        }

        long endTime = System.currentTimeMillis();

        System.out.println();
        System.out.println("Done, time taken = " + (endTime - startTime)/1000 + " seconds");
        System.out.println("Number of files: " + counter);

    }


    private static void performTasks(InputStream inputStream, File outputFile, String currentUrl, String currentDir, LinkedList<String> dirsToVisit, Set<String> dirsInQueue) {
        saveFile(inputStream, outputFile);
        if(FilenameUtils.isExtension(currentUrl, "html"))
            scanHtmlFile(inputStream, outputFile, currentDir, dirsToVisit, dirsInQueue);
    }


    private static void saveFile(InputStream inputStream, File outputFile) {
        try {
            FileUtils.forceMkdirParent(outputFile);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));
            IOUtils.copy(inputStream, bos);
            bos.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private static void scanHtmlFile(InputStream inputStream, File outputFile, String currentDir, LinkedList<String> dirsToVisit, Set<String> dirsInQueue){

//        BufferedReader br;
//        BufferedWriter bw;
//
//        try {
//
//            FileUtils.forceMkdirParent(outputFile);
//            br = new BufferedReader(new InputStreamReader(inputStream));
//            bw = new BufferedWriter(new FileWriter(outputFile));
//            String line;
//
//            while((line = br.readLine()) != null) {
//
//                bw.write(line);
//
//                Document doc = Jsoup.parse(line);
//
//                Elements hrefLinks = doc.select("[href]");
//                Elements srcLinks = doc.select("[src]");
//
//                for (Element elt : hrefLinks) {
//                    String newDir = elt.attr("href");
//                    String newLink = connectUrl(currentDir, newDir);
//                    addToQueue(newLink, newDir, dirsToVisit, dirsInQueue);
//                }
//
//                for (Element elt : srcLinks) {
//                    String newDir = elt.attr("src");
//                    String newLink = connectUrl(currentDir, newDir);
//                    addToQueue(newLink, newDir, dirsToVisit, dirsInQueue);
//                }
//
//            }
//
//            bw.close();
//            br.close();
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }

//        BufferedReader br;
//        BufferedWriter bw;
//
//        try {
//
//            FileUtils.forceMkdirParent(outputFile);
//            br = new BufferedReader(new InputStreamReader(inputStream));
//            bw = new BufferedWriter(new FileWriter(outputFile));
//            String line;
//
//            while((line = br.readLine()) != null) {
//
//                bw.write(line);
//
//                Matcher matcher = Pattern.compile(TAGS_FILTER).matcher(line);
//
//                while(matcher.find()) {
//                    String newDir = matcher.group(1);
//                    String newLink = connectUrl(currentDir, newDir);
//                    addToQueue(newLink, newDir, dirsToVisit, dirsInQueue);
//                }
//
//            }
//
//            bw.close();
//            br.close();
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        try {

            Document doc = Jsoup.parse(outputFile, "UTF-8");

            Elements hrefLinks = doc.select("[href]");
            Elements srcLinks = doc.select("[src]");

            for (Element elt : hrefLinks) {
                String newDir = elt.attr("href");
                String newLink = connectUrl(currentDir, newDir);
                addToQueue(newLink, newDir, dirsToVisit, dirsInQueue);
            }

            for (Element elt : srcLinks) {
                String newDir = elt.attr("src");
                String newLink = connectUrl(currentDir, newDir);
                addToQueue(newLink, newDir, dirsToVisit, dirsInQueue);
            }

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