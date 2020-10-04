package com.example.fitme.IT19240602;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.fitme.DailyCalorieIntake.CalculateDailyCalorieIntake;
import com.example.fitme.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CalculateCalorieIntakeInstrumentedTest {
    @Rule
    public ActivityScenarioRule<CalculateDailyCalorieIntake> calculate_daily_calorie_intake=
            new ActivityScenarioRule<>(CalculateDailyCalorieIntake.class);

    @Test
    public void IsActivityInView(){
        onView(withId(R.id.main_tool_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.cardView)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_Age_Calorie)).check(matches(isDisplayed()));
        onView(withId(R.id.et_age_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_Gender_Calorie)).check(matches(isDisplayed()));
        onView(withId(R.id.sp_gender_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_Height_Calorie)).check(matches(isDisplayed()));
        onView(withId(R.id.et_height_feet_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.et_height_inches_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_weight_calorie)).check(matches(isDisplayed()));
        onView(withId(R.id.et_weight_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_activity_calorie)).check(matches(isDisplayed()));
        onView(withId(R.id.sp_activity_level_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_type_of_person)).check(matches(isDisplayed()));
        onView(withId(R.id.sp_type_of_person_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_calorie_calculate)).check(matches(isDisplayed()));
        onView(withId(R.id.bottomNavigationView)).check(matches(isDisplayed()));
    }


}
