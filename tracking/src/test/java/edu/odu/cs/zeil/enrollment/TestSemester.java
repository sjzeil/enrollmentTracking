/**
 * 
 */
package edu.odu.cs.zeil.enrollment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.Month;
import java.util.GregorianCalendar;


/**
 * @author zeil
 *
 */
class TestSemester {
	
	private static final String CS101 = "cs101";
	private static final String CS201 = "cs201";
	private static final String CS301 = "cs301";

	public final LocalDate dec25 = LocalDate.of(2017, Month.DECEMBER, 25);
	
	public final LocalDate jan1 = LocalDate.of(2018, Month.JANUARY, 1); 
	public final LocalDate jan3 = LocalDate.of(2018, Month.JANUARY, 3); 
	public final LocalDate jan6 = LocalDate.of(2018, Month.JANUARY, 6); 
	public final LocalDate jan9 = LocalDate.of(2018, Month.JANUARY, 9); 
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
	
	@Test
	void testInterpolationByDate() {
		Semester sem = new Semester(201720, jan1, jan12);
		sem.addPoint(CS101, jan1, 10);
		sem.addPoint(CS101, jan6, 20);
		sem.addPoint(CS101, jan12, 80);
		
		sem.addPoint(CS201, jan12, 80);

		assertThat (sem.completion(CS101, dec25), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS101, jan1), closeTo(10.0/80.0, 0.01));
		assertThat (sem.completion(CS101, jan3), closeTo(14.0/80.0, 0.01));
		assertThat (sem.completion(CS101, jan6), closeTo(20.0/80.0, 0.01));
		assertThat (sem.completion(CS101, jan9), closeTo(50.0/80.0, 0.01));
		assertThat (sem.completion(CS101, jan12), closeTo(80.0/80.0, 0.01));
		assertThat (sem.completion(CS101, jan31), closeTo(80.0/80.0, 0.01));
		

		assertThat (sem.completion(CS201, dec25), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS201, jan6), closeTo(40.0/80.0, 0.01));
		assertThat (sem.completion(CS201, jan12), closeTo(80.0/80.0, 0.01));
		assertThat (sem.completion(CS201, jan31), closeTo(80.0/80.0, 0.01));

		assertThat (sem.completion(CS301, dec25), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS301, jan1), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS301, jan6), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS301, jan12), closeTo(1.0, 0.01));
		assertThat (sem.completion(CS301, jan31), closeTo(1.0, 0.01));

	}

	@Test
	void testInterpolationByFraction() {
		Semester sem = new Semester(201720, jan1, jan12);
		sem.addPoint(CS101, jan1, 10);
		sem.addPoint(CS101, jan6, 20);
		sem.addPoint(CS101, jan12, 80);
		
		sem.addPoint(CS201, jan12, 80);

		assertThat (sem.completion(CS101, 0.0), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS101, 1.0/12.0), closeTo(10.0/80.0, 0.01));
		assertThat (sem.completion(CS101, 0.25), closeTo(14.0/80.0, 0.01));
		assertThat (sem.completion(CS101, 0.5), closeTo(20.0/80.0, 0.01));
		assertThat (sem.completion(CS101, 0.75), closeTo(50.0/80.0, 0.01));
		assertThat (sem.completion(CS101, 1.0), closeTo(80.0/80.0, 0.01));
		assertThat (sem.completion(CS101, 1.5), closeTo(80.0/80.0, 0.01));
		

		assertThat (sem.completion(CS201, 0.0), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS201, 0.5), closeTo(40.0/80.0, 0.01));
		assertThat (sem.completion(CS201, 1.0), closeTo(80.0/80.0, 0.01));
		assertThat (sem.completion(CS201, 1.5), closeTo(80.0/80.0, 0.01));

		assertThat (sem.completion(CS301, 0.0), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS301, 1.0/12.0), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS301, 0.5), closeTo(0.0, 0.01));
		assertThat (sem.completion(CS301, 1.0), closeTo(1.0, 0.01));
		assertThat (sem.completion(CS301, 1.5), closeTo(1.0, 0.01));

	}

}
