package general.response;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    BASIC_RESPONSE,
    TICKET_RESPONSE,
    ERROR_RESPONSE;

    private final static long serialVersionUID = 1842L;
}
