package edu.odu.cs.zeil.enrollment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class SemesterEnrollment {

    private Semester semester;
    private Interpolator total;
    private Map<Character, Interpolator> byCampus;
    private LocalDate registration;
    private LocalDate addDrop;

    public SemesterEnrollment(int semesterCode, Path historyPath) {
        readSemesterInfo(semesterCode, historyPath);
        semester = new Semester(semesterCode, registration, addDrop);
        total = new Interpolator();
        byCampus = new HashMap<>();
        readEnrollmentDetails(historyPath);
    }

    private void readEnrollmentDetails(Path historyPath) {
        FilenameFilter csvFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv");
            }
        };
        for (File csvFile : historyPath.toFile().listFiles(csvFilter)) {
            String name = csvFile.getName();
            name = name.substring(0, name.length() - 4); // discard ".csv"
            LocalDate localDate = LocalDate.parse(name, DateTimeFormatter.ISO_DATE);
            double x = semester.enrollmentCompletion(localDate);
            try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
                String[] headers = reader.readNext();
                String[] sectionFields = reader.readNext();
                while (sectionFields != null) {
                    Section section = Section.loadFromCSV(sectionFields);
                    int y = section.enrollment() * section.credits();
                    if (y > 0) {
                        processEnrollment(section, x);
                    }
                    sectionFields = reader.readNext();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }

        }
    }

    private void processEnrollment(Section section, double x) {
        double y = section.enrollment() * section.credits();
        total.add(new DataPoint(x, y));
        char campus = section.campus();
        Interpolator byCampusData = byCampus.get(campus);
        if (byCampusData == null) {
            byCampusData = new Interpolator();
            byCampus.put(campus, byCampusData);
        }
        byCampusData.add(new DataPoint(x, y));
    }

    private void readSemesterInfo(int semesterCode, Path historyPath) {
        Path datesFile = historyPath.resolve("dates.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(datesFile.toFile()))) {
            String line = in.readLine();
            registration = parseDate(line);
            line = in.readLine();
            addDrop = parseDate(line);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find " + datesFile);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading from " + datesFile + "\n" + e.getMessage());
            System.exit(2);
        }
    }

    private LocalDate parseDate(String line) {
        LocalDate localDate = LocalDate.parse(line, DateTimeFormatter.ISO_DATE);
        return localDate;
    }

    public LocalDate getStartOfPreregistration() {
        return registration;
    }

    public Object getAddDeadline() {
        return addDrop;
    }

    public int getTotal(LocalDate date) {
        double fraction = semester.enrollmentCompletion(date);
        double value = total.get(fraction);
        return (int) Math.round(value);
    }

    public int getOnCampusUG(LocalDate date) {
        return getDataOrZero('A', date);
    }

    public int getOnCampusG(LocalDate date) {
        return getDataOrZero('N', date);
    }

    private int getDataOrZero(char campus, LocalDate date) {
        Interpolator dataset = byCampus.get(campus);
        if (dataset == null)
            return 0;
        else {
            double fraction = semester.enrollmentCompletion(date);
            double value = dataset.get(fraction);
            return (int) Math.round(value);
        }
    }

    public int getHamptonRoadsUG(LocalDate date) {
        return getDataOrZero('B', date);
    }

    public int getVirginiaUG(LocalDate date) {
        return getDataOrZero('E', date);
    }

    public int getOutOfStateUG(LocalDate date) {
        return getDataOrZero('H', date) + getDataOrZero('V', date);
    }

    public int getHamptonRoadsG(LocalDate date) {
        return getDataOrZero('O', date);
    }

    public int getVirginiaG(LocalDate date) {
        return getDataOrZero('R', date);
    }

    public int getOutOfStateG(LocalDate date) {
        return getDataOrZero('U', date) + getDataOrZero('W', date);
    }

    public int getUGTotal(LocalDate date) {
        return getOnCampusUG(date) + getHamptonRoadsUG(date) + getVirginiaUG(date) + getOutOfStateUG(date);
    }

    public int getGTotal(LocalDate date) {
        return getOnCampusG(date) + getHamptonRoadsG(date) + getVirginiaG(date) + getOutOfStateG(date);
    }

    public String description() {
        return semester.description();
    }

    public double enrollmentCompletion(LocalDate date) {
        return semester.enrollmentCompletion(date);
    }

    public LocalDate getRegistrationDate() {
        return semester.getStartOfPreregistration();
    }

    public LocalDate getAddDropDate() {
        return semester.getAddDeadline();
    }

}
