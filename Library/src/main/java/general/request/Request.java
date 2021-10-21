package general.request;

import general.Ticket;

import java.io.Serializable;

public class Request implements Serializable {
    private final static long serialVersionUID = -43814713L;

    private RequestType requestType;

    private String commandName;

    private String arg;

    private Ticket ticket;

    public Request(RequestType requestType, String commandName, String arg) {
        this.requestType = requestType;
        this.commandName = commandName;
        this.arg = arg;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
