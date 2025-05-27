/**
 * 
 */
package edu.odu.cs.zeil.enrollment;

import org.junit.jupiter.api.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.GregorianCalendar;


/**
 * @author zeil
 *
 */
class TestSection {
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

    @Test
	void testSection() throws IOException {
        
        int crn = 1000;
        String subj = "CS";
        String num = "100G";
        String title = "Freshman Research";
        int cr = 3;
        int enr = 25;
        String link = "A2";
        String xList = "SC001";
        char campus = 'B';
        String  time = "TBA";
        String days = "MTWRF";
        String bldg = "WC2";
        String room = "";
        String instructor = "staff";

        Section cs115 = new Section(crn, subj, num, title, cr, enr, link, xList, campus, time, days, bldg, room, instructor);
        assertThat(cs115.building(), is(bldg));
        assertThat(cs115.credits(), is(cr));
        assertThat(cs115.crn(), is(crn));
        assertThat(cs115.days(), is(days));
        assertThat(cs115.enrollment(), is(enr));
        assertThat(cs115.instructor(), is(instructor));
        assertThat(cs115.link(), is(link));
        assertThat(cs115.number(), is(num));
        assertThat(cs115.room(), is(room));
        assertThat(cs115.subject(), is(subj));
        assertThat(cs115.time(), is(time));
        assertThat(cs115.title(), is(title));
        assertThat(cs115.toString(), containsString(subj));
        assertThat(cs115.toString(), containsString(title));
        assertThat(cs115.toString(), containsString(instructor));

	}

	@Test
	void testLoadFromCSV() throws IOException, CsvValidationException {
        Path dataFile = Paths.get("src", "test", "data", "history", "202210", "2022-05-20.csv");
        CSVReader reader = new CSVReader(new FileReader(dataFile.toFile()));
        String[] headers = reader.readNext();
        String[] cs115Info = reader.readNext();
        Section cs115 = Section.loadFromCSV(cs115Info);
	
        // "13","21344","CS","115","INTRO TO CS WITH PYTHON","1","30","17","","","A","","A","TRAD","Y","1000-1050AM","F","DRGS","1115","","POURSARDAR,FARYANEH","","","17","1","CLAS","27-AUG-22","09-DEC-22","0","0","0"
        int crn = 21344;
        String subj = "CS";
        String num = "115";
        String title = "INTRO TO CS WITH PYTHON";
        int cr = 1;
        int enr = 17;
        String link = "";
        String xList = "";
        char campus = 'A';
        String  time = "1000-1050AM";
        String days = "F";
        String bldg = "DRGS";
        String room = "1115";
        String instructor = "POURSARDAR,FARYANEH";

        assertThat(cs115.building(), is(bldg));
        assertThat(cs115.credits(), is(cr));
        assertThat(cs115.crn(), is(crn));
        assertThat(cs115.days(), is(days));
        assertThat(cs115.enrollment(), is(enr));
        assertThat(cs115.instructor(), is(instructor));
        assertThat(cs115.link(), is(link));
        assertThat(cs115.number(), is(num));
        assertThat(cs115.room(), is(room));
        assertThat(cs115.subject(), is(subj));
        assertThat(cs115.campus(), is(campus));
        assertThat(cs115.time(), is(time));
        assertThat(cs115.title(), is(title));
        assertThat(cs115.toString(), containsString(subj));
        assertThat(cs115.toString(), containsString(title));
        assertThat(cs115.toString(), containsString(instructor));

    }
	

}
