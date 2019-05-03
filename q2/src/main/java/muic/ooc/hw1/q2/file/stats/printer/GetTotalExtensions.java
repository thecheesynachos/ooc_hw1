package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class GetTotalExtensions implements StatisticsPresenter {

    private Option option = new Option("c", "total-unique-exts", false, "total number of unique directories");

    public String getResultString(FileStatisticsCollector fsc, CommandLine cmd) {
        String extension = cmd.getOptionValue("num-ext").toLowerCase();
        int count = 0;
        if(fsc.getExtensionsCounter().containsKey(extension)) {
            count = fsc.getExtensionsCounter().get(extension); }
        return "Count of " + extension + " files: " + count;
    }

    public Option getOption() {
        return option;
    }

}
