package commands;

import collection.CollectionManager;
import connection.response.ResponseCreator;
import exceptions.EnumNotFoundException;
import exceptions.InvalidFieldException;
import general.AbstractCommand;
import subsidiary.TicketBuilder;

public class CountLessThanTypeCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final TicketBuilder ticketBuilder;
    private final ResponseCreator response;

    public CountLessThanTypeCommand(CollectionManager collectionManager, TicketBuilder ticketBuilder, ResponseCreator response) {
        super("count_less_than_type: ", "вывести количество элементов, значение поля type которых меньше заданного");
        this.collectionManager = collectionManager;
        this.ticketBuilder = ticketBuilder;
        this.response = response;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            response.addToMsg("Input argument");
            return;
        }
        try {
            collectionManager.countLessThanType(ticketBuilder.checkTicketType(args[1].trim()));
        } catch (EnumNotFoundException e) {
            response.addToMsg("There is not enum named " + args[1].trim());
        } catch (InvalidFieldException e) {
            response.addToMsg("TicketType Enum can't be null!");
        }
    }
}
