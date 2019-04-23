/**
 * 
 */
package edu.odu.cs.zeil.enrollment;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import java.time.LocalDate;
import java.time.Month;
import java.util.GregorianCalendar;

import org.hamcrest.number.IsCloseTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author zeil
 *
 */
class TestSemester {
	
	public final LocalDate dec25 = LocalDate.of(2017, Month.DECEMBER, 25);
	
	public final LocalDate jan1 = LocalDate.of(2018, Month.JANUARY, 1); 
	public final LocalDate jan3 = LocalDate.of(2018, Month.JANUARY, 3); 
	public final LocalDate jan6 = LocalDate.of(2018, Month.JANUARY, 6); 
	public final LocalDate jan12 = LocalDate.of(2018, Month.JANUARY, 12);
	
	public final LocalDate jan31 = LocalDate.of(2018, Month.JANUARY, 31);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for {@link edu.odu.cs.zeil.enrollment.Semester#Semester(int, java.util.Date, java.util.Date)}.
	 */
	@Test
	void testSemester() {
		Semester sem = new Semester(201720, jan1, jan12);
		assertThat(sem.description(), equalTo("Spring 2018"));
		assertThat(sem.getAddDeadline(), equalTo(jan12));
		assertThat(sem.getStartOfPreregistration(), equalTo(jan1));
		
		sem = new Semester(201810, jan1, jan12);
		assertThat(sem.description(), equalTo("Fall 2018"));

		sem = new Semester(201930, jan1, jan12);
		assertThat(sem.description(), equalTo("Summer 2020"));
	}
	
	@Test
	void testEnrCompletion() {
		Semester sem = new Semester(201720, jan1, jan12);
		assertThat (sem.enrollmentCompletion(dec25), closeTo(0.0, 0.01));
		assertThat (sem.enrollmentCompletion(jan31), closeTo(1.0, 0.01));
		
		assertThat (sem.enrollmentCompletion(jan1), closeTo(1.0/12.0, 0.01));
		assertThat (sem.enrollmentCompletion(jan3), closeTo(0.25, 0.01));
		assertThat (sem.enrollmentCompletion(jan6), closeTo(0.5, 0.01));
		
		
	}

}
