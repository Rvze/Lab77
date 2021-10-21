package connection.request;

import general.request.Request;

import java.io.IOException;
import java.nio.channels.Selector;

public interface RequestReader {
    Request requestReader(Selector selector) throws IOException, ClassNotFoundException;
}
