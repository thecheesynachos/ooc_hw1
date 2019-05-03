package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public interface StatisticsPresenter {

    public String getResultString(FileStatisticsCollector fsc, CommandLine cmd);

    public Option getOption();

}
