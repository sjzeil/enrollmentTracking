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
record Section (int crn, String subject, String number, String title, int credits, int enrollment, String link, String xList, String time, String days, String building, String room, String instructor) {

    public static Section loadFromCSV(String[] cs115Info) {
        // Seats,CRN,SUBJ,CRSE,TITLE,CR HRS,XLST CAP,ENR,LINK,XLST GROUP,SCHED TYPE,,CAMPUS,INSM,PRINT?,TIME,DAYS,BLDG,ROOM,OVERRIDE,INSTRUCTOR,,OVERALL CAP,OVERALL ENR,,,PTRM START,PTRM END,WL CAP,WL,WL REMAIN,NOTES,COMMENTS,COLL
        int crn = Integer.parseInt(cs115Info[1]);
        String subj = cs115Info[2];
        String num = cs115Info[3];
        String title = cs115Info[4];
        int cr = Integer.parseInt(cs115Info[5]);
        int enr = Integer.parseInt(cs115Info[7]);
        String link = cs115Info[8];
        String xList = cs115Info[9];
        String  time = cs115Info[15];
        String days = cs115Info[16];
        String bldg = cs115Info[17];
        String room = cs115Info[18];
        String instructor = cs115Info[20];

        return new Section(crn, subj, num, title, cr, enr, link, xList, time, days, bldg, room, instructor);
    }

}