package commands;

import collection.CollectionManager;
import collection.ServerTicket;
import connection.ServerConnectionManager;
import general.AbstractCommand;

public class ExitCommand extends AbstractCommand {
    private final ServerConnectionManager serverManager;
    private final
    private final ServerTicket serverTicket;

    public ExitCommand()

    @Override
    public void execute(String[] args) {

    }
}
