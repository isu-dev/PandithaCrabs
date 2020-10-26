package com.example.fitme.IT19238418;


import com.example.fitme.R;
import com.example.fitme.shape.Shape;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CalculateShapeTest {
    Shape calculator ;

    @Before
    public void setUpTest(){
        calculator = new Shape();
    }

    @Test
    public void testBanana(){
        this.calculator.setHighHipSize(135);
        this.calculator.setHipSize(150);
        this.calculator.setWaistSize(150);
        this.calculator.setBustSize(150);

        assertEquals(this.calculator.calculate(), R.drawable.banana);
    }


    @Test
    public void testApple(){
        this.calculator.setHighHipSize(135);
        this.calculator.setHipSize(10);
        this.calculator.setWaistSize(150);
        this.calculator.setBustSize(150);

        assertEquals(this.calculator.calculate(), R.drawable.apple);
    }

    @Test
    public void testPears(){
        this.calculator.setHighHipSize(135);
        this.calculator.setHipSize(150);
        this.calculator.setWaistSize(150);
        this.calculator.setBustSize(10);

        assertEquals(this.calculator.calculate(), R.drawable.pear);
    }

    @Test
    public void testHourGlass(){
        this.calculator.setHighHipSize(150);
        this.calculator.setHipSize(150);
        this.calculator.setWaistSize(10);
        this.calculator.setBustSize(10);

        assertEquals(this.calculator.calculate(), R.drawable.hour_glass);
    }

}
