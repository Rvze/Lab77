package connection.response;

import general.response.Response;
import general.response.ResponseType;

public class ResponseCreatorImpl implements ResponseCreator{
    private Response response = new Response();

    @Override
    public void addToMsg(String message) {
        String last = response.getMessage();
        response.setMessage(last + "\n" + message);
    }

    @Override
    public Response getResponse(String message, ResponseType responseType) {
        return new Response(responseType, message);
    }

    @Override
    public Response getResponse() {
        Response response = this.response;
        this.response = new Response();
        return response;
    }
}
