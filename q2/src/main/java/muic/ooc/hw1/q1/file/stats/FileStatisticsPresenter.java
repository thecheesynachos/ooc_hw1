package muic.ooc.hw1.q1.file.stats;

public class FileStatisticsPresenter {

    FileStatisticsCollector fileStatisticsCollector;

    public FileStatisticsPresenter(String startDirectory){
        fileStatisticsCollector = new FileStatisticsCollector(startDirectory);
    }

    public FileStatisticsPresenter(){
    }

    public void printTotalDirectories(){
        System.out.print("Directory count: ");
        System.out.println(fileStatisticsCollector.getDirectoryCounter());
    }

    public void printTotalFiles(){
        System.out.print("Files count: ");
        System.out.println(fileStatisticsCollector.getFileCounter());
    }

    public void printTotalExtensions(){
        System.out.print("Unique extensions count: ");
        System.out.println(fileStatisticsCollector.getExtensionsCounter().size());
    }

    public void printUniqueExtensions(){
        System.out.println("List of unique file extensions:");
        for(String extension: fileStatisticsCollector.getExtensionsCounter().keySet()){
            System.out.println(extension);
        }
    }

    public void printCountOfExtension(String extension){
        extension = extension.toLowerCase();
        int count = 0;
        if(fileStatisticsCollector.getExtensionsCounter().containsKey(extension)) {
            count = fileStatisticsCollector.getExtensionsCounter().get(extension); }
        System.out.println("Count of ." + extension + " files: " + count);
    }

}
