/**
 * 
 */
package edu.odu.cs.zeil.enrollment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about a semester relevant to enrollment tracking.
 * 
 * @author zeil
 *
 */
public class Semester {
	private int semesterCode;
	private LocalDate startOfPreregistration;
	private LocalDate addDeadline;
	
	private class DataPoint implements Comparable<DataPoint> {
		public double date;
		public int enrollment;

		public DataPoint(double aDate, int enroll) {
			date = aDate;
			enrollment = enroll;
		}

		@Override
		public int compareTo(DataPoint dp) {
			double d = date - dp.date;
			if (d < 0.0) 
				return -1;
			else if (d == 0.0)
				return 0;
			else
				return 1;
		}
		
		public String toString() {
			return "(" + date + "," + enrollment + ")";
		}
	}
	
	private Map<String, List<DataPoint>> history;
	
	
	/**
	 * Create a new semester object.
	 * @param code  ODU semester code, form is YYYYN0, where YYYY is the year
	 *     in which the Fall semester takes place, N=1 for Fall, =2 for 
	 *     Spring, and =3 for Summer.
	 * @param registration   date on which pre-registration for this semester 
	 *       began
	 * @param frozen   date after which students may no longer enroll in
	 *          additional courses for this semester
	 */
	public Semester (int code, LocalDate registration, LocalDate frozen) {
		semesterCode = code;
		startOfPreregistration = registration;
		addDeadline = frozen;
		history = new HashMap<>();
	}
	
	/**
	 * Describe semester as  Fall/Spring/Summer Year 
	 */
	public String description() {
		String semester = "Fall";
		int semPart = semesterCode % 100;
		int year = semesterCode / 100;
		if (semPart == 20) {
			semester = "Spring";
			++year;
		} else if (semPart == 30) {
			semester = "Summer";
			++year;
		}
		return semester + ' ' + year;
	}
	
	
	/**
	 * Express a date as a fraction of the time passed between the opening
	 * and close of registration. 
	 * @param date  a date
	 * @return a number in the range 0..1, 0 if date < getStartOfRegistration(),
	 *    1 if date >= getAddDeadline(), otherwise
	 *    numDaysBetween(date,getStartOfRegistration()) /
	 *      numDaysBetween(getAddDeadline,getStartOfRegistration()) 
	 */
	public double enrollmentCompletion(LocalDate date) {
			long semesterLen = startOfPreregistration.until(addDeadline, 
					ChronoUnit.DAYS);
			long elapsed = startOfPreregistration.until(date, ChronoUnit.DAYS);
			double fraction = (double)(elapsed+1) / (double)(semesterLen + 1);
			return fraction;
	}
	
	
	public String toString() {
		return "" + semesterCode + ":" + startOfPreregistration
				+ ":" + addDeadline;
	}

	public int getSemesterCode() {
		return semesterCode;
	}

	public void setSemesterCode(int semesterCode) {
		this.semesterCode = semesterCode;
	}

	public LocalDate getStartOfPreregistration() {
		return startOfPreregistration;
	}

	public void setStartOfPreregistration(LocalDate startOfPreregistration) {
		this.startOfPreregistration = startOfPreregistration;
	}

	public LocalDate getAddDeadline() {
		return addDeadline;
	}

	public void setAddDeadline(LocalDate addDeadline) {
		this.addDeadline = addDeadline;
	}

	/**
	 * Adds an enrollment data point for the semester.
	 * @param course identifier for the course being tracked 
	 * @param date date for enrollment point (dates prior to 
	 *     getStartOfPreregistration() or after getAddDeadLine() are
	 *     ignored.
	 * @param enroll number of students enrolled at the end of the day on date
	 */
	public void addPoint(String course, LocalDate date, int enroll) {
		List<DataPoint> points = history.get(course);
		if (points == null) {
			points = new ArrayList<DataPoint>();
			history.put(course, points);
		}
		DataPoint pt = new DataPoint(enrollmentCompletion(date), enroll);
		
		int pos = Collections.binarySearch(points, pt);
		if (pos >= 0) {
			points.set(pos, pt);
		} else {
			points.add(-pos - 1, pt);			
		}
	}
	
    /**
     * Estimates fraction of enrollment as of a given date.	
     * @param course course being examined
     * @param date date of inquiry
     * @return estimated fraction (0.0 to 1.0) of a course's total enrollment
     *     as of that date. 
     */
	public double completion(String course, LocalDate date) {
		return completion(course, enrollmentCompletion(date));
	}

	
	/**
     * Estimates fraction of enrollment as of a given fraction of the
     * time elapsed between start and end of registration.
     * 	
     * @param course course being examined
     * @param dateCompletion fraction of registration time elapsed
     * @return estimated fraction (0.0 to 1.0) of a course's total enrollment
     *     as of that date. 
     */
	public double completion(String course, double dateCompletion) {
		if (dateCompletion >= 1.0)
			return 1.0;
		else if (dateCompletion <= 0.0)
			return 0.0;
		else {
			List<DataPoint> points = history.get(course);
			if (points == null || points.size() == 0)
				return 0.0;
			
			int maxEnroll = points.get(points.size()-1).enrollment;
			double projected = 0;
			
			DataPoint key = new DataPoint(dateCompletion, 0);
			int k = Collections.binarySearch(points, key);
			if (k >= 0) {
				projected = points.get(k).enrollment;
			} else {
				k = -k - 1;
				DataPoint p0 = new DataPoint(0.0, 0);
				if (k > 0) {
					p0 = points.get(k-1);
				}
				DataPoint p1 = new DataPoint(1.0,  maxEnroll);
				if (k <= points.size()-1) {
					p1 = points.get(k);
				}
				double d = (dateCompletion - p0.date) / (p1.date - p0.date);
				projected = (double)p0.enrollment 
						+ (d * (double)(p1.enrollment - p0.enrollment));
			}
			return projected / (double)maxEnroll;
			
		}
	}

	
	
	
}
