package programmingbasics.a07strings;

public class MedicalStringDemo {
    public static void main(String[] args) {

        // ====== 1. STRING METHODS ======
        System.out.println("=== String Methods ===");

        String patientName = "  Dr. Ramesh Sharma  ";
        System.out.println("Original: [" + patientName + "]");

        System.out.println("Trimmed: [" + patientName.trim() + "]");
        System.out.println("Uppercase: " + patientName.toUpperCase());
        System.out.println("Lowercase: " + patientName.toLowerCase());
        System.out.println("Length: " + patientName.length());
        System.out.println("Character at index 4: " + patientName.charAt(4));
        System.out.println("Substring (4 to 10): " + patientName.substring(4, 10));
        System.out.println("Contains 'Sharma'? " + patientName.contains("Sharma"));
        System.out.println("Replace 'Dr.' with 'Doctor': " + patientName.replace("Dr.", "Doctor"));

        String inputName = "dr. ramesh sharma";
        System.out.println("Equals: " + patientName.trim().equals(inputName));
        System.out.println("Equals Ignore Case: " + patientName.trim().equalsIgnoreCase(inputName));


        // ====== 2. STRINGBUILDER ======
        System.out.println("\n=== StringBuilder Example ===");

        StringBuilder sb = new StringBuilder();
        sb.append("Patient: ");
        sb.append("Ramesh");
        sb.append(" | ");
        sb.append("Diagnosis: Fever");

        System.out.println("Before modification: " + sb.toString());

        // Insert title
        sb.insert(9, "Dr. ");
        // Replace 'Fever' with 'Cold'
        sb.replace(sb.indexOf("Fever"), sb.indexOf("Fever") + "Fever".length(), "Cold");
        // Reverse the string
        sb.reverse();

        System.out.println("Modified & Reversed: " + sb.toString());


        // ====== 3. STRINGBUFFER ======
        System.out.println("\n=== StringBuffer Example ===");

        StringBuffer sbuf = new StringBuffer("Medical Report - ");
        sbuf.append("Patient: Ramesh");
        sbuf.append(" | Condition: Stable");

        System.out.println("Before Update: " + sbuf.toString());

        // Replace 'Stable' with 'Critical'
        int start = sbuf.indexOf("Stable");
        int end = start + "Stable".length();
        sbuf.replace(start, end, "Critical");

        System.out.println("After Update: " + sbuf.toString());
    }
}
