package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.Option;

public class SetRootFolder extends StatisticsPresenter {

    @Override
    protected Option createOption() {
        return Option.builder("f").desc("specifies path to folder").hasArg().argName("path-to-folder").build();
    }

    public String getResultString(FileStatisticsCollector fsc) {
        String startPath = argument;
        fsc = new FileStatisticsCollector(startPath);
        return "Looking in directory " + startPath;
    }

}
