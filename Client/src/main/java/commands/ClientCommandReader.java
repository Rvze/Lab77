package commands;

import exceptions.CommandIsNotExistException;
import general.Ticket;

public interface ClientCommandReader {
    void executeCommand(String userCommand, Ticket ticket) throws CommandIsNotExistException;
}
