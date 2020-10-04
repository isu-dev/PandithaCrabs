package com.example.fitme.IT19240602;

import android.content.ComponentName;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.fitme.AppCommon.HomeActivity;
import com.example.fitme.AppCommon.LoginActivity;
import com.example.fitme.AppCommon.MainAppScreen;
import com.example.fitme.AppCommon.RegisterActivity;
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
public class MainAppScreenInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainAppScreen> MainAppScreenActivity=
            new ActivityScenarioRule<>(MainAppScreen.class);

    @Test
    public void IsActivityInView(){
        onView(withId(R.id.appLogo)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_appName)).check(matches(isDisplayed()));
        onView(withId(R.id.login_btn_main_screen)).check(matches(isDisplayed()));
        onView(withId(R.id.register_btn_main_screen)).check(matches(isDisplayed()));
    }

    @Test
    public void IsIntentPassedToLoginActivity(){
        Intents.init();
        onView(withId(R.id.login_btn_main_screen)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
        Intents.release();
    }

    @Test
    public void IsIntentPassedToRegisterActivity(){
        Intents.init();
        onView(withId(R.id.register_btn_main_screen)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivity.class)));
        Intents.release();
    }
}
