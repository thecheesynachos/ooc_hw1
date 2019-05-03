package muic.ooc.hw1.q2.file.stats.printer;

public class StatisticsPresenterFactory {

    public static StatisticsPresenter[] generateOptions(){
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
