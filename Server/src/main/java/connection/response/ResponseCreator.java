package connection.response;

import general.response.Response;
import general.response.ResponseType;

public interface ResponseCreator {
    public void addToMsg(String message);

    public Response getResponse(String message, ResponseType responseType);

    public Response getResponse();
}
