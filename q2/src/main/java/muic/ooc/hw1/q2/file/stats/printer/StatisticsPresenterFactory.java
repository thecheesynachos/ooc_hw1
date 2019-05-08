package muic.ooc.hw1.q2.file.stats.printer;

import org.apache.commons.cli.Options;

public class StatisticsPresenterFactory {

    public static StatisticsPresenter[] generateOptionsArray(){
        return new StatisticsPresenter[]{
                new SetRootFolder(),
                new GetTotalFiles(),
                new GetTotalDirectories(),
                new ListExtensions(),
                new GetExtensionCount(),
                new GetTotalExtensions()
        };
    }

}
