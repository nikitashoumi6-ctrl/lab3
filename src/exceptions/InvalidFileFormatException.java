package exceptions;

public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(int lineNumber, String reason) {
        super("Строка " + lineNumber + ": " + reason);
    }

    public InvalidFileFormatException(int lineNumber, String reason, Throwable cause) {
        super("Строка " + lineNumber + ": " + reason, cause);
    }
}