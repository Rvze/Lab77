package subsidiary;

import exceptions.InvalidFieldException;
import general.TicketType;

public class TicketValidatorImpl implements TicketValidator {
    @Override
    public void validateName(String name) throws InvalidFieldException {
        if (name == null || name.equals(" ")) {
            throw new InvalidFieldException("Invalid value for Ticket name");
        }
    }


    @Override
    public void validateCoordinateY(Integer y) throws InvalidFieldException {
        if (y == null) {
            throw new InvalidFieldException("Invalid value for Coordinate Y");
        }
    }

    @Override
    public void validatePrice(Float price) throws InvalidFieldException {
        if (price < 0) {
            throw new InvalidFieldException("Invalid value for Ticket price");
        }
    }

    @Override
    public void validateDiscount(Long discount) throws InvalidFieldException {
        if (100 <= discount && discount < 0) {
            throw new InvalidFieldException("Invalid value for Ticket discount");
        }
    }


    @Override
    public void validateTicketType(TicketType ticketType) throws InvalidFieldException {
        if (ticketType == null) {
            throw new InvalidFieldException("Invalid value for TicketType");
        }
    }


    @Override
    public void validateEventName(String eventName) throws InvalidFieldException {
        if (eventName == null || eventName.equals(" ")) {
            throw new InvalidFieldException("Invalid value for Event name");
        }
    }

    @Override
    public void validateEventDescription(String eventDescription) throws InvalidFieldException {
        if (eventDescription.equals("")) {
            throw new InvalidFieldException("Invalid value for Event description");
        }
    }

    @Override
    public void validateDescription(String description) throws InvalidFieldException {
        if (description.equals(" ")) {
            throw new InvalidFieldException("Invalid value for Event description");
        }
    }
}
