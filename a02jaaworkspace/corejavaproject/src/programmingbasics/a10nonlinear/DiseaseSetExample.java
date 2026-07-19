package programmingbasics.a10nonlinear;

import java.util.HashSet;
import java.util.Set;

public class DiseaseSetExample {
    public static void main(String[] args) {
        // Create a Set to store unique diseases
        Set<String> diseases = new HashSet<>();

        // Add diseases (some duplicates)
        diseases.add("Flu");
        diseases.add("Diabetes");
        diseases.add("Migraine");
        diseases.add("Flu");          // duplicate
        diseases.add("Hypertension");
        diseases.add("Diabetes");     // duplicate

        // Display all unique diseases
        System.out.println("Unique diseases diagnosed:");
        for (String disease : diseases) {
            System.out.println("- " + disease);
        }
    }
}
