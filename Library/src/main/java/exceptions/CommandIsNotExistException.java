package exceptions;

public class CommandIsNotExistException extends Exception {
    public CommandIsNotExistException(String str) {
        super(str);
    }
}
