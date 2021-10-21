package commands;

import collection.CollectionManager;
import collection.ServerTicket;
import exceptions.InvalidCommandType;
import general.AbstractCommand;
import general.TicketCommand;
import subsidiary.TicketBuilder;

import java.util.NoSuchElementException;

public class AddIfMaxCommand extends AbstractCommand implements TicketCommand {
    private final CollectionManager collectionManager;
    private final TicketBuilder ticketBuilder;

    public AddIfMaxCommand(CollectionManager collectionManager, TicketBuilder ticketBuilder) {
        super("add if max ", "{element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.ticketBuilder = ticketBuilder;
    }

    @Override
    public void execute(String[] args) {
        throw new InvalidCommandType("add if max command required Ticket command");
    }

    @Override
    public void execute(String[] args, ServerTicket ticket) {
        try {
            ticketBuilder.askTicket();

            ticket = (ServerTicket) ticketBuilder.askTicket();
            if (ticket.compareByTicket(collectionManager.getTicketStream().max(ServerTicket.getTicketComparator()).get()) > 0) {
                collectionManager.addElement(ticket);
            }
        } catch (NoSuchElementException e) {
            System.err.println("^D is forbidden input");
        }
    }

    @Override
    public boolean isTicketCommand() {
        return true;
    }
}
