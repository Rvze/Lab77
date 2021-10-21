package connection.response;

import connection.ServerConnectionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public class ServerConnectionManagerImpl1 implements ServerConnectionManager {
    private ServerSocketChannel serverChannel;

    @Override
    public void openConnection(int port) throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));
    }

    @Override
    public void closeConnection() throws IOException {
        serverChannel.socket().close();
        serverChannel.close();
    }
}
