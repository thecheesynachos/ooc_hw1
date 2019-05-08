package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.Option;

public class GetTotalDirectories extends StatisticsPresenter {

    @Override
    protected Option createOption() {
        return new Option("b", "total-num-dirs", false, "total number of directories");
    }

    public String getResultString(FileStatisticsCollector fsc) {
        return "Total number of directories: " + fsc.getDirectoryCounter();
    }

}
