package muic.ooc.hw1.q1.file.stats;

import org.apache.commons.io.DirectoryWalker;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileStatisticsCollector extends DirectoryWalker {

    private int directoryCounter;
    private int fileCounter;
    private Hashtable<String, Integer> extensionsCounter;
    private File startDirectory;

    public FileStatisticsCollector(String pathname) {
        super();
        directoryCounter = 0;
        fileCounter = 0;
        extensionsCounter = new Hashtable<String, Integer>();
        startDirectory = new File(pathname);
        this.gatherFiles();
    }

    private List gatherFiles(){
        List<File> results = new ArrayList<File>();
        try {
            walk(startDirectory, results);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    protected void handleDirectoryStart(File directory, int depth, Collection results){
        // every time a directory is found, we add one to directory counter
        directoryCounter++;
    }

    @Override
    protected void handleFile(File file, int depth, Collection results) throws IOException {
        results.add(file);
        String fileName = file.getName();
        // if statement to exclude files without extensions in count
        String extension;
        if(fileName.contains(".")) {
            // get the extension out from the file, and make it case insensitive
            // +1 to exclude the period
            extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        } else{
            extension = "<no extension>";
        }
        if (extensionsCounter.containsKey(extension)) {
            extensionsCounter.put(extension, extensionsCounter.get(extension) + 1);
        } else {
            extensionsCounter.put(extension, 1);
        }
        fileCounter++;
    }

    public int getDirectoryCounter() {
        return directoryCounter;
    }

    public int getFileCounter() {
        return fileCounter;
    }

    public Hashtable<String, Integer> getExtensionsCounter() {
        return extensionsCounter;
    }

}
