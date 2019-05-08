package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.Option;

public class GetTotalExtensions extends StatisticsPresenter {

    @Override
    protected Option createOption() {
        return new Option("c", "total-unique-exts", false, "total number of unique directories");
    }

    public String getResultString(FileStatisticsCollector fsc) {
        return "Total number of unique extensions: " + fsc.getExtensionsCounter().size();
    }

}
