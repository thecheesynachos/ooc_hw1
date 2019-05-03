package muic.ooc.hw1.q2.commandline;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import muic.ooc.hw1.q2.file.stats.printer.StatisticsPresenter;
import org.apache.commons.cli.*;

public class StatsCommandsParser{

    public static void processCommands(CommandLine cmd, StatisticsPresenter[] statisticsPresenters){
        FileStatisticsCollector fsc = new FileStatisticsCollector();
        for(StatisticsPresenter sp : statisticsPresenters){
            if(sp.getOption().getOpt() != null && cmd.hasOption(sp.getOption().getOpt())){
                System.out.println(sp.getResultString(fsc, cmd));
            } else if(sp.getOption().getLongOpt() != null && cmd.hasOption(sp.getOption().getLongOpt())){
                System.out.println(sp.getResultString(fsc, cmd));
            }
        }

    }

}
