package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class GetTotalFiles implements StatisticsPresenter {

    private Option option = new Option("a", "total-num-files", false, "total number of files");

    public String getResultString(FileStatisticsCollector fsc, CommandLine cmd) {
        return "Total number of files: " + fsc.getFileCounter();
    }

    public Option getOption() {
        return option;
    }

}
