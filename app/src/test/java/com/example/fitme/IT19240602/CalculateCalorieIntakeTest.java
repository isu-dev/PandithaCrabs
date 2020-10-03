package com.example.fitme.IT19240602;

import com.example.fitme.DailyCalorieIntake.CalculateDailyCalorieIntake;
import com.example.fitme.DailyCalorieIntake.Daily_Calorie_Record;
import com.example.fitme.DailyCalorieIntake.InputsForCalorieIntake;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CalculateCalorieIntakeTest {
    private CalculateDailyCalorieIntake calculate_daily_calorie_intake;
    private Daily_Calorie_Record daily_calorie_record;
    private InputsForCalorieIntake inputsForCalorieIntake;

    @Before
    public void setUpTest(){
        calculate_daily_calorie_intake = new CalculateDailyCalorieIntake();

        inputsForCalorieIntake = new InputsForCalorieIntake();

        //SetUpInputs
        inputsForCalorieIntake.setAge(32);
        inputsForCalorieIntake.setHeightInFeet(6);
        inputsForCalorieIntake.setHeightInInches(12);
        inputsForCalorieIntake.setWeight(91);
        inputsForCalorieIntake.setGender("Male");
        inputsForCalorieIntake.setActivityLevel("Sedentary");
        inputsForCalorieIntake.setTypeOfPerson("Fatty Person");

    }

    @Test
    public void calculateCalorieIntake_isCorrect(){
        Daily_Calorie_Record daily_calorie_record = calculate_daily_calorie_intake.calculateCalorieIntakeinInsert(inputsForCalorieIntake);
        assertEquals(304,daily_calorie_record.getCarbIntake());
        assertEquals(2506,daily_calorie_record.getDailyCalorieIntake());
        assertEquals(70,daily_calorie_record.getFatIntake());
        assertEquals(166,daily_calorie_record.getProteinIntake());
    }

}
