package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class ListExtensions implements StatisticsPresenter {

    private Option option = new Option("d", "list-exts", false, "list all unique extensions");

    public String getResultString(FileStatisticsCollector fsc, CommandLine cmd) {
        StringBuilder sb = new StringBuilder().append("Unique extensions: \n");
        for(String ext : fsc.getExtensionsCounter().keySet()){
            sb.append(ext).append("\n");
        }
        return sb.toString();
    }

    public Option getOption() {
        return option;
    }

}
