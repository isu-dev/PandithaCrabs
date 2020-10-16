package com.example.fitme.IT19135458;


import com.example.fitme.fat.BFCalculator;
import com.example.fitme.fat.BFGender;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CalculateBodyFatTest {
    BFCalculator calculator ;

    @Before
    public void setUpTest(){
        calculator = new BFCalculator();
    }

    @Test
    public void testMaleWithHigh(){
        this.calculator.setAge(45);
        this.calculator.setHeight(170.68);
        this.calculator.setGender(BFGender.male);
        this.calculator.setNeck(50.00);
        this.calculator.setWaist(500.00);
        this.calculator.setWeight(150.00);

        assertEquals(Math.round(this.calculator.calculate()), Math.round(298));
    }


    @Test
    public void testFemaleWithHigh(){
        this.calculator.setAge(45);
        this.calculator.setHeight(170.68);
        this.calculator.setGender(BFGender.female);
        this.calculator.setNeck(50.00);
        this.calculator.setWaist(300.00);
        this.calculator.setWeight(60.00);

        assertEquals(Math.round(this.calculator.calculate()), Math.round(542));
    }

    @Test
    public void testFemaleWithNormal(){
        this.calculator.setAge(24);
        this.calculator.setHeight(170.68);
        this.calculator.setGender(BFGender.female);
        this.calculator.setNeck(50.00);
        this.calculator.setWaist(120.00);
        this.calculator.setWeight(60.00);

        assertEquals(Math.round(this.calculator.calculate()), Math.round(74));
    }


    @Test
    public void testMaleWithNormal(){
        this.calculator.setAge(24);
        this.calculator.setHeight(170.68);
        this.calculator.setGender(BFGender.male);
        this.calculator.setNeck(50.00);
        this.calculator.setWaist(140.00);
        this.calculator.setWeight(78.00);

        assertEquals(Math.round(this.calculator.calculate()), Math.round(61));
    }
}
