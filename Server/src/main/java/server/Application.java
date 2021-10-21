package server;

import collection.CollectionManager;
import collection.ServerTicket;
import commands.CommandWorker;
import connection.ServerConnectionManager;
import connection.request.RequestReader;
import connection.response.ResponseCreator;
import connection.response.ResponseSender;
import fileWorker.TicketReader;
import general.IO;
import subsidiary.TicketBuilder;

public class Application implements IO, Server {
    private final TicketReader ticketReader;
    private final CollectionManager collectionManager;
    private final CommandWorker commandWorker;
    private final ServerConnectionManager connectionManager;
    private final ResponseCreator responseCreator;
    private final RequestReader requestReader;
    private final ResponseSender responseSender;
    private TicketBuilder ticketBuilder;
    private volatile boolean isRunning = true;

    public Application(TicketReader ticketReader, CollectionManager collectionManager, CommandWorker commandWorker,
                       ServerConnectionManager connectionManager, ResponseCreator responseCreator,
                       RequestReader requestReader, ResponseSender responseSender) {
        this.ticketReader = ticketReader;
        this.collectionManager = collectionManager;
        this.commandWorker = commandWorker;
        this.connectionManager = connectionManager;
        this.responseCreator = responseCreator;
        this.requestReader = requestReader;
        this.responseSender = responseSender;
    }

    public void start(String address, int port) {

    }

    @Override
    public void start(String[] values) {

    }

    @Override
    public void exit() {

    }
}
