package muic.ooc.hw1.q2.commandline;

import muic.ooc.hw1.q2.file.stats.printer.StatisticsPresenter;
import org.apache.commons.cli.*;

import java.util.ArrayList;

public class StatsCommandsParser{

    public static ArrayList<StatisticsPresenter> processCommands(CommandLine cmd, StatisticsPresenter[] statisticsPresenters){

        ArrayList<StatisticsPresenter> presentStatPresenters = new ArrayList<StatisticsPresenter>();

        for(StatisticsPresenter sp : statisticsPresenters){

            String shortOpt = sp.getOption().getOpt();
            String longOpt = sp.getOption().getLongOpt();

            if(shortOpt != null && cmd.hasOption(shortOpt)){
                if(sp.getOption().hasArg()) sp.setArgument(cmd.getOptionValue(shortOpt));
                presentStatPresenters.add(sp);
            } else if(longOpt != null && cmd.hasOption(longOpt)){
                if(sp.getOption().hasArg()) sp.setArgument(cmd.getOptionValue(longOpt));
                presentStatPresenters.add(sp);
            }

        }

        return presentStatPresenters;

    }

}
