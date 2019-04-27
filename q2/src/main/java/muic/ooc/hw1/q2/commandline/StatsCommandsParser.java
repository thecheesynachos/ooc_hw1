package muic.ooc.hw1.q2.commandline;

import muic.ooc.hw1.q1.file.stats.FileStatisticsPresenter;
import org.apache.commons.cli.*;

public class StatsCommandsParser{

    private FileStatisticsPresenter fileStatisticsPresenter;

    private StatsCommandsParser(){
        FileStatisticsPresenter fileStatisticsPresenter = new FileStatisticsPresenter();
    }

    private void processCommands(CommandLine cmd){
        if(cmd.hasOption("f")){
            String startPath = cmd.getOptionValue("f");
            fileStatisticsPresenter = new FileStatisticsPresenter(startPath);
        }
        if(cmd.hasOption("a")) fileStatisticsPresenter.printTotalFiles();
        if(cmd.hasOption("b")) fileStatisticsPresenter.printTotalDirectories();
        if(cmd.hasOption("c")) fileStatisticsPresenter.printTotalExtensions();
        if(cmd.hasOption("d")) fileStatisticsPresenter.printUniqueExtensions();
        if(cmd.hasOption("num-ext")){
            String extension = cmd.getOptionValue("num-ext");
            fileStatisticsPresenter.printCountOfExtension(extension);
        }
    }

    public static void main(String[] args) {

        // add options to command
        // NEXT EDIT MOVE THIS OUT OF MAIN METHOD
        Options options = new Options();
        Option totalNumFiles = new Option("a", "total-num-files", false, "total number of files");
        Option totalNumDirs = new Option("b", "total-num-dirs", false, "total number of directories");
        Option totalUniqueExts = new Option("c", "total-unique-exts", false, "total number of unique directories");
        Option listExtensions = new Option("d", "list-exts", false, "list all unique extensions");
        Option numExt = Option.builder().longOpt("num-ext").desc("total number of files with extension EXT").hasArg().argName("EXT").build();
        Option pathToFolder = Option.builder("f").desc("specifies path to folder").hasArg().argName("path-to-folder").build();
        options.addOption(totalNumFiles);
        options.addOption(totalNumDirs);
        options.addOption(totalUniqueExts);
        options.addOption(listExtensions);
        options.addOption(numExt);
        options.addOption(pathToFolder);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        StatsCommandsParser statsCommandsParser = new StatsCommandsParser();

        try{
            cmd = parser.parse(options, args);
            statsCommandsParser.processCommands(cmd);
        } catch(Exception e){
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        }

    }

}
