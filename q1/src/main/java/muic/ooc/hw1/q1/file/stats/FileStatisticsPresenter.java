package muic.ooc.hw1.q1.file.stats;

public class FileStatisticsPresenter {

    private FileStatisticsCollector fileStatisticsCollector;

    public FileStatisticsPresenter(String startDirectory){
        fileStatisticsCollector = new FileStatisticsCollector(startDirectory);
    }

    public void printTotalDirectories(){
        System.out.print("Directory count: ");
        System.out.println(fileStatisticsCollector.getDirectoryCounter());
        System.out.println();
    }

    public void printTotalFiles(){
        System.out.print("Files count: ");
        System.out.println(fileStatisticsCollector.getFileCounter());
        System.out.println();
    }

    public void printTotalExtensions(){
        System.out.print("Unique extensions count: ");
        System.out.println(fileStatisticsCollector.getExtensionsCounter().size());
        System.out.println();
    }

    public void printUniqueExtensions(){
        System.out.println("List of unique file extensions");
        for(String extension: fileStatisticsCollector.getExtensionsCounter().keySet()){
            System.out.println(extension);
        }
        System.out.println();
    }

    public void printExtensionsTally(){
        System.out.println("Tally of unique file extensions");
        for(String extension: fileStatisticsCollector.getExtensionsCounter().keySet()){
            System.out.print(extension);
            System.out.print(": ");
            System.out.println(fileStatisticsCollector.getExtensionsCounter().get(extension));
        }
        System.out.println();
    }

    public void printCountOfExtension(String extension){
        extension = extension.toLowerCase();
        System.out.println("Count of ." + extension + " files: " + fileStatisticsCollector.getExtensionsCounter().get(extension));
        System.out.println();
    }

}
