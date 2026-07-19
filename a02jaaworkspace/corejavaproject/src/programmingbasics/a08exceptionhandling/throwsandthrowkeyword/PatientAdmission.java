package programmingbasics.a08exceptionhandling.throwsandthrowkeyword;
//done by RAmesh
public class PatientAdmission {

    //person does not know context. so report problems and not handle it.

    // Method that may throw the exception (uses throws)
    public void admitPatient(int age) throws InvalidAgeException {
        if (age <= 0 || age > 120) {
            // throw: used to throw the custom exception
            throw new InvalidAgeException("Invalid age for patient admission: " + age);
        }

        System.out.println("Patient admitted successfully. Age: " + age);
    }

    // Intermediate method (does not catch, so exception propagates)
    public void processAdmission(int age) throws InvalidAgeException {
        admitPatient(age); // exception propagates here
    }
}
