package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class GetTotalDirectories implements StatisticsPresenter {

    private Option option = new Option("b", "total-num-dirs", false, "total number of directories");

    public String getResultString(FileStatisticsCollector fsc, CommandLine cmd) {
        return "Total number of directories: " + fsc.getDirectoryCounter();
    }

    public Option getOption() {
        return option;
    }

}
