package muic.ooc.hw1.q2.file.stats.printer;

import muic.ooc.hw1.q2.file.stats.FileStatisticsCollector;
import org.apache.commons.cli.Option;

public abstract class StatisticsPresenter {

    protected Option option;
    protected String argument;

    public StatisticsPresenter(){
        option = createOption();
    }

    public abstract String getResultString(FileStatisticsCollector fsc);

    public Option getOption(){
        return option;
    }

    protected abstract Option createOption();

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

}
