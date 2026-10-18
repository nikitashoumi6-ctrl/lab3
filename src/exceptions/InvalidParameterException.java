package exceptions;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String parameter, String reason) {
        super(parameter + " " + reason);
    }
}