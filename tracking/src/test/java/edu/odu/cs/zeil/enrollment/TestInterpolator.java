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


/**
 * @author zeil
 *
 */
class TestInterpolator {
	
    static final double epsilon = 0.0001;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

    @Test
	void testInterpolatorExact() {
        
        Interpolator itp = new Interpolator();
        itp.add(new DataPoint(0.0, 0.0));
        itp.add(new DataPoint(2.0, 3.0));
        itp.add(new DataPoint(1.0, 1.0));

        assertThat(itp.get(0.0), closeTo(0.0, epsilon));
        assertThat(itp.get(1.0), closeTo(1.0, epsilon));
        assertThat(itp.get(2.0), closeTo(3.0, epsilon));
	}

    @Test
	void testInterpolatorApprox() {
        
        Interpolator itp = new Interpolator();
        itp.add(new DataPoint(0.0, 0.0));
        itp.add(new DataPoint(2.0, 3.0));
        itp.add(new DataPoint(1.0, 1.0));

        assertThat(itp.get(0.5), closeTo(0.5, epsilon));
        assertThat(itp.get(1.5), closeTo(2.0, epsilon));
	}

    @Test
	void testInterpolatorBounds() {
        
        Interpolator itp = new Interpolator();
        itp.add(new DataPoint(0.0, 0.0));
        itp.add(new DataPoint(2.0, 3.0));
        itp.add(new DataPoint(1.0, 1.0));

        assertThat(itp.get(-0.5), closeTo(0.0, epsilon));
        assertThat(itp.get(2.5), closeTo(3.0, epsilon));
	}



}
