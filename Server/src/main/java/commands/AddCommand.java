package commands;

import collection.CollectionManager;
import collection.ServerTicket;
import exceptions.InvalidCommandType;
import general.AbstractCommand;
import general.TicketCommand;

public class AddCommand extends AbstractCommand implements TicketCommand {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add ", "{element} : добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        throw new InvalidCommandType("add command required Ticket instance");
    }

    @Override
    public void execute(String[] args, ServerTicket ticket) {
        collectionManager.addElement(ticket);
    }

    @Override
    public boolean isTicketCommand() {
        return true;
    }
}
