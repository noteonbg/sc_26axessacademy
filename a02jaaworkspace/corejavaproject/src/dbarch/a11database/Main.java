package dbarch.a11database;

import java.time.LocalDate;
import java.util.List;

import dbarch.a11database.model.Patient;
import dbarch.a11database.dao.PatientDAO;
import dbarch.a11database.dao.PatientDAOImpl;


public class Main {
    public static void main(String[] args) {
        PatientDAO dao = new PatientDAOImpl();

        Patient p1 = new Patient("Ramesh", "Kumar", LocalDate.of(1990, 5, 21), "A+", LocalDate.now(), "asthma");
        Patient p2 = new Patient("Harish", "Kumar", LocalDate.of(1985, 3, 14), "B-", LocalDate.now(), "diabetes");

        dao.insertPatient(p1);
        dao.insertPatient(p2);

        System.out.println("All Patients:");
        List<Patient> all = dao.getAllPatients();
        all.forEach(System.out::println);

        System.out.println("\nPatients with 'asthma':");
        List<Patient> asthmaPatients = dao.getPatientsByDisease("asthma");
        asthmaPatients.forEach(System.out::println);

        System.out.println("\nUpdating Suresh..");
        Patient updatedRamesh = new Patient("Suresh", "Kumar", LocalDate.of(1985, 3, 14), "B-", LocalDate.now(), "diabetes, hypertension");
        dao.updatePatient("Ramesh", updatedRamesh);

        System.out.println("\nAll Patients After Update:");
        dao.getAllPatients().forEach(System.out::println);
    }
}

