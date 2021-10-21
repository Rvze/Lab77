package commands;

import collection.ServerTicket;
import exceptions.CommandIsNotExistException;
import general.AbstractCommand;
import general.Command;
import general.LimitedQueue;
import general.TicketCommand;

import java.util.HashMap;

public class CommandWorkerImpl implements CommandWorker {
    private final HashMap<String, AbstractCommand> commandMap;
    private final HashMap<String, ServerCommand> serverCommandMap;
    private final LimitedQueue<String> history;

    public CommandWorkerImpl() {
        commandMap = new HashMap<>();
        serverCommandMap = new HashMap<>();
        history = new LimitedQueue<>(11);
    }

    @Override
    public LimitedQueue<String> getHistory() {
        return history;
    }

    @Override
    public void executeCommand(String userCommand, ServerTicket ticket) throws CommandIsNotExistException {
        if (userCommand == null) {
            throw new CommandIsNotExistException("This command doesn't exist");
        }
        String[] updatedUserCommand = userCommand.toLowerCase().trim().split("\\s", 2);
        updatedUserCommand[0] = updatedUserCommand[0].trim().toLowerCase();
        Command command = commandMap.get(updatedUserCommand[0]);
        if (command != null) {
            history.add(updatedUserCommand[0]);
            if (!command.isTicketCommand()) {
                command.execute(updatedUserCommand);
            } else {
                ((TicketCommand) command).execute(updatedUserCommand, ticket);
            }

        } else if (!updatedUserCommand[0].equals("")) {
            throw new CommandIsNotExistException("This command doesn't exist");
        }
    }

    @Override
    public void executeServerCommand(String userCommand) throws CommandIsNotExistException {
        if (userCommand == null) {
            throw new CommandIsNotExistException("This server command doesn't exist");
        }
        userCommand = userCommand.toLowerCase().trim();
        ServerCommand command = serverCommandMap.get(userCommand);
        if (command != null) {
            command.execute();
        } else
            throw new CommandIsNotExistException("This server command doesn't exist");
    }

    @Override
    public void addCommand(String commandName, AbstractCommand command) {
        commandMap.put(commandName, command);
    }

    @Override
    public void addServerCommand(String commandName, ServerCommand command) {
        serverCommandMap.put(commandName, command);
    }

    @Override
    public boolean isTicketCommand(Command command) {
        return command.isTicketCommand();
    }

    @Override
    public HashMap<String, AbstractCommand> getCommandMap() {
        return commandMap;
    }

    @Override
    public HashMap<String, ServerCommand> getServerCommandMap() {
        return serverCommandMap;
    }

    @Override
    public AbstractCommand getCommandByName(String commandName) {
        return commandMap.get(commandName);
    }
}
