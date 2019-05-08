package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class ListExtensions extends StatisticsPresenter {

    @Override
    protected Option createOption() {
        return new Option("d", "list-exts", false, "list all unique extensions");
    }

    public String getResultString(FileStatisticsCollector fsc) {
        StringBuilder sb = new StringBuilder().append("Unique extensions: \n");
        for(String ext : fsc.getExtensionsCounter().keySet()){
            sb.append(ext).append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

}
