package dbarch.a11database.model;

import java.time.LocalDate;

public class Patient {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bloodType;
    private LocalDate lastVisit;
    private String allergies;

    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String bloodType, LocalDate lastVisit, String allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.lastVisit = lastVisit;
        this.allergies = allergies;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getBloodType() { return bloodType; }
    public LocalDate getLastVisit() { return lastVisit; }
    public String getAllergies() { return allergies; }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", DOB: " + dateOfBirth + ", Blood Type: " + bloodType +
                ", Last Visit: " + lastVisit + ", Allergies: " + allergies;
    }
}
