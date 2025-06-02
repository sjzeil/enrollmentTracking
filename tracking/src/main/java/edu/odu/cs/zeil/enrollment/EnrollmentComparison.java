package edu.odu.cs.zeil.enrollment;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Prints an HTML table comparing current enrollment in a semester to the
 * enrollment as of one year before.
 */
public class EnrollmentComparison {

    private int currentSemesterCode;
    private int priorSemesterCode;
    private LocalDate today;
    private SemesterEnrollment currentSemester;
    private SemesterEnrollment priorSemester;

    public EnrollmentComparison(int semesterCode) {
        currentSemesterCode = semesterCode;
        priorSemesterCode = semesterCode - 100;
        Path historicalData = Paths.get("/", "home", "zeil", "secure_html", "courseSchedule", "History");
        Path currentPath = historicalData.resolve("" + currentSemesterCode);
        currentSemester = new SemesterEnrollment(currentSemesterCode, currentPath);
        Path priorPath = historicalData.resolve("" + priorSemesterCode);
        priorSemester = new SemesterEnrollment(priorSemesterCode, priorPath);
        today = LocalDate.now();
    }

    /**
     * Run the enrollment prediction. Predicts final enrollment for a semester with
     * registration underway based upon data from the two prior years.
     * 
     * @param args Command-line arguments, semester-code courseNumber,
     *             e.g.,
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java ... edu.odu.cs.zeil.enrollment.EnrollmentComparison semester-code");
            System.exit(1);
        }
        int semesterCode = Integer.parseInt(args[0]);

        new EnrollmentComparison(semesterCode).generateTable();

    }

    private void generateTable() {
        print("<table border='1'><tr><th colspan='12'>Comparing " + currentSemester.description() + " to "
                + priorSemester.description() + "</th></tr>");
        print("<th></th><th colspan='5'>UGrad</th><th colspan='5'>Grad</th><th>Combined</th>");
        print("<tr><th> </th><th>On-campus</th><th>Remote</th><th>Virginia</th><th>Out of State</th><th>UG Total</th>");
        print("<th>On-campus</th><th>Remote</th><th>Virginia</th><th>Out of State</th><th>G Total</th><th>Total</th></tr>");

        double fraction = currentSemester.enrollmentCompletion(today);
        LocalDate priorReg = priorSemester.getRegistrationDate();
        LocalDate priorAddDrop = priorSemester.getAddDropDate();
        long priorDateRange = ChronoUnit.DAYS.between(priorReg, priorAddDrop);
        LocalDate comparativeDate = priorReg.plusDays(Math.round(fraction * priorDateRange));
        printDetailLine(priorSemester, comparativeDate);
        printDetailLine(currentSemester, today);
        printPercentageLine(currentSemester, today, priorSemester, comparativeDate);
        print("</table>");

    }

    private void printDetailLine(SemesterEnrollment semester, LocalDate date) {
        print("<tr><th>" + semester.description() + "</th>");
        print(td(semester.getOnCampusUG(date)));
        print(td(semester.getHamptonRoadsUG(date)));
        print(td(semester.getVirginiaUG(date)));
        print(td(semester.getOutOfStateUG(date)));
        print(td(semester.getUGTotal(date)));
        print(td(semester.getOnCampusG(date)));
        print(td(semester.getHamptonRoadsG(date)));
        print(td(semester.getVirginiaG(date)));
        print(td(semester.getOutOfStateG(date)));
        print(td(semester.getGTotal(date)));
        print(td(semester.getTotal(date)));

        print("</tr>");
    }

    private void printPercentageLine(SemesterEnrollment semester1, LocalDate date1, SemesterEnrollment semester0,
            LocalDate date0) {
        print("<tr><th>% Change</th>");
        print(td(pct(semester1.getOnCampusUG(date1), semester0.getOnCampusUG(date0))));
        print(td(pct(semester1.getHamptonRoadsUG(date1), semester0.getHamptonRoadsUG(date0))));
        print(td(pct(semester1.getVirginiaUG(date1), semester0.getVirginiaUG(date0))));
        print(td(pct(semester1.getOutOfStateUG(date1), semester0.getOutOfStateUG(date0))));
        print(td(pct(semester1.getUGTotal(date1), semester0.getUGTotal(date0))));
        print(td(pct(semester1.getOnCampusG(date1), semester0.getOnCampusG(date0))));
        print(td(pct(semester1.getHamptonRoadsG(date1), semester0.getHamptonRoadsG(date0))));
        print(td(pct(semester1.getVirginiaG(date1), semester0.getVirginiaG(date0))));
        print(td(pct(semester1.getOutOfStateG(date1), semester0.getOutOfStateG(date0))));
        print(td(pct(semester1.getGTotal(date1), semester0.getGTotal(date0))));
        print(td(pct(semester1.getTotal(date1), semester0.getTotal(date0))));

        print("</tr>");
    }

    private String pct(int v1, int v0) {
        double change;
        if (v0 != 0) {
            change = 100.0 * ((double)(v1 - v0))/v0;
            return String.format("%.1f",change) + '%';
        } else {
            return "---";
        }
    }

    private String td(int value) {
        return "<td>" + value + "</td>";
    }

    private String td(String value) {
        return "<td>" + value + "</td>";
    }

    private void print(String string) {
        System.out.println(string);
    }

}
