/**
 * 
 */
package edu.odu.cs.zeil.enrollment;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * @author zeil
 *
 */
class TestSemesterEnrollment {
	
    static public final LocalDate registration = LocalDate.of(2022, Month.MARCH, 28); 
	static public final LocalDate addDrop = LocalDate.of(2022, Month.SEPTEMBER, 6); 
	static public final LocalDate summer = LocalDate.of(2022, Month.MAY, 20); 
	static public final LocalDate lateSummer = LocalDate.of(2022, Month.AUGUST, 15); 
	
    static public final Path fall22 = Paths.get("src", "test", "data", "simpleHistory", "202210");


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

    @Test
	void testSETotalEnroll() {
        
        SemesterEnrollment enroll = new SemesterEnrollment(202210, fall22);
        assertThat(enroll.getStartOfPreregistration(), is(registration));
        assertThat(enroll.getAddDeadline(), is(addDrop));

        assertThat(enroll.getTotal(registration), is(15*3));
        int finalReg = enroll.getTotal(addDrop); 
        assertThat(finalReg, is(138*3));

        int intermReg = enroll.getTotal(summer);
        assertThat(intermReg, is(69*3));

        int laterReg = enroll.getTotal(lateSummer);
        assertThat(laterReg, greaterThan(intermReg));
        assertThat(laterReg, lessThan(finalReg));
	}

   
}
