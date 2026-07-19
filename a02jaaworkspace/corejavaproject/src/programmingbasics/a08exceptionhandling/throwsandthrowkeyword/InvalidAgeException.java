package programmingbasics.a08exceptionhandling.throwsandthrowkeyword;

public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
