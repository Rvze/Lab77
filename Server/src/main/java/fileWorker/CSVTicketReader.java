package fileWorker;

import collection.CollectionManager;
import collection.ServerTicket;
import exceptions.EnumNotFoundException;
import exceptions.IdExistException;
import exceptions.InvalidFieldException;
import exceptions.UnsupportedFileException;
import general.IO;
import subsidiary.InputChecker;
import subsidiary.TicketBuilder;
import subsidiary.TicketBuilderImpl;
import subsidiary.TicketValidatorImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVTicketReader implements TicketReader, IO {
    private final CollectionManager manager;
    private String filePath;
    private String separator;
    private Scanner scanner;

    public CSVTicketReader(CollectionManager collectionManager) {
        manager = collectionManager;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void inputFilePath() {
        println("Enter the file path: ");
        Scanner reader = new Scanner(System.in);
        filePath = reader.nextLine().trim().toLowerCase();
        File file = new File(filePath);
        while (!file.exists() || file.isDirectory() || !file.canRead()) {
            if (!file.exists()) {
                errPrint("File isn't exist, try again: ");
            } else if (file.isDirectory()) {
                errPrint("This is Directory, try again: ");
            } else
                errPrint("File can't be read, try again: ");
            filePath = reader.nextLine();
            file = new File(filePath.trim().toLowerCase());
        }
        scanner = new Scanner(System.in);
        println("File path is correct!");
    }

    @Override
    public ServerTicket read(String[] values) throws IdExistException {
        TicketBuilder ticketBuilder = new TicketBuilderImpl(new TicketValidatorImpl(), scanner, false);
        for (int i = 0; i < values.length; ++i) {
            values[i] = values[i].trim();
            if (values[i].isEmpty()) {
                println("You has empty field in the file");
                return null;
            }
        }
        try {
            ticketBuilder.setName(values[0]);
            if (InputChecker.checkLong(values[1]))
                ticketBuilder.setCoordinateX(Long.parseLong(values[1]));
            else
                throw new InvalidFieldException("Coordinate X should be long");
            if (InputChecker.checkInt(values[2]))
                ticketBuilder.setCoordinateY(Integer.parseInt(values[2]));
            else
                throw new InvalidFieldException("Coordinate Y should be int");
            if (InputChecker.checkFloat(values[3]))
                ticketBuilder.setPrice(Float.parseFloat(values[3]));
            else
                throw new InvalidFieldException("Price should be float");
            if (InputChecker.checkLong(values[4]))
                ticketBuilder.setDiscount(Long.parseLong(values[4]));
            else
                throw new InvalidFieldException("Ticket discount should be long");
            ticketBuilder.setRefundable(Boolean.parseBoolean(values[5]));
            ticketBuilder.setTicketType(ticketBuilder.checkTicketType(values[6]));
            if (InputChecker.checkLong(values[7]))
                ticketBuilder.setEventId(Long.parseLong(values[7]));
            ticketBuilder.setEventName(values[8]);
            ticketBuilder.setDescription(values[9]);
            ticketBuilder.setEventType(ticketBuilder.checkEventType(values[10]));

        } catch (InvalidFieldException | EnumNotFoundException e) {
            print(e.getMessage());
            return null;
        }
        return manager.getServerTicket(ticketBuilder.getTicket());
    }


}
