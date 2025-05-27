/**
 * 
 */
package edu.odu.cs.zeil.enrollment;


/**
 * Information about a section (a course unit identified by a single CRN)
 * 
 * @author zeil
 *
 */
record Section (int crn, String subject, String number, String title, int credits, int enrollment, String link, String xList, char campus, String time, String days, String building, String room, String instructor) {

    public static Section loadFromCSV(String[] fields) {
        // Seats,CRN,SUBJ,CRSE,TITLE,CR HRS,XLST CAP,ENR,LINK,XLST GROUP,SCHED TYPE,,CAMPUS,INSM,PRINT?,TIME,DAYS,BLDG,ROOM,OVERRIDE,INSTRUCTOR,,OVERALL CAP,OVERALL ENR,,,PTRM START,PTRM END,WL CAP,WL,WL REMAIN,NOTES,COMMENTS,COLL
        int crn = Integer.parseInt(fields[1]);
        String subj = fields[2];
        String num = fields[3];
        String title = fields[4];
        int cr = Integer.parseInt(fields[5]);
        int enr = Integer.parseInt(fields[7]);
        String link = fields[8];
        String xList = fields[9];
        char campus = (fields[12].length() > 0) ? fields[12].charAt(0) : ' ';
        String  time = fields[15];
        String days = fields[16];
        String bldg = fields[17];
        String room = fields[18];
        String instructor = fields[20];

        return new Section(crn, subj, num, title, cr, enr, link, xList, campus, time, days, bldg, room, instructor);
    }

}