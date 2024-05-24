package com.example.app;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.widget.Button;

import com.example.app.Pages.MainActivity;

import org.junit.Rule;
import org.junit.Test;

public class Test1 {
    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void test1() throws InterruptedException {
        onView(withId(R.id.LogInButton)).perform(click());
        onView(withId(R.id.loginEd))
                .perform(typeText("TestLogin"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEd))
                .perform(typeText("TestPassword"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.SignUpTextButton)).perform(click());
        onView(withId(R.id.loginEd))
                .check(matches(withText("TestLogin")));
        Thread.sleep(1000);
        onView(withId(R.id.nameTagEd))
                .perform(typeText("Student"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.RegisterButton))
                .perform(click());
        onView(withId(R.id.saveLocation))
                .perform(click());
        onView(withId(R.id.sendNotifications))
                .perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.sendButton))
                .perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.returnButton))
                .perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.header_title))
                .perform(typeText("Testing app"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edTag))
                .perform(typeText("Samsung"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.text))
                .perform(typeText("I'm typing text right now"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.sendButton))
                .perform(click());

        Thread.sleep(5000);
    }
    @Test
    public void test2() throws InterruptedException {
        onView(withId(R.id.header_title))
                .perform(typeText("Testing app"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edTag))
                .perform(typeText("Samsung"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.text))
                .perform(typeText("I'm typing text right now"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.clearButton))
                .perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.importantActions))
                .perform(click());
        onView(withId(R.id.copyButton))
                .perform(click());

        Thread.sleep(10000);

        onView(withId(R.id.returnButton))
                .perform(click());
        Thread.sleep(5000);
    }
}
