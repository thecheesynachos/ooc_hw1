package muic.ooc.hw1.q2.commandline;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import muic.ooc.hw1.q2.file.stats.printer.StatisticsPresenter;

import java.util.ArrayList;

public class StatsCommandPrinter {

    public static void printStatsCommand(ArrayList<StatisticsPresenter> statisticsPresenters){

        FileStatisticsCollector fsc = new FileStatisticsCollector();

        for(StatisticsPresenter sp : statisticsPresenters){
            System.out.println(sp.getResultString(fsc));
            System.out.println();
        }

    }

}
