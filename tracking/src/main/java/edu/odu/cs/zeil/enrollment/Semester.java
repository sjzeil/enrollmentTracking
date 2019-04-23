/**
 * 
 */
package edu.odu.cs.zeil.enrollment;

import java.time.LocalDate;

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
	
	/**
	 * Create a new semester object.
	 * @param code  ODU semester code, form is YYYYN0, where YYYY is the year
	 *     in which the Fall semester takes place, N=1 for Fall, =2, for 
	 *     Spring, and =3 for Summer.
	 * @param registration   date on which pre-registration for this semester 
	 *       began
	 * @param frozen   date after which students may no longer enroll in
	 *          additional course for this semester
	 */
	public Semester (int code, LocalDate registration, LocalDate frozen) {
		semesterCode = code;
		startOfPreregistration = registration;
		addDeadline = frozen;
	}
	
	/**
	 * Describe semester as  Fall/Spring/Summer Year 
	 */
	public String description() {
		//TODO
		return null;
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
		//TODO
		return 0.0;
	}
	
	
	public String toString() {
		//TODO
		return null;
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
	
}
