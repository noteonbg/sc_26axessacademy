package programmingbasics.a08exceptionhandling;
// File: InvalidPatientCountException.java
public class InvalidPatientCountException extends Exception {
    public InvalidPatientCountException(String message) {
        super(message);
    }
}
