package exceptions;

public class DBException extends RuntimeException {
    public DBException() {
        super();
    }

    public DBException(Throwable s) {
        super(s);
    }
}
