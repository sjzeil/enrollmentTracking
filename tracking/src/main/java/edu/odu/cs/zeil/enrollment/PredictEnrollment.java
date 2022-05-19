package edu.odu.cs.zeil.enrollment;

public class PredictEnrollment {

    private Semester registering;
    private Semester historical1;
    private Semester historical2;
 
    public PredictEnrollment(String semesterCode, String semesterM1Year, String semesterM2Year, String courseNumber) {
        History registeringHistory = new History(semesterCode);
        History history1 = new History(semesterM1Year);
        History history2 = new History(semesterM2Year);
        registering = new Semester(semesterCode, registeringHistory.getRegistratioNStart(), registeringHistory.getAddDeadline());
        
    }

    /**
     * Run the enrollment prediction.   Predicts final enrollment for a semester with
     * registration underway based upon data from the two prior years. 
     * @param args  Command-line arguments, semester-code courseNumber,
     *              e.g., 
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java ... edu.odu.cs.zeil.enrollment.PredictEnrollment semester-code courseNumber");
            System.exit(1);
        }
        String semesterCode = args[0];
        String courseNumber = args[1];

        String semesterM1Year = priorYear(semesterCode);
        String semesterM2Year = priorYear(semesterM1Year);
        
        new PredictEnrollment(semesterCode, semesterM1Year, semesterM2Year, courseNumber).predict();
        
    }

    private void predict() {
    }

    private static String priorYear(String semesterCode) {
        return null;
    }
}
