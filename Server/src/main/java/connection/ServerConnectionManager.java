package connection;

import java.io.IOException;

public interface ServerConnectionManager {
    void openConnection(int port) throws IOException;

    void closeConnection() throws IOException;

    //Selector select() throws IOException;
}
