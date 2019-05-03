package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class SetRootFolder implements StatisticsPresenter {

    private Option option = Option.builder("f").desc("specifies path to folder").hasArg().argName("path-to-folder").build();

    public String getResultString(FileStatisticsCollector fsc, CommandLine cmd) {
        String startPath = cmd.getOptionValue("f");
        fsc = new FileStatisticsCollector(startPath);
        return "Looking in directory " + startPath;
    }

    public Option getOption() {
        return option;
    }

}
