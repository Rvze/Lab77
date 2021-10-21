package general;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class IdTicket implements Comparable<IdTicket> {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Поле может быть null, Значение поля должно быть больше 0
    private Long discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private boolean refundable;
    private TicketType type; //Поле не может быть null
    private Event event; //Поле может быть null

    public IdTicket(int id, Ticket ticket, LocalDateTime creationDate) {
        this.id = id;
        this.name = ticket.getName();
        this.coordinates = ticket.getCoordinates();
        this.price = ticket.getPrice();
        this.discount = ticket.getDiscount();
        this.refundable = ticket.isRefundable();
        this.type = ticket.getType();
        this.event = ticket.getEvent();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Float getPrice() {
        return price;
    }

    public Long getDiscount() {
        return discount;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public TicketType getType() {
        return type;
    }

    public Event getEvent() {
        return event;
    }

    public Ticket getTicket() {
        return new Ticket(getName(), getCoordinates(), getPrice(), getDiscount(), isRefundable(), getType(), getEvent());
    }

    @Override
    public int compareTo(IdTicket o) {
        int result = name.compareTo(o.name);
        if (result == 0) {
            return Integer.compare(id, o.id);
        } else
            return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdTicket idTicket = (IdTicket) o;
        return id == idTicket.id && refundable == idTicket.refundable && name.equals(idTicket.name) && coordinates.equals(idTicket.coordinates) && creationDate.equals(idTicket.creationDate) && price.equals(idTicket.price) && discount.equals(idTicket.discount) && type == idTicket.type && event.equals(idTicket.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, discount, refundable, type, event);
    }

    @Override
    public String toString() {
        return "IdTicket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", discount=" + discount +
                ", refundable=" + refundable +
                ", type=" + type +
                ", event=" + event +
                '}';
    }
}
