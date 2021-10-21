package dataBase;

import authorization.Auth;
import exceptions.DBException;
import exceptions.InvalidFieldException;
import exceptions.NoSuchIdException;
import general.EventType;
import general.IdTicket;
import general.Ticket;
import general.TicketType;
import subsidiary.TicketBuilder;
import subsidiary.TicketBuilderImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class DBReader implements DataReader {
    private final Connection connection;

    public DBReader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<IdTicket> readElements() {
        Collection<IdTicket> tickets = new HashSet<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery("select * from tickets");
            while (res.next()) {
                tickets.add(createTicket(res));
            }
        } catch (SQLException | InvalidFieldException e) {
            throw new DBException(e);
        }
        return tickets;
    }

    @Override
    public IdTicket getElement(int id) {
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery("select * from tickets where id = " + id);
            if (res.next()) {
                return createTicket(res);
            } else
                throw new NoSuchIdException();
        } catch (SQLException | InvalidFieldException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<Auth> readUsers() {
        Set<Auth> users = new HashSet<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery("select * from users");
            while (res.next()) {
                users.add(new Auth(res.getString("username"), res.getString("password")));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return users;
    }

    @Override
    public IdTicket getLastElement() {
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery("select * from tickets where id = (select max(id) from tickets)");
            if (res.next()) {
                return createTicket(res);
            } else
                return null;
        } catch (SQLException | InvalidFieldException e) {
            throw new DBException(e);
        }
    }

    private IdTicket createTicket(ResultSet res) throws SQLException, InvalidFieldException {
        TicketBuilder ticketBuilder = new TicketBuilderImpl();
        ticketBuilder.setName("name");
        ticketBuilder.setCoordinateX(res.getLong("coordinate_x"));
        ticketBuilder.setCoordinateY(res.getInt("coordinate_y"));
        ticketBuilder.setPrice(res.getFloat("price"));
        ticketBuilder.setDiscount(res.getLong("discount"));
        ticketBuilder.setRefundable(res.getBoolean("refundable"));
        ticketBuilder.setTicketType(TicketType.valueOf(res.getString("ticket_type")));
        ticketBuilder.setEventName(res.getString("event_name"));
        ticketBuilder.setEventDescription(res.getString("event_description"));
        ticketBuilder.setEventType(EventType.valueOf(res.getString("event_type")));
        return new IdTicket(res.getInt("id"), ticketBuilder.getTicket(),
                res.getDate("creationDate").toLocalDate().atStartOfDay());
    }
}
