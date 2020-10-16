package com.example.fitme.IT19240602;

import android.content.ComponentName;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.fitme.AppCommon.HomeActivity;
import com.example.fitme.AppCommon.LoginActivity;
import com.example.fitme.AppCommon.RegisterActivity;
import com.example.fitme.DailyCalorieIntake.CalculateDailyCalorieIntake;
import com.example.fitme.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivity=
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void IsActivityInView(){
        onView(withId(R.id.tv_userName_inHeader)).check(matches(isDisplayed()));
        onView(withId(R.id.email_input_Login)).check(matches(isDisplayed()));
        onView(withId(R.id.password_input_Login)).check(matches(isDisplayed()));
        onView(withId(R.id.loginBtn_InLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.register_Button_In_login)).check(matches(isDisplayed()));

    }

    @Test
    public void IsIntentPassedToHomeActivity(){
        Intents.init();
        onView(withId(R.id.loginBtn_InLogin)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), HomeActivity.class)));
        Intents.release();
    }

    @Test
    public void IsIntentPassedToRegisterActivity(){
        Intents.init();
        onView(withId(R.id.register_Button_In_login)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivity.class)));
        Intents.release();
    }
}
