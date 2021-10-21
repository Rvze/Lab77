package subsidiary;

import exceptions.EnumNotFoundException;
import exceptions.InvalidFieldException;
import general.*;

import java.time.ZonedDateTime;
import java.util.Scanner;

public class TicketBuilderImpl implements TicketBuilder, IO {
    private boolean isScript;
    private TicketValidator validator;
    private Scanner scanner;
    private String line;

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Поле может быть null, Значение поля должно быть больше 0
    private Long discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private boolean refundable;
    private TicketType type; //Поле не может быть null
    private Event event; //Поле может быть null

    public TicketBuilderImpl(TicketValidator validator, Scanner scanner, boolean isScript) {
        this.validator = validator;
        this.scanner = scanner;
        this.isScript = isScript;
    }

    public TicketBuilderImpl() {

    }

    @Override
    public void setTicketId(Long id) {
        if (id == null || id < 0) {
            println("Invalid value");
            askTicketId();
        }
        this.id = id;
    }

    @Override
    public void setName(String name) throws InvalidFieldException {
        validator.validateName(name);
        this.name = name;
    }

    @Override
    public void setCoordinateX(long x) throws InvalidFieldException {
        if (coordinates == null) {
            this.coordinates = new Coordinates();
        }
        coordinates.setX(x);
    }

    @Override
    public void setCoordinateY(Integer y) throws InvalidFieldException {
        validator.validateCoordinateY(y);
        if (coordinates == null) {
            this.coordinates = new Coordinates();
        }
        coordinates.setY(y);
    }

    @Override
    public void setPrice(Float price) throws InvalidFieldException {
        validator.validatePrice(price);
        this.price = price;
    }

    @Override
    public void setDiscount(Long discount) throws InvalidFieldException {
        validator.validateDiscount(discount);
        this.discount = discount;
    }

    @Override
    public Boolean setRefundable(boolean refundable) throws InvalidFieldException {
        return refundable;
    }

    @Override
    public void setTicketType(TicketType ticketType) throws InvalidFieldException {
        validator.validateTicketType(ticketType);
        this.type = ticketType;
    }

    @Override
    public void setEventName(String eventName) throws InvalidFieldException {
        validator.validateEventName(name);
        if (event == null) {
            event = new Event();
        }
        event.setName(eventName);
    }

    @Override
    public void setEventId(Long eventId) throws InvalidFieldException {
        if (eventId == null || eventId <= 0) {
            print("Invalid value");
            askEventId();
        } else if (event == null) {
            event = new Event();
        }
        event.setId(eventId);
    }

    @Override
    public void setEventDescription(String eventDescription) throws InvalidFieldException {
        validator.validateEventDescription(eventDescription);
        event.setDescription(eventDescription);
    }

    @Override
    public void setDescription(String description) throws InvalidFieldException {
        validator.validateDescription(description);
        event.setDescription(description);
    }

    @Override
    public void setEventType(EventType eventType) {

        event.setEventType(eventType);
    }

    @Override
    public void setCreationDate() {
        creationDate = ZonedDateTime.now();
    }

    @Override
    public Ticket getTicket() {
        return new Ticket(name, coordinates, price, discount, refundable, type, event);
    }

    /**
     * Находит enum TicketType
     *
     * @param s
     * @return найденный TicketType
     * @throws InvalidFieldException
     * @throws EnumNotFoundException
     */
    @Override
    public TicketType checkTicketType(String s) throws InvalidFieldException, EnumNotFoundException {
        for (TicketType ticketType : TicketType.values()) {
            if (s.equalsIgnoreCase(ticketType.getUrl())) {
                validator.validateTicketType(ticketType);
                return ticketType;
            }
        }
        throw new EnumNotFoundException("There is no enum named " + s);
    }

    /**
     * Находит enum EventType
     *
     * @param s
     * @return найденный EventType
     * @throws InvalidFieldException
     * @throws EnumNotFoundException
     */
    @Override
    public EventType checkEventType(String s) throws EnumNotFoundException {
        for (EventType eventType : EventType.values()) {
            if (s.equalsIgnoreCase(eventType.getUrl())) {

                return eventType;
            }
        }
        throw new EnumNotFoundException("There is no enum named " + s);
    }

