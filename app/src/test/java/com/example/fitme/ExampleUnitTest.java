package com.example.fitme;

import com.example.fitme.bmi.addBmiRecord;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private com.example.fitme.bmi.addBmiRecord addBmiRecord;

    @Before
    public void setUp() {
        addBmiRecord = new addBmiRecord();
    }

    @Test
    public void calculateBmi_isCorrect() {
        double result = addBmiRecord.calculateBmi(5, 7, 60);
        assertEquals(21.38, result, 0.1);
    }

    @Test
    public void findCategory_isCorrect() {
        String result1 = addBmiRecord.findCategory(16.0);
        assertEquals("Underweight", result1);

        String result2 = addBmiRecord.findCategory(22.0);
        assertEquals("Normal", result2);

        String result3 = addBmiRecord.findCategory(27.0);
        assertEquals("Overweight", result3);

        String result4 = addBmiRecord.findCategory(31.0);
        assertEquals("Obese", result4);
    }

}