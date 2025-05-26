/**
 * 
 */
package edu.odu.cs.zeil.enrollment;

import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * @author zeil
 *
 */
class TestHistory {



    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link edu.odu.cs.zeil.enrollment.Semester#Semester(int, java.util.Date, java.util.Date)}.
     */
    @Test
    void testConstructor() {
        LocalDate fall21Registration = LocalDate.of(2021, 3, 29);
        LocalDate fall21AddDeadline = LocalDate.of(2021, 9, 7);
        
        History.setHistoryDir(Paths.get("src", "test", "data", "history"));
        History fall2021 = new History(202110);

        assertThat (fall2021.getRegistrationStart(), is(fall21Registration));
        assertThat (fall2021.getAddDeadline(), is(fall21AddDeadline));
    }

}
