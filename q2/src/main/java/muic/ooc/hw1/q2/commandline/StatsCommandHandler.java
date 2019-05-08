package muic.ooc.hw1.q2.commandline;

import muic.ooc.hw1.q2.file.stats.printer.StatisticsPresenter;
import muic.ooc.hw1.q2.file.stats.printer.StatisticsPresenterFactory;
import org.apache.commons.cli.*;

import java.util.ArrayList;

public class StatsCommandHandler {

    public static void presentCommands(String[] inputs) {

        StatisticsPresenter[] statisticsPresenters = StatisticsPresenterFactory.generateOptionsArray();
        Options options = new Options();
        for (StatisticsPresenter sp : statisticsPresenters) {
            options.addOption(sp.getOption());
        }
        options.addOption("h", "help", false, "retrieve help message");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, inputs);

            if(cmd.hasOption("help")){
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("help", options);
            }

            ArrayList<StatisticsPresenter> presentStatPresenters = StatsCommandsParser.processCommands(cmd, statisticsPresenters);
            StatsCommandPrinter.printStatsCommand(presentStatPresenters);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
