package collection;

import connection.response.ResponseCreator;
import general.Event;
import general.Ticket;
import general.TicketType;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionManagerImpl extends AbstractCollectionManager {
    private final ZonedDateTime creationDate;
    private final ResponseCreator responseCreator;


    public CollectionManagerImpl(ResponseCreator responseCreator) {
        tickets = new HashSet<>();
        creationDate = ZonedDateTime.now();
        this.responseCreator = responseCreator;
        initIdSet();

    }

    @Override
    public void info() {
        String info = "Initialization time: " + getInitializationTime() + "\n" +
                "Count of array elements: " + tickets.size() + "\n" +
                "Type of collection: " + tickets.getClass().getSimpleName();
        responseCreator.addToMsg(info);
    }

    @Override
    public void addElement(ServerTicket ticket) {
        tickets.add(ticket);
        System.out.println("Element is added successfully");
    }


    private Comparator<ServerTicket> getComparatorById() {
        return (ServerTicket a, ServerTicket b) -> (int) (a.getId() - b.getId());
    }

    public void sort() {
        tickets = tickets.stream()
                .sorted(getComparatorById())
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Stream<ServerTicket> getTicketStream() {
        return tickets.stream();
    }

    @Override
    public void countLessThanType(TicketType ticketType) {
        int count = (int) tickets.stream()
                .filter(ticket -> ticket.getType().getValue() > ticketType.getValue())
                .count();
        responseCreator.addToMsg("Count of element is: " + count);
    }

    @Override
    public void filterGreaterThanEvent(Event event) {
        int cnt = 0;
        for (ServerTicket ticket : tickets) {
            if (ticket.getEvent().getId() < event.getId()) {
                cnt++;
                responseCreator.addToMsg(TicketShower.toStrView(ticket));
            }
        }
        responseCreator.addToMsg("Finally found " + cnt + "elements!");
    }

    @Override
    public void printFieldDescendingPrice() {
        Vector<ServerTicket> temp = tickets.stream()
                .collect(Collectors.toCollection(Vector::new));
        Collections.sort(temp);
        Collections.reverse(temp);
        for (ServerTicket tick : temp) {
            responseCreator.addToMsg(TicketShower.toStrView(tick));
        }
    }

    @Override
    public HashSet<Long> getIdList() {
        return null;
    }

    @Override
    public void removeLower(Ticket ticket) {
        int count = tickets.size();
        tickets.stream()
                .filter(ticket1 -> ticket1.getId() < ticket.getId())
                .forEach(ticket1 -> removeId(ticket1.getId()));

        tickets = tickets.stream()
                .filter(ticket1 -> ticket1.getId() >= ticket.getId())
                .collect(Collectors.toCollection(HashSet::new));
        responseCreator.addToMsg(count - tickets.size() + " elements removed");
    }

    @Override
    public boolean update(long id, ServerTicket ticket) {
        for (ServerTicket st : tickets) {
            if (st.getId() == id) {
                st.setName(ticket.getName());
                st.setCoordinates(ticket.getCoordinates());
                st.setPrice(ticket.getPrice());
                st.setDiscount(ticket.getDiscount());
                st.setRefundable(ticket.isRefundable());
                st.setType(ticket.getType());
                st.setEvent(ticket.getEvent());
                responseCreator.addToMsg("Element is updated!");
                return true;
            }
        }
        responseCreator.addToMsg("Input id is not found");
        return false;
    }

    @Override
    public void removeById(long id) {
        if (tickets.stream().anyMatch(ticket -> ticket.getId() == id)) {
            responseCreator.addToMsg("Element is successfully removed from collection");
        } else
            responseCreator.addToMsg("Element is successfully removed from collection");
        tickets = tickets.stream()
                .filter(ticket -> ticket.getId() != id)
                .collect(Collectors.toCollection(HashSet::new));
        removeId(id);
    }

    @Override
    public void clear() {
        tickets.clear();
    }

    @Override
    public boolean checkId(long id) {
        if (tickets.stream().anyMatch(ticket -> ticket.getId() == id)) {
            return true;
        } else
            return false;
    }

    @Override
    public ZonedDateTime getInitializationTime() {
        return creationDate;
    }

    @Override
    public boolean containsId(long id) {
        return tickets.stream().anyMatch(x -> x.getId() == id);
    }

    @Override
    public HashSet<ServerTicket> getTickets() {
        sort();
        return tickets;
    }
}