    /**
     * Ввод всех полей
     */
    @Override
    public void inputFieldsFile() {
        askName();
        askCoordinateX();
        askCoordinateY();
        askPrice();
        askDiscount();
        askRefundable();
        askTicketType();
        askEventName();
        askEventDescription();
        askEventType();
    }

    @Override
    public Long askTicketId() {
        String str;
        try {
            println("Enter the Ticket id: ");
            str = scanner.nextLine();
            if (InputChecker.checkLong(str.trim())) {
                setTicketId(Long.parseLong(str));
            } else {
                print("Ticket id should be long, try again: ");
                if (!isScript)
                    askTicketId();
            }
        } catch (Exception e) {
            print("Input error");
            if (!isScript)
                askTicketId();
        }
        return null;
    }

    @Override
    public void askName() {
        this.println("Input Ticket name: ");
        try {
            setName(inputLine());
        } catch (InvalidFieldException e) {
            println("Ticket name should be String, can't be null");
            if (!isScript) {
                askName();
            }
        }
    }

    @Override
    public void askCoordinateX() {
        this.println("Input Ticket coordinate x: ");
        try {
            setCoordinateX(Long.parseLong(inputLine()));
        } catch (InvalidFieldException | NumberFormatException e) {
            println("Ticket coordinate x should be long ");
            if (!isScript) {
                askCoordinateX();
            }
        }
    }

    @Override
    public void askCoordinateY() {
        this.println("Input Ticket coordinate y: ");
        try {
            setCoordinateY(Integer.parseInt(inputLine()));
        } catch (InvalidFieldException | NumberFormatException e) {
            println("Ticket coordinate y should be int, can't be null ");
            if (!isScript) {
                askCoordinateY();
            }
        }
    }

    @Override
    public void askPrice() {
        this.println("Input Ticket price: ");
        try {
            setPrice(Float.parseFloat(inputLine()));
        } catch (InvalidFieldException | NumberFormatException e) {
            println("Ticket price should be float and greater than 0 ");
            if (!isScript) {
                askPrice();
            }
        }
    }

    @Override
    public void askDiscount() {
        this.println("Input Ticket discount: ");
        try {
            setDiscount(Long.parseLong(inputLine()));
        } catch (InvalidFieldException | NumberFormatException e) {
            println("Ticket discount should be long, greater than 0 and less than 100 ");
            if (!isScript) {
                askDiscount();
            }
        }
    }

    @Override
    public void askRefundable() {
        this.println("Input Ticket refundable");
        try {
            setRefundable(Boolean.parseBoolean(inputLine()));
        } catch (InvalidFieldException e) {
            println("Ticket refundable should be boolean");
            if (!isScript) {
                askRefundable();
            }
        }
    }

    @Override
    public void askTicketType() {
        TicketType.printType();
        this.print("Input Ticket type: ");
        try {
            setTicketType(checkTicketType(inputLine()));
        } catch (InvalidFieldException e) {
            println("Field TicketType can't be null");
            if (!isScript)
                askTicketType();

        } catch (EnumNotFoundException e) {
            println(e.getMessage());
            if (!isScript)
                askTicketType();
        }
    }

    @Override
    public void askEventId() {
        print("Enter Event id: ");
        try {
            setEventId(Long.parseLong(inputLine()));
        } catch (NumberFormatException | InvalidFieldException e) {
            print("Event id should be long");
            if (!isScript) {
                askEventId();
            }
        }
    }

    @Override
    public void askEventName() {
        this.println("Input Event name: ");
        try {
            setEventName(inputLine());
        } catch (InvalidFieldException e) {
            println("Field Event name can't be null and void ");
            if (!isScript)
                askEventName();
        }
    }


    @Override
    public void askEventDescription() {
        this.println("Input Event description: ");
        try {
            setDescription(inputLine());
        } catch (InvalidFieldException e) {
            println("Event description can't be void ");
            if (!isScript)
                askEventDescription();
        }
    }

    @Override
    public void askEventType() {
        EventType.printValues();
        this.println("Input EventType: ");
        try {
            setEventType(checkEventType(inputLine()));
        } catch (EnumNotFoundException e) {
            println(e.getMessage());
            if (!isScript)
                askEventType();
        }
    }

    @Override
    public Ticket askTicket() {
        inputFieldsFile();
        return getTicket();
    }

    private String inputLine() {
        line = scanner.nextLine().trim().toLowerCase();
        return line;
    }
}
