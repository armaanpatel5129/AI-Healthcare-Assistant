public class HealthAnalyzer {

    // The answer our analyzer gives back
    public static class Result {
        public String riskLevel;
        public String guidance;
        public String warning;
    }

    // Words that mean EMERGENCY
    static String[] EMERGENCY_WORDS = {
        "chest pain", "difficulty breathing", "cannot breathe", "unconscious",
        "severe bleeding", "seizure", "stroke", "suicidal"
    };

    // Words that mean MODERATE
    static String[] MODERATE_WORDS = {
        "high fever", "vomiting", "blood", "persistent"
    };

    // Main method: give symptoms, duration, severity -> get a Result
    public static Result analyze(String symptoms, String duration, String severity) {
        String text = symptoms.toLowerCase();
        String sev = severity.toUpperCase();
        Result r = new Result();

        // Rule 1: EMERGENCY
        if (containsAny(text, EMERGENCY_WORDS)) {
            r.riskLevel = "EMERGENCY";
            r.guidance = "These symptoms can be serious. Seek immediate emergency medical care.";
            r.warning = "Call your local emergency number or go to the nearest hospital now.";
            return r;
        }

        // Rule 2: MODERATE
        if (sev.equals("SEVERE") || getDays(duration) >= 3 || containsAny(text, MODERATE_WORDS)) {
            r.riskLevel = "MODERATE";
            r.guidance = "This symptom pattern may need medical attention. Rest, drink fluids, and consult a healthcare professional.";
            r.warning = "Please see a doctor soon, especially if symptoms get worse.";
            return r;
        }

        // Rule 3: LOW (default)
        r.riskLevel = "LOW";
        r.guidance = "Rest, drink enough water, and monitor your symptoms.";
        r.warning = "If symptoms continue or worsen, consult a healthcare professional.";
        return r;
    }

    // Checks if the text contains any word from the list
    static boolean containsAny(String text, String[] words) {
        for (String w : words) {
            if (text.contains(w)) {
                return true;
            }
        }
        return false;
    }

    // Reads days from text like "2 days" or "1 week"
    static int getDays(String duration) {
        if (duration == null) return 0;
        String d = duration.toLowerCase();
        String digits = d.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return 0;
        int number = Integer.parseInt(digits);
        if (d.contains("week")) return number * 7;
        if (d.contains("month")) return number * 30;
        return number;
    }

    // Test it here (only for your testing)
    public static void main(String[] args) {
        test("headache", "1 day", "MILD");
        test("fever, vomiting", "2 days", "MODERATE");
        test("cough", "5 days", "MILD");
        test("chest pain", "1 hour", "SEVERE");
    }

    static void test(String s, String d, String sev) {
        Result r = analyze(s, d, sev);
        System.out.println(s + " -> " + r.riskLevel);
    }
}