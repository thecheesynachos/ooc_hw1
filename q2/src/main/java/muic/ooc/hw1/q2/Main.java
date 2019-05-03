package muic.ooc.hw1.q2;

import muic.ooc.hw1.q2.file.stats.printer.StatisticsPresenter;
import muic.ooc.hw1.q2.file.stats.printer.StatisticsPresenterFactory;
import muic.ooc.hw1.q2.commandline.StatsCommandsParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class Main {

    public static void main(String[] args) {

        StatisticsPresenter[] statisticsPresenters = StatisticsPresenterFactory.generateOptions();
        Options options = new Options();
        for(StatisticsPresenter sp : statisticsPresenters) options.addOption(sp.getOption());

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try{
            cmd = parser.parse(options, args);
            StatsCommandsParser.processCommands(cmd, statisticsPresenters);
        } catch(Exception e){
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        }

    }

}
