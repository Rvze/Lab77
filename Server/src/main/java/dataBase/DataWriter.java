package dataBase;

import authorization.Auth;
import general.Ticket;

public interface DataWriter {
    void addElement(Ticket ticket, Auth auth);

    void updateElement(Ticket ticket, int id, Auth auth);

    void clearElements(Auth auth);

    void removeElement(int id, Auth auth);

    void addUser(Auth auth);
}
