package fileWorker;

import collection.ServerTicket;
import exceptions.IdExistException;
import exceptions.UnsupportedFileException;

public interface TicketReader extends CSVFileWorker {
    ServerTicket read(String[] values) throws IdExistException;

}
