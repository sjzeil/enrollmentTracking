package edu.odu.cs.zeil.enrollment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class History {

    private static Path historyDirectory;
    private LocalDate start;
    private LocalDate stop;
    

    public History(int semesterCode) {
        String dateFormat = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        Path datesFile = (historyDirectory != null) ? historyDirectory.resolve(Integer.toString(semesterCode)).resolve("dates.txt") : Paths.get("unknownDates.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(datesFile.toFile()))) {
            start = LocalDate.parse(in.readLine(), formatter);
            stop = LocalDate.parse(in.readLine(), formatter);
        } catch (IOException ex) {
            System.err.println ("Could not read " + datesFile.toString() + "\n" + ex.getMessage());
            if (start == null)
                start = LocalDate.of(2020, 1, 1);
            if (stop == null)
                stop = start.plusDays(100);
        }
    }

    public static void setHistoryDir(Path path) {
        historyDirectory = path;
    }

    public LocalDate getRegistrationStart() {
        return start;
    }

    public LocalDate getAddDeadline() {
        return stop;
    }

}
