package dataBase;

import authorization.Auth;
import general.IdTicket;
import general.Ticket;

import java.util.Collection;
import java.util.Set;

public interface DataReader {
    Collection<IdTicket> readElements();

    IdTicket getElement(int id);

    Set<Auth> readUsers();

    IdTicket getLastElement();
}
