package collection;

import exceptions.IdExistException;
import general.Event;
import general.Ticket;
import general.TicketType;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.stream.Stream;

public interface CollectionManager {
    void info();

    void addElement(ServerTicket ticket);


    Stream<ServerTicket> getTicketStream();

    void countLessThanType(TicketType ticketType);

    void filterGreaterThanEvent(Event event);

    void printFieldDescendingPrice();

    HashSet<Long> getIdList();

    void removeLower(Ticket ticket);

    boolean update(long id, ServerTicket tickets);

    void removeById(long id);

    void clear();

    boolean checkId(long id);

    ZonedDateTime getInitializationTime();

    boolean containsId(long id);

    ServerTicket getServerTicket(Ticket ticket) throws IdExistException;

    HashSet<ServerTicket> getTickets();
}
