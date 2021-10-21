package commands;

import collection.ServerTicket;
import exceptions.CommandIsNotExistException;
import general.AbstractCommand;
import general.Command;
import general.LimitedQueue;

import java.util.HashMap;

public interface CommandWorker {
    HashMap<String, AbstractCommand> getCommandMap();

    HashMap<String, ServerCommand> getServerCommandMap();

    LimitedQueue<String> getHistory();

    void executeCommand(String userCommand, ServerTicket serverTicket) throws CommandIsNotExistException;

    void executeServerCommand(String userCommand) throws CommandIsNotExistException;

    void addCommand(String commandName, AbstractCommand abstractCommand);

    void addServerCommand(String commandName, ServerCommand serverCommand);

    boolean isTicketCommand(Command command);

    AbstractCommand getCommandByName(String commandName);
}
