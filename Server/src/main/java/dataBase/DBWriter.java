package dataBase;

import authorization.Auth;
import exceptions.DBException;
import exceptions.NoSuchIdException;
import general.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class DBWriter implements DataWriter {
    private final Connection connection;

    public DBWriter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addElement(Ticket ticket, Auth auth) {
        try (PreparedStatement stm = connection.prepareStatement(
                "insert into tickets (id, name, coordinate_x, coordinate_y, price, discount, refundable," +
                        "ticket_type, event_id, event_name, event_description, event_type, creationDate  , owner) values " +
                        "(nextval('id_seq'), ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?)")) {
            setTicketToStatement(ticket, stm);
            stm.setDate(13, Date.valueOf(LocalDate.now()));
            stm.setString(14, auth.getLogin());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }

    }

    @Override
    public void updateElement(Ticket ticket, int id, Auth auth) {
        try (PreparedStatement statement = connection.prepareStatement("update ticket set " +
                "name = ?" +
                "coordinate_x = ?" +
                "coordinate_y = ?" +
                "price = ?" +
                "discount = ?" +
                "refundable = ?" +
                "ticket_type = ?" +
                "event_id = ?" +
                "event_name = ?" +
                "event_description = ?" +
                "event_type = ? " +
                "where id = ? and owner = ?")) {
            setTicketToStatement(ticket, statement);
            statement.setInt(12, id);
            statement.setString(13, auth.getLogin());
            if (statement.executeUpdate() < 1) {
                throw new NoSuchIdException();
            }

        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void clearElements(Auth auth) {
        try (PreparedStatement statement = connection.prepareStatement("delete from tickets where owner = ?")) {
            statement.setString(1, auth.getLogin());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void removeElement(int id, Auth auth) {
        try (PreparedStatement statement = connection.prepareStatement("delete from tickets where id = ? and owner = ?")) {
            statement.setInt(1, id);
            statement.setString(2, auth.getLogin());
            if (statement.executeUpdate() < 1) {
                throw new NoSuchIdException();
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void addUser(Auth auth) {
        try (PreparedStatement statement = connection.prepareStatement("insert into users (username, password) values (?, ?)")) {
            statement.setString(1, auth.getLogin());
            statement.setString(2, auth.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private void setTicketToStatement(Ticket ticket, PreparedStatement statement) throws SQLException {
        statement.setString(1, ticket.getName());
        statement.setLong(2, ticket.getCoordinates().getX());
        statement.setInt(3, ticket.getCoordinates().getY());
        statement.setFloat(4, ticket.getPrice());
        statement.setLong(5, ticket.getDiscount());
        statement.setBoolean(6, ticket.isRefundable());
        statement.setString(7, ticket.getType().getUrl());
        statement.setString(8, ticket.getEvent().getName());
        statement.setString(9, ticket.getEvent().getDescription());
        statement.setString(10, ticket.getEvent().getEventType().getUrl());
        if (ticket.getEvent() == null) {
            statement.setNull(8, Types.VARCHAR);
            statement.setNull(9, Types.VARCHAR);
            statement.setNull(10, Types.VARCHAR);
        } else {
            statement.setString(8, ticket.getEvent().getName());
            statement.setString(9, ticket.getEvent().getDescription());
            statement.setString(10, ticket.getEvent().getEventType().getUrl());
        }
    }
}
