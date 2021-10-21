package connection.request;

import general.request.Request;
import general.request.RequestType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class RequestReaderImpl implements RequestReader {
    private Selector selector;

    @Override
    public Request requestReader(Selector selector) throws IOException, ClassNotFoundException {
        this.selector = selector;
        byte[] bytes;
        try {
            bytes = readBytes();
        } catch (BufferOverflowException e) {
            return new Request(RequestType.ERROR_TYPE_REQUEST, "no command", "");
        }
        return deserializeRequest(bytes);
    }

    private byte[] readBytes() throws IOException {
        int capacity = 16384;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    channel = (SocketChannel) key.channel();
                    int bytesRead = channel.read(byteBuffer);
                    channel.register(selector, SelectionKey.OP_WRITE);
                    if (bytesRead == capacity) {
                        throw new BufferOverflowException();
                    }
                }
                iterator.remove();
            }
        }
        return byteBuffer.array();
    }

    private Request deserializeRequest(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream stream = new ObjectInputStream(inputStream);
        return (Request) stream.readObject();
    }
}
