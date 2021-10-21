package collection;

public interface TicketShower {
    static String toStrView(ServerTicket st) {
        return "Ticket{id = " + st.getId() +
                ", name = " + st.getName() +
                ", coordinate X = " + st.getCoordinates().getX() +
                ", coordinate Y = " + st.getCoordinates().getY() +
                ", creation date = " + st.getCreationDate() +
                ", price = " + st.getPrice() +
                ", discount = " + st.getDiscount() +
                ", refundable = " + st.isRefundable() +
                "} Ticket type = " + st.getType().getUrl() +
                ", Event{id = " + st.getEvent().getId() +
                ", name = " + st.getEvent().getName() +
                ", description = " + st.getEvent().getDescription() +
                "} Event Type = " + st.getEvent().getEventType().getUrl() + "\n";
    }
}
