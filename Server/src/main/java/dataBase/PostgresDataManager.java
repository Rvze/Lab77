package dataBase;

import authorization.Auth;
import general.IdTicket;
import general.Ticket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public class PostgresDataManager implements DataManager {
    private final DataWriter dataWriter;
    private final DataReader dataReader;

    public PostgresDataManager(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        this.dataWriter = new DBWriter(connection);
        this.dataReader = new DBReader(connection);
    }


    @Override
    public Collection<IdTicket> readElements() {
        return dataReader.readElements();
    }

    @Override
    public IdTicket addElement(Ticket ticket, Auth auth) {
        dataWriter.addElement(ticket, auth);
        return dataReader.getLastElement();
    }

    @Override
    public IdTicket updateElement(Ticket ticket, int id, Auth auth) {
        dataWriter.updateElement(ticket, id, auth);
        return dataReader.getElement(id);
    }

    @Override
    public void clearElements(Auth auth) {
        dataWriter.clearElements(auth);
    }

    @Override
    public void removeElement(int id, Auth auth) {
        dataWriter.removeElement(id, auth);
    }

    @Override
    public void addUser(Auth auth) {
        dataWriter.addUser(auth);
    }

    @Override
    public Set<Auth> readUsers() {
        return dataReader.readUsers();
    }
}
