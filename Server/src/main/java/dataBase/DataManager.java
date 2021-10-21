package dataBase;

import authorization.Auth;
import general.IdTicket;
import general.Ticket;

import java.util.Collection;
import java.util.Set;

public interface DataManager {
    Collection<IdTicket> readElements();

    IdTicket addElement(Ticket ticket, Auth auth);

    IdTicket updateElement(Ticket ticket, int id, Auth auth);

    void clearElements(Auth auth);

    void removeElement(int id, Auth auth);

    void addUser(Auth auth);

    Set<Auth> readUsers();

}
