package dbarch.a11database.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dbarch.a11database.model.Patient;
import dbarch.a11database.config.DBConnection;

public class PatientDAOImpl implements PatientDAO {
    private final Connection connection;

    public PatientDAOImpl() {
        this.connection = DBConnection.getConnection();
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS patients (" +
                "first_name VARCHAR(50), " +
                "last_name VARCHAR(50), " +
                "date_of_birth DATE, " +
                "blood_type VARCHAR(3), " +
                "last_visit DATE, " +
                "allergies TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertPatient(Patient patient) {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, blood_type, last_visit, allergies) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, patient.getFirstName());
            ps.setString(2, patient.getLastName());
            ps.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            ps.setString(4, patient.getBloodType());
            ps.setDate(5, Date.valueOf(patient.getLastVisit()));
            ps.setString(6, patient.getAllergies());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatient(String lastName, Patient updatedPatient) {
        String sql = "UPDATE patients SET first_name=?, date_of_birth=?, blood_type=?, last_visit=?, allergies=? WHERE last_name=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, updatedPatient.getFirstName());
            ps.setDate(2, Date.valueOf(updatedPatient.getDateOfBirth()));
            ps.setString(3, updatedPatient.getBloodType());
            ps.setDate(4, Date.valueOf(updatedPatient.getLastVisit()));
            ps.setString(5, updatedPatient.getAllergies());
            ps.setString(6, lastName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public List<Patient> getPatientsByDisease(String disease) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE allergies ILIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + disease + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    private Patient mapRow(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("blood_type"),
                rs.getDate("last_visit").toLocalDate(),
                rs.getString("allergies")
        );
    }
}
