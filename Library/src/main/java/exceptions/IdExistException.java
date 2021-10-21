package exceptions;

public class IdExistException extends Exception {
    public IdExistException() {
        super("Id is already exist");
    }

}
