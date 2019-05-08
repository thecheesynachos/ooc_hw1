package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.Option;

public class GetExtensionCount extends StatisticsPresenter {

    @Override
    protected Option createOption() {
        return Option.builder().longOpt("num-ext").desc("total number of files with extension EXT").hasArg().argName("EXT").build();

    }

    public String getResultString(FileStatisticsCollector fsc) {
        String extension = argument;
        int count = 0;
        if(fsc.getExtensionsCounter().containsKey(extension)) {
            count = fsc.getExtensionsCounter().get(extension); }
        return "Count of " + extension + " files: " + count;
    }

}
