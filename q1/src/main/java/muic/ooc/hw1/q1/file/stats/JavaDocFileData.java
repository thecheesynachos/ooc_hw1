package muic.ooc.hw1.q1.file.stats;

public class JavaDocFileData {

    public static void main(String[] args) {

        // Testing the class on java doc file
        FileStatisticsPresenter fileStatisticsPresenter = new FileStatisticsPresenter("/Volumes/DOCUMENTS/CS Stuff/OOC/hw1_outputs/docs-crawled");

        fileStatisticsPresenter.printTotalDirectories();
        fileStatisticsPresenter.printTotalFiles();
        fileStatisticsPresenter.printTotalExtensions();
        fileStatisticsPresenter.printExtensionsTally();

    }

}
