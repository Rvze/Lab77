package collection;

import exceptions.IdExistException;
import general.Ticket;

import java.util.HashSet;

public abstract class AbstractCollectionManager implements CollectionManager {
    private static HashSet<Long> idSet;
    protected HashSet<ServerTicket> tickets;

    protected static void initIdSet() {
        idSet = new HashSet<>();
    }

    protected static void addId(long id) throws IdExistException {
        if (idSet.contains(id)) {
            throw new IdExistException();
        } else
            idSet.add(id);
    }

    protected static void removeId(long id) {
        idSet.remove(id);
    }

    protected final Long getId() {
        long id;
        while (idSet.contains(id = (long) (Math.random() * Integer.MAX_VALUE + 1))) ;
        return id;
    }

    public final ServerTicket getServerTicket(Ticket ticket) throws IdExistException {
        long id = getId();
        addId(id);
        return new ServerTicket(ticket, id);
    }
}
