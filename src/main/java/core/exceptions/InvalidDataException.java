package core.exceptions;

public class InvalidDataException extends Exception {
    public InvalidDataException() {
        super("Galaxy could not be constructed due to invalid data");
    }
}
