package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class GetExtensionCount implements StatisticsPresenter {

    private Option option = Option.builder().longOpt("num-ext").desc("total number of files with extension EXT").hasArg().argName("EXT").build();

    public String getResultString(FileStatisticsCollector fsc, CommandLine cmd) {
        return "Total number of directories: " + fsc.getDirectoryCounter();
    }

    public Option getOption() {
        return option;
    }

}
